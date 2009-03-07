package risetek.client.control;

import java.util.ArrayList;
import java.util.List;

import risetek.client.model.IfModel;

import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.control.IAction;
import com.risetek.rismile.client.control.ModelCallback;
import com.risetek.rismile.client.control.PlainCallback;
import com.risetek.rismile.client.http.RequestFactory;

public class IfController {

	private RequestFactory requestFactory;
	private String ifpath = "Dialers";
	private String setPath = "ConfigDialers";
	public IfController(){
		requestFactory = RequestFactory.getInstance();
	}
	
	public void getIf(IAction action){
		requestFactory.get(ifpath, null, new IfCallback(action));
	}
	public void setDialer(IAction action, String text){

		if(text == null) return;
		String query = URL.encode(text);
		requestFactory.get(setPath, query, new PlainCallback(action));
	}
	class IfCallback extends ModelCallback {

		public IfCallback(IAction action) {
			super(action);
			// TODO Auto-generated constructor stub
		}

		public void onResponse(Response response) {
			// TODO Auto-generated method stub
			if(response.getStatusCode() == 200){
				
				Document customerDom = XMLParser.parse(response.getText());
				Element customerElement = customerDom.getDocumentElement();

				XMLParser.removeWhitespace(customerElement);

				NodeList error = customerElement.getElementsByTagName("ERROR");

				if (error.getLength() > 0
						&& error.item(0).getNodeName().equals("ERROR")) {
				
					action.onFailure(error.item(0).getFirstChild().getNodeValue());
				}else {

					List<IfModel> ifModelList = new ArrayList<IfModel>();
					NodeList interfaces = customerElement.getElementsByTagName("interface");
					for(int i = 0; i < interfaces.getLength(); i++){
						Element element = (Element) interfaces.item(i);
						String name = element.getAttribute("name");
						IfModel ifModel = new IfModel(name, element);
						ifModelList.add(ifModel);
					}
					
					action.onSuccess(ifModelList);
				}
			}else{
				action.onUnreach("请求失败！返回："+response.getStatusText()+"。");
			}
		}
		
	}
}
