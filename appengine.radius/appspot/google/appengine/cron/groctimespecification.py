#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


"""Implementation of scheduling for Groc format schedules.

A Groc schedule looks like '1st,2nd monday 9:00', or 'every 20 mins'. This
module takes a parsed schedule (produced by Antlr) and creates objects that
can produce times that match this schedule.

A parsed schedule is one of two types - an Interval, and a Specific Time.
See the class docstrings for more.

Extensions to be considered:

  allowing a comma separated list of times to run
  allowing the user to specify particular days of the month to run

"""


import calendar
import datetime

import groc

HOURS = 'hours'
MINUTES = 'minutes'


def GrocTimeSpecification(schedule):
  """Factory function.

  Turns a schedule specification into a TimeSpecification.

  Arguments:
    schedule: the schedule specification, as a string

  Returns:
    a TimeSpecification instance
  """

  parser = groc.CreateParser(schedule)
  parser.timespec()

  if parser.interval_mins:
    return IntervalTimeSpecification(parser.interval_mins, parser.period_string)
  else:
    return SpecificTimeSpecification(parser.ordinal_set, parser.weekday_set,
                                     parser.month_set, None, parser.time_string)


class TimeSpecification(object):
  """Base class for time specifications."""

  def GetMatches(self, start, n):
    """Returns the next n times that match the schedule, starting at time start.

    Arguments:
      start: a datetime to start from. Matches will start from after this time
      n:     the number of matching times to return

    Returns:
      a list of n datetime objects
    """
    out = []
    for _ in range(n):
      start = self.GetMatch(start)
      out.append(start)
    return out

  def GetMatch(self, start):
    """Returns the next match after time start.

    Must be implemented in subclasses.

    Arguments:
      start: a datetime to start with. Matches will start from this time

    Returns:
      a datetime object
    """
    raise NotImplementedError


class IntervalTimeSpecification(TimeSpecification):
  """A time specification for a given interval.

  An Interval type spec runs at the given fixed interval. They have two
  attributes:
  period   - the type of interval, either "hours" or "minutes"
  interval - the number of units of type period.
  """

  def __init__(self, interval, period):
    super(IntervalTimeSpecification, self).__init__(self)
    self.interval = interval
    self.period = period

  def GetMatch(self, t):
    """Returns the next match after time 't'.

    Arguments:
      t: a datetime to start from. Matches will start from after this time

    Returns:
      a datetime object
    """
    if self.period == HOURS:
      return t + datetime.timedelta(hours=self.interval)
    else:
      return t + datetime.timedelta(minutes=self.interval)


class SpecificTimeSpecification(TimeSpecification):
  """Specific time specification.

  A Specific interval is more complex, but define a certain time to run, on
  given days. They have the following attributes:
  time     - the time of day to run, as "HH:MM"
  ordinals - first, second, third &c, as a set of integers in 1..5
  months   - the months that this is valid, as a set of integers in 1..12
  weekdays - the days of the week to run this, 0=Sunday, 6=Saturday.

  The specific time interval can be quite complex. A schedule could look like
  this:
  "1st,third sat,sun of jan,feb,mar 09:15"

  In this case, ordinals would be [1,3], weekdays [0,6], months [1,2,3] and time
  would be "09:15".
  """

  def __init__(self, ordinals=None, weekdays=None, months=None, monthdays=None,
               timestr='00:00'):
    super(SpecificTimeSpecification, self).__init__(self)
    if weekdays and monthdays:
      raise ValueError("can't supply both monthdays and weekdays")
    if ordinals is None:
      self.ordinals = set(range(1, 6))
    else:
      self.ordinals = ordinals

    if weekdays is None:
      self.weekdays = set(range(7))
    else:
      self.weekdays = weekdays

    if months is None:
      self.months = set(range(1, 13))
    else:
      self.months = months

    if monthdays is None:
      self.monthdays = set()
    else:
      self.monthdays = monthdays
    hourstr, minutestr = timestr.split(':')
    self.time = datetime.time(int(hourstr), int(minutestr))

  def _MatchingDays(self, year, month):
    """Returns matching days for the given year and month.

    For the given year and month, return the days that match this instance's
    day specification, based on the ordinals and weekdays.

    Arguments:
      year: the year as an integer
      month: the month as an integer, in range 1-12

    Returns:
      a list of matching days, as ints in range 1-31
    """
    out_days = []
    start_day, last_day = calendar.monthrange(year, month)
    start_day = (start_day + 1) % 7
    for ordinal in self.ordinals:
      for weekday in self.weekdays:
        day = ((weekday - start_day) % 7) + 1
        day += 7 * (ordinal - 1)
        if day <= last_day:
          out_days.append(day)
    return sorted(out_days)

  def _NextMonthGenerator(self, start, matches):
    """Creates a generator that produces results from the set 'matches'.

    Matches must be >= 'start'. If none match, the wrap counter is incremented,
    and the result set is reset to the full set. Yields a 2-tuple of (match,
    wrapcount).

    Arguments:
      start: first set of matches will be >= this value (an int)
      matches: the set of potential matches (a sequence of ints)

    Yields:
      a two-tuple of (match, wrap counter). match is an int in range (1-12),
      wrapcount is a int indicating how many times we've wrapped around.
    """
    potential = matches = sorted(matches)
    after = start - 1
    wrapcount = 0
    while True:
      potential = [x for x in potential if x > after]
      if not potential:
        wrapcount += 1
        potential = matches
      after = potential[0]
      yield (after, wrapcount)

  def GetMatch(self, start):
    """Returns the next time that matches the schedule after time start.

    Arguments:
      start: a datetime to start with. Matches will start after this time

    Returns:
      a datetime object
    """
    start_time = start
    if self.months:
      months = self._NextMonthGenerator(start.month, self.months)
    while True:
      month, yearwraps = months.next()
      candidate = start_time.replace(day=1, month=month,
                                     year=start_time.year + yearwraps)

      if self.monthdays:
        _, last_day = calendar.monthrange(candidate.year, candidate.month)
        day_matches = sorted([x for x in self.monthdays if x <= last_day])
      else:
        day_matches = self._MatchingDays(candidate.year, month)

      if ((candidate.year, candidate.month)
          == (start_time.year, start_time.month)):
        day_matches = [x for x in day_matches if x >= start_time.day]
        if day_matches and day_matches[0] == start_time.day:
          if start_time.time() >= self.time:
            day_matches.pop(0)
      if not day_matches:
        continue
      out = candidate.replace(day=day_matches[0], hour=self.time.hour,
                              minute=self.time.minute, second=0, microsecond=0)
      return out
