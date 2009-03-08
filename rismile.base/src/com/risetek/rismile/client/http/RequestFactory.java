package com.risetek.rismile.client.http;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.risetek.rismile.client.control.SysLog;

public class RequestFactory {
	private final String baseUrl;
	private String contentType;
	private Request request;
	

	public RequestFactory(){
		this("forms");
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

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, baseUrl+"/"+path+"?"+query);
		try{
			SysLog.log(builder.getRequestData());
			request = builder.sendRequest( null, handler );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
			//handler.onError("网页内部出错!", e);
		}
	}
	
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
			//handler.onError("网页内部出错!");
		}
	}
	
/*	
	public class ResponseCallback implements RequestCallback{
		private RequestListener handler;

		public ResponseCallback(RequestListener handler) {

			this.handler = handler;
		}
		
		public void onError(Request request, Throwable exception)
		{
			GWT.log( "error", exception );
			MessageConsole.setText("请求失败！");
			handler.onError("网络故障!");
		}
		
		public void onResponseReceived(Request request, Response response)
		{
			
			handler.onResponse(response);
			
		}
	}
*/
}
