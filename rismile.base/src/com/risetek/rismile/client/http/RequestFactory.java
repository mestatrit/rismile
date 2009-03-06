package com.risetek.rismile.client.http;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

public class RequestFactory {
	static private RequestFactory instance;
	private final String baseUrl;
	private String contentType;
	private Request request;
	static public RequestFactory getInstance(){
		if(instance == null){
			instance = new RequestFactory("forms");
		}
		return instance;
	}
	private RequestFactory(String baseUrl){
		this.baseUrl = baseUrl;
		contentType = "application/text";
	}

	public void get( String path, String query, RequestListener handler ){
		if(request != null && request.isPending()){
			request.cancel();
		}
		handler.onLoadingStart();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, baseUrl+"/"+path+"?"+query);
		try{
			request = builder.sendRequest( null, new ResponseCallback(handler) );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
			handler.onError("网页内部出错!");
		}
	}
	public void post( String path, String text, RequestListener handler ){
		if(request != null && request.isPending()){
			request.cancel();
		}
		handler.onLoadingStart();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, baseUrl+"/"+path);
		
		if( text != null ){
			builder.setHeader("Content-Type", contentType );
		}
		
		try{
			request = builder.sendRequest( text,  new ResponseCallback(handler) );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
			handler.onError("网页内部出错!");
		}
	}
	
	public class ResponseCallback implements RequestCallback{
		private RequestListener handler;

		public ResponseCallback(RequestListener handler) {

			this.handler = handler;
		}
		
		public void onError(Request request, Throwable exception){
			
			GWT.log( "error", exception );
			handler.onError("网络故障!");
		}
		
		public void onResponseReceived(Request request, Response response){
			
			handler.onResponse(response);
			
		}
	}

}
