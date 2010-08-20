package com.risetek.rismile.client.http;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.risetek.rismile.client.utils.Heartbeat;
import com.risetek.rismile.client.utils.SysLog;

public class RequestFactory {
	private final String baseUrl;
	private Request request;
	
	public RequestFactory(){
		this.baseUrl = "forms";
	}

	// TODO: 这样hooker一个RequestCallback的目的是想能够观察请求的运行时间，从而用来
	// 调整网络速度估值。
	// 如果还能够计算数据传输的总量，那就更好了。
	private class hookRequestCallback implements RequestCallback {

		private RequestCallback hooker;
		
		public hookRequestCallback(RequestCallback handler) {
			hooker = handler;
		}
		
		@Override
		public void onResponseReceived(Request request, Response response) {
			hooker.onResponseReceived(request, response);
		}

		@Override
		public void onError(Request request, Throwable exception) {
			hooker.onError(request, exception);
		}
		
	}
	
	
	public void get( String path, String query, RequestCallback handler )
	{
		if(request != null && request.isPending()){
			request.cancel();
		}

		String url = baseUrl+"/"+path;
		
		if( query != null )
			url += "?"+query;

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		// 用监测出来的速度加上处理速度作为期望速度。
		builder.setTimeoutMillis(Heartbeat.networkspeed+1000);
		builder.setHeader("Content-Type", "text/plain; charset=GB2312" );
		try{
			SysLog.log(builder.getRequestData());
			request = builder.sendRequest( null, new hookRequestCallback(handler) );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
		}
	}
}
