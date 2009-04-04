

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

  def get(self):
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
  	
class monitorHandler(webapp.RequestHandler):

  def get(self):
  	self.response.headers['Content-Type'] = 'application/x-java-jnlp-file'
	self.response.out.write('<?xml version="1.0" encoding="utf-8"?>')
	self.response.out.write('<jnlp spec="1.5+" codebase="http://risedius.appspot.com/" href="forms/rtmon">')
	self.response.out.write('<information><title>Risetek Monitor</title><vendor>ChengDu Risetek Corp.</vendor>')
	self.response.out.write('<homepage href="http://www.risetek.com"/><description>risetek monitor</description>')
	self.response.out.write('<description kind="short">risetek monitor</description>')
	self.response.out.write('<association mime-type="application-x/singleinstance-file" extensions="sif"/>')
	self.response.out.write('<shortcut online="false" /></information>')
	self.response.out.write('<resources><j2se version="1.5+" /><jar href="httping.jar" main="true" /></resources>')
	self.response.out.write('<application-desc main-class="com.risetek.pinger.RisetekMonitor">')
	self.response.out.write('<argument>risedius.appspot.com</argument></application-desc>')
	self.response.out.write('<security><all-permissions/></security>')
	self.response.out.write('</jnlp>')  

class netstateHandler(webapp.RequestHandler):

  def get(self):
	self.response.out.write('<?xml version="1.0" encoding="utf-8"?>')
	self.response.out.write('<Risetek><networks><interface name="eth0"><flags>UP</flags><flags>BROADCAST</flags><flags>RUNNING</flags><flags>SIMPLEX</flags><flags>MULTICAST</flags><ip_address>10.0.0.10</ip_address><ip_mask>255.255.255.0</ip_mask><ip_broadcast>10.0.0.255</ip_broadcast></interface><interface name="lo0"><flags>UP</flags><flags>LOOPBACK</flags><flags>RUNNING</flags><flags>MULTICAST</flags><ip_address>127.0.0.1</ip_address><ip_mask>255.0.0.0</ip_mask></interface></networks><networks><eth0><flags>UP</flags><flags>BROADCAST</flags><flags>RUNNING</flags><flags>SIMPLEX</flags><flags>MULTICAST</flags><ip_address>10.0.0.10</ip_address><ip_mask>255.255.255.0</ip_mask><ip_broadcast>10.0.0.255</ip_broadcast></eth0><lo0><flags>UP</flags><flags>LOOPBACK</flags><flags>RUNNING</flags><flags>MULTICAST</flags><ip_address>127.0.0.1</ip_address><ip_mask>255.0.0.0</ip_mask></lo0></networks><Protocols><protocol name="IPv4"><Total>3</Total><Bad>0</Bad><Reassembled>0</Reassembled><Delivered>3</Delivered><Send><Total>2</Total><Raw>0</Raw><Fragmented>0</Fragmented></Send></protocol><protocol name="ICMPv4"><Received><ECHO>0</ECHO><ECHO_REPLY>0</ECHO_REPLY><UNREACH>0</UNREACH><REDIRECT>0</REDIRECT><Other>0</Other><Bad>0</Bad></Received><Send><ECHO>0</ECHO><ECHO_REPLY>0</ECHO_REPLY><UNREACH>0</UNREACH><REDIRECT>0</REDIRECT><Other>0</Other></Send></protocol><protocol name="TCP"><Connections><Initiated>0</Initiated><Accepted>1</Accepted><Established>1</Established><Closed>2</Closed></Connections><Received><Packets>3</Packets><DataPackets>1</DataPackets><Bytes>422</Bytes></Received><Sent><Packets>3</Packets><DataPackets>2</DataPackets><Bytes>1627</Bytes></Sent></protocol><protocol name="UDP"><Received><Total>0</Total><Bad><Drops>0</Drops><Badsum>0</Badsum><Badlen>0</Badlen><Noport>0</Noport><Noportbcast>0</Noportbcast><Fullsock>0</Fullsock></Bad></Received><Send><Total>0</Total></Send></protocol></Protocols><MBUFFERS><Mbufs>16</Mbufs><Clusters>3</Clusters><FreeClusters>1</FreeClusters><Drops>0</Drops><Waits>0</Waits><Drains>0</Drains><CopyFails>0</CopyFails><PullupFails>0</PullupFails><FREE>11</FREE><DATA>4</DATA><HEADER>1</HEADER><SONAME>0</SONAME><FTABLE>0</FTABLE></MBUFFERS><ROUTER></ROUTER></Risetek>')

class systateHandler(webapp.RequestHandler):

  def get(self):
	self.response.out.write('<?xml version="1.0" encoding="utf-8"?>')
	self.response.out.write('<Risetek><currnet_date>2008/03/13</currnet_date><currnet_time>17:13:37</currnet_time><serial>0AC04FCA0320</serial>')
	self.response.out.write('<service><radius><version>Mar 13 2008 16:58:18</version><status>0 day 00:14:48</status></radius></service></Risetek>')

  	
def main():
  application = webapp.WSGIApplication(
  		[	
  		('/forms/hb', hbHandler),
  		('/forms/radiuscfg', radiuscfgHandler), 
  		('/forms/netstate', netstateHandler), 
  		('/forms/SqlLogMessageXML', logHandler),
  		('/forms/SqlUserInfoXML', usersHandler),
  		('/forms/SysStateXML', systateHandler),
  		('/forms/test', testHandler),
  		('/forms/rtmon', monitorHandler),
  		('/forms/.*', MainHandler)
  		],
      debug=True)
  wsgiref.handlers.CGIHandler().run(application)

if __name__ == '__main__':
  main()
