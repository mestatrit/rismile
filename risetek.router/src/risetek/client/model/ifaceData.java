package risetek.client.model;

import java.util.ArrayList;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.risetek.rismile.client.utils.XMLDataParse;

public class ifaceData {

	public boolean dial_on_demand;
	public boolean nat;
	public boolean mppc;
	public int idle_timeout;
	public int session_timeout;
	
	public boolean haveDefaultRoute;
	public ArrayList<router> routers;

	public class router {
		public String dest;
		public String mask;
		public router(String d, String m)
		{
			dest = d;
			mask = m;
		}
	}
	
	public void parseXML(Element element) {
		dial_on_demand = XMLDataParse.getElementText(element, "on-demand").equals("Enabled");
		nat = XMLDataParse.getElementText(element, "nat").equals("Enabled");
		mppc = XMLDataParse.getElementText(element, "mppc").equals("Enabled");
		
		try {
			idle_timeout = Integer.parseInt(XMLDataParse.getElementText(element,
					"idle_timeout"));
			session_timeout = Integer.parseInt(XMLDataParse.getElementText(element,
					"session_timeout"));
		} catch (Exception e) {
			// nothing to do.
		}
		// 路由信息是变化长度的，所以每次我们都产生一个新的实体与其对应。
		// haveDefaultRoute = false;

		routers = new ArrayList<router>();
		NodeList nodelist = element.getElementsByTagName("route");
		for (int i = 0; i < nodelist.getLength(); i++) {
			String dest = XMLDataParse.getElementText(((Element) nodelist.item(i)), "dest");
			String mask = XMLDataParse.getElementText(((Element) nodelist.item(i)), "mask");
			if( "0.0.0.0".equals(dest) && "0.0.0.0".equals(mask))
			{
				haveDefaultRoute = true;
				// 加入这句话，那么在接口路由表中就不会出现缺省路由，加还是不加？
				// continue;
			}
		router r = new router(dest, mask);
		routers.add(r);
		}
	}

}
