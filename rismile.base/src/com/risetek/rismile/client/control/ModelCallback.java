package com.risetek.rismile.client.control;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.risetek.rismile.client.http.RequestListener;

public abstract class ModelCallback implements RequestListener {

	protected IAction action;
	
	public ModelCallback(IAction action){
		
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

	protected String getElementText( Element item, String value ) {
		String result = "";
		NodeList itemList = item.getElementsByTagName(value);
		if( itemList != null && itemList.getLength() > 0 && itemList.item(0).hasChildNodes()) {

			result = itemList.item(0).getFirstChild().getNodeValue();
		}
		return result;
	}
}
