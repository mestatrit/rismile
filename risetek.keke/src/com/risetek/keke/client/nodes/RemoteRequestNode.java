package com.risetek.keke.client.nodes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.context.RemoteResponse;
import com.risetek.keke.client.sticklet.Sticklet;

/*
 * 这个节点发起请求。
 * 
 */
public class RemoteRequestNode extends VStick {
	
	public RemoteRequestNode(String promotion) {
		super(promotion);
	}

	@Override
	public int failed(D3Context context) {
		Sticklet sticklet = context.getSticklet();
		Stick n = sticklet.HistoryNodesStack.pop();
		return n.rollback(context);
	}
	
	@Override
	public int action(D3Context context) {
		// 开始登录过程
		// 1、发送登录信息，钩挂回调函数和超时定时器。
		Sticklet sticklet = context.getSticklet();
		String method = sticklet.ParamStack.peek();
		
		String param ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><RemoteService name=\""+method+"\""
			+	" sticklet=\"" + sticklet.aStickletName +"\">";
		for( int loop = 0; loop < sticklet.ParamStack.size(); loop++ ) {
			param = param.concat("<p>");
			param = param.concat(sticklet.ParamStack.elementAt(loop));
			param = param.concat("</p>");
		}
		
		param = param.concat("</RemoteService>");
		
		try {
			RequestBuilder rqBuilder = new RequestBuilder(RequestBuilder.POST, "remotecall");
			rqBuilder.setTimeoutMillis(1000);
			GWT.log("Send Request:"+param);
			rqBuilder.sendRequest(param, new RemoteResponse());
		} catch (RequestException e) {
			D3Context.Log("Request failed.");
			e.printStackTrace();
		}
		return super.action(context);
	}
}
