

import cgi
import datetime
import wsgiref.handlers


from google.appengine.ext import db
from google.appengine.api import users
from google.appengine.ext import webapp

class userTABLE(db.Model):
  date = db.DateTimeProperty(auto_now_add=True)

class logTABLE(db.Model):
  message = db.StringProperty()
  date = db.DateTimeProperty(auto_now_add=True)


class MainHandler(webapp.RequestHandler):

  def get(self):
    self.response.out.write('<html><body>')
    radius_users = db.GqlQuery("SELECT * "
                            "FROM userTABLE "
                            "ORDER BY date DESC LIMIT 10")
    for greeting in radius_users:
	    self.response.out.write('get wang %s!' % greeting.date)

    self.response.out.write("""
          <form action="/sign" method="post">
            <div><textarea name="content" rows="3" cols="60"></textarea></div>
            <div><input type="submit" value="Sign Guestbook"></div>
          </form>
        </body>
      </html>""")

  def post(self):
    self.response.out.write('post wang!')

class testHandler(webapp.RequestHandler):

  def get(self):
  	loger = logTABLE()
  	loger.message = self.request.remote_addr
  	loger.put()

  def post(self):
    self.response.out.write('post wang!')

class hbHandler(webapp.RequestHandler):

  def post(self):
    self.response.out.write('<?xml version="1.0" encoding="GB2312"?><Risetek><OK><nopassword>UNTRUSTED</nopassword></OK></Risetek>')

class radiuscfgHandler(webapp.RequestHandler):

  def get(self):
    self.response.out.write('<?xml version="1.0" encoding="GB2312"?><Risetek><secret>risetek</secret><auth>1812</auth><acct>1813</acct><serial>0AC04FCA0320</serial><maxuser>500</maxuser></Risetek>')

  def post(self):
    self.response.out.write('<?xml version="1.0" encoding="GB2312"?><Risetek><secret>risetek</secret><auth>1812</auth><acct>1813</acct><serial>0AC04FCA0320</serial><maxuser>500</maxuser></Risetek>')

class logHandler(webapp.RequestHandler):

  def get(self):
    offset = self.request.get('offset')
    limit = self.request.get('lpage')
    radius_users = db.GqlQuery("SELECT * "
                            "FROM logTABLE "
                            "ORDER BY date DESC LIMIT %s, %s" % (offset, limit))
    index = 0
    self.response.out.write('<?xml version="1.0" encoding="gb2312"?><Risetek>')
    for user in radius_users:
	    self.response.out.write('<rowid>%d<logTIME>%s</logTIME>' % (index, user.date))
	    self.response.out.write('<message>coming from %s</message></rowid>' % user.message)
	    index += 1

    control = db.GqlQuery("SELECT * FROM logTABLE " )
    self.response.out.write('<CONTROL>CONTROL<TOTAL>%d</TOTAL><OFFSET>0</OFFSET></CONTROL></Risetek>' % control.count())
		
class usersHandler(webapp.RequestHandler):

  def post(self):
  	self.response.out.write('<?xml version="1.0" encoding="gb2312"?><Risetek>');
  	self.response.out.write('<rowid>2<IMSI>123456789123456</IMSI><USER>aa@12.com</USER><PASSWORD>********</PASSWORD><ADDRESS>201392385</ADDRESS><STATUS>0</STATUS></rowid><rowid>3<IMSI>123333333333333</IMSI><USER>as@12.com</USER><PASSWORD>********</PASSWORD><ADDRESS>16843009</ADDRESS><STATUS>0</STATUS></rowid><rowid>4<IMSI>111111111111111</IMSI><USER>test@sfdl.dy.133vpdn.sc</USER><PASSWORD>********</PASSWORD><ADDRESS>218103811</ADDRESS><STATUS>0</STATUS></rowid><rowid>5<IMSI>123456789012345</IMSI><USER>test@sfdl.dy.133vpdn.sc</USER><PASSWORD>********</PASSWORD><ADDRESS>218103809</ADDRESS><STATUS>0</STATUS></rowid><rowid>7<IMSI>111111111111111</IMSI><USER>test@sdd.du</USER><PASSWORD>********</PASSWORD><ADDRESS>201326594</ADDRESS><STATUS>0</STATUS></rowid><rowid>8<IMSI>111111111111111</IMSI><USER>test@qwe.dd</USER><PASSWORD>********</PASSWORD><ADDRESS>369098754</ADDRESS><STATUS>0</STATUS></rowid><rowid>9<IMSI>123456789012345</IMSI><USER>123@risetek.com</USER><PASSWORD>********</PASSWORD><ADDRESS>3232237830</ADDRESS><STATUS>0</STATUS></rowid><rowid>10<IMSI>460030930940547</IMSI><USER>test@cdz.133vpdn.sc</USER><PASSWORD>********</PASSWORD><ADDRESS>218103817</ADDRESS><STATUS>1</STATUS></rowid><CONTROL>CONTROL<TOTAL>8</TOTAL><OFFSET>0</OFFSET></CONTROL>');
  	self.response.out.write('</Risetek>');
  	
def main():
  application = webapp.WSGIApplication(
  		[	
  		('/forms/hb', hbHandler),
  		('/forms/radiuscfg', radiuscfgHandler), 
  		('/forms/SqlLogMessageXML', logHandler),
  		('/forms/SqlUserInfoXML', usersHandler),
  		('/forms/test', testHandler),
  		('/forms/.*', MainHandler) 
  		],
      debug=True)
  wsgiref.handlers.CGIHandler().run(application)

if __name__ == '__main__':
  main()
