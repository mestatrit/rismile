package com.risetek.rismile.client.http;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.risetek.rismile.client.utils.Heartbeat;
import com.risetek.rismile.client.utils.SysLog;

public class RequestFactory {
	private final String baseUrl;
	private Request request;
	
	public RequestFactory(String base){
		this.baseUrl = base;
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
	
	
	public synchronized void get( String path, String query, RequestCallback handler )
	{
		if(request != null && request.isPending()){
			request.cancel();
		}

//		String url = baseUrl+"/"+ (path == null ? "" : path);
		String url = baseUrl + (path == null ? "" : path);
		// 为了防止数据被Cache，需要加入变化的时间戳。
		url += "?ts=" + System.currentTimeMillis(); 
		
		if( query != null )
			url += "&"+query;

		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
		
		GWT.log("GET: " + url );
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

	public synchronized void POST( String path, String query, RequestCallback handler )
	{
		if(request != null && request.isPending()){
			request.cancel();
		}

//		String url = baseUrl+"/"+ (path == null ? "" : path);
		String url = baseUrl + (path == null ? "" : path);
		GWT.log("POST: " + url );
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
		// 用监测出来的速度加上处理速度作为期望速度。
		builder.setTimeoutMillis(Heartbeat.networkspeed+1000);
		builder.setHeader("Content-Type", "text/plain; charset=GB2312" );
		try{
			// 如果不给数据，回调无法完成，为什么？
			request = builder.sendRequest( (query == null ? "nodata" : query), handler );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
		}
	}


	public synchronized void DELETE( String path, String query, RequestCallback handler )
	{
		if(request != null && request.isPending()){
			request.cancel();
		}

		String url = baseUrl+"/"+ (path == null ? "" : path);
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.DELETE, URL.encode(url));
		// 用监测出来的速度加上处理速度作为期望速度。
		builder.setTimeoutMillis(Heartbeat.networkspeed+1000);
		builder.setHeader("Content-Type", "text/plain; charset=GB2312" );
		try{
			request = builder.sendRequest( query, handler );
		} catch (RequestException e){ 
			GWT.log( "error", e); 
		}
	}

}
