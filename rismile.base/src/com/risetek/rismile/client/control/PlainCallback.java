package com.risetek.rismile.client.control;

import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.rismile.client.http.RequestListener;

public class PlainCallback implements RequestListener {

	private IAction action;
	
	public PlainCallback(IAction action){
		
		this.action = action;
		
	}
	public void onError(String error) {
		// TODO Auto-generated method stub
		action.onUnreach(error);
	}

	public void onLoadingFinish() {
		// TODO Auto-generated method stub
		action.onFinish();
	}

	public void onLoadingStart() {
		// TODO Auto-generated method stub
		action.onStart();
	}

	public void onResponse(Response response) {
		// TODO Auto-generated method stub
		if(response.getStatusCode() == 200){
			
			Document xmldoc = XMLParser.parse(response.getText());
			com.google.gwt.xml.client.Element entryElement = xmldoc.getDocumentElement();
			XMLParser.removeWhitespace(entryElement);
			
			NodeList error = entryElement.getElementsByTagName("ERROR");
			NodeList ok = entryElement.getElementsByTagName("OK");
			if(error != null && error.getLength() > 0 && error.item(0).hasChildNodes() ){
				String text = error.item(0).getFirstChild().getNodeValue();
				action.onFailure(text);
			}else if(ok != null && ok.getLength() > 0 && ok.item(0).hasChildNodes()){
				String text = ok.item(0).getFirstChild().getNodeValue();
				action.onSuccess(text);
			}else{
				action.onFailure("请求失败！返回：" + response.getText()+"。");
			}
		}else{
			action.onUnreach("请求失败！返回："+response.getStatusText()+"。");
		}
	}
	
}
