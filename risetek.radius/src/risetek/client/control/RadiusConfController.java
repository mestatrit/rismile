package risetek.client.control;

import risetek.client.model.RadiusConfModel;

import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.control.IAction;
import com.risetek.rismile.client.control.ModelCallback;
import com.risetek.rismile.client.control.PlainCallback;
import com.risetek.rismile.client.http.RequestFactory;


public class RadiusConfController {
	private RequestFactory objectFactory;
	private String confPath = "radiuscfg";
	public RadiusConfController(){
		objectFactory = RequestFactory.getInstance();
	}
	
	public void getConfAll(IAction action){
		objectFactory.get(confPath, null, new RadiusConfCallback(action));
	}
	public void modify(String query, IAction action){
		objectFactory.get(confPath, query, new PlainCallback(action));
	}
	class RadiusConfCallback extends ModelCallback{

		public RadiusConfCallback(IAction action) {
			super(action);
			// TODO Auto-generated constructor stub
		}

		public void onResponse(Response response) {
			// TODO Auto-generated method stub
			if(response.getStatusCode() == 200){
				String text = response.getText();
				Document dom = XMLParser.parse(text);
				Element customerElement = dom.getDocumentElement();
				XMLParser.removeWhitespace(customerElement);
				
				NodeList error = customerElement.getElementsByTagName("ERROR");

				if(error.getLength() > 0 && error.item(0).getNodeName().equals("ERROR") ){
					action.onFailure(error.item(0).getFirstChild().getNodeValue());
				}else{
				
					String secret = customerElement.getElementsByTagName("secret").item(0).getFirstChild().getNodeValue();
					String auth   = customerElement.getElementsByTagName("auth").item(0).getFirstChild().getNodeValue();
					String acct   = customerElement.getElementsByTagName("acct").item(0).getFirstChild().getNodeValue();
					String serial = customerElement.getElementsByTagName("serial").item(0).getFirstChild().getNodeValue();
					String maxuser = customerElement.getElementsByTagName("maxuser").item(0).getFirstChild().getNodeValue();
					RadiusConfModel radiusConfModel = new RadiusConfModel(auth, acct, secret, serial, maxuser);
					action.onSuccess(radiusConfModel);
				}
			}else{
				action.onUnreach("请求失败！返回："+response.getStatusText()+"。");
			}
		}
		
	}
}
