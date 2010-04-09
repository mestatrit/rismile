package com.risetek.rismile.client.http;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.risetek.rismile.client.utils.SysLog;

public class RequestFactory {
	private final String baseUrl;
	private Request request;
	
	public RequestFactory(){
		this.baseUrl = "forms";
	}

	public void get( String path, String query, RequestCallback handler )
	{
		if(request != null && request.isPending()){
			request.cancel();
		}
		RequestBuilder builder;
		
		if( query != null ){
			builder = new RequestBuilder(RequestBuilder.GET, baseUrl+"/"+path+"?"+query);
		}else{
			builder = new RequestBuilder(RequestBuilder.GET, baseUrl+"/"+path);
		}
		builder.setHeader("Content-Type", "text/plain; charset=GB18030" );
		
		try{
			SysLog.log(builder.getRequestData());
			request = builder.sendRequest( null, handler );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
			//handler.onError("网页内部出错!", e);
		}
	}
/*	
	public void post( String path, String text, RequestCallback callback ){
		if(request != null && request.isPending()){
			request.cancel();
		}
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, baseUrl+"/"+path);
		
		if( text != null ){
			builder.setHeader("Content-Type", contentType );
		}
		
		try{
			request = builder.sendRequest( text,  callback );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
		}
	}
	*/
}
