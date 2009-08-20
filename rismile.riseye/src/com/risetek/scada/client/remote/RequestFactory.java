package com.risetek.scada.client.remote;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public class RequestFactory {
	private final String baseUrl;
	private String contentType;
	private Request request;
	

	public RequestFactory(){
		this(GWT.getModuleBaseURL());
	}
	
	public RequestFactory(String baseUrl){
		this.baseUrl = baseUrl;
		contentType = "application/text";
	}

	public void get( String path, String query, RequestCallback handler )
	{
		if(request != null && request.isPending()){
			request.cancel();
		}

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, baseUrl +path+"?"+query);
		try{
			GWT.log(query, null);
			request = builder.sendRequest( null, handler );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
		}
	}
	
	public void post( String path, String text, RequestCallback callback ){
		if(request != null && request.isPending()){
			request.cancel();
		}
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, baseUrl + path);
		
		if( text != null ){
			builder.setHeader("Content-Type", contentType );
		}
		
		try{
			request = builder.sendRequest( text,  callback );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
		}
	}
	
}
