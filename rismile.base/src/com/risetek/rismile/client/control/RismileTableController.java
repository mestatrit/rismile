package com.risetek.rismile.client.control;

import com.google.gwt.http.client.RequestCallback;
import com.risetek.rismile.client.http.RequestFactory;

public abstract class RismileTableController implements RequestCallback {

	public RequestFactory remoteRequest = new RequestFactory();
	String tableName;
	
	public void changeTableData(String formFunction, String query, RequestCallback callback){
		remoteRequest.get(formFunction, query, callback);
	}

	
	public void loadTableData(String formFunction, String query){
		remoteRequest.get(formFunction, query, this);
	}

	
}
