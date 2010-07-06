package com.risetek.keke.client.nodes;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.context.RemoteResponse;
import com.risetek.keke.client.nodes.ui.PromotionComposite;
import com.risetek.keke.client.sticklet.ASticklet;

/*
 * 这个节点发起请求。
 * 
 */
public class RemoteRequestNode extends Stick {
	
	public RemoteRequestNode(String promotion) {
		super(promotion);
	}

	public int enter(ASticklet sticklet) {
		// 开始登录过程
		// 1、发送登录信息，钩挂回调函数和超时定时器。
		String password = sticklet.ParamStack.pop();
		String username = sticklet.ParamStack.pop();
	
		// 压回数据，当重复本过程的时候，需要这些参数。
		sticklet.ParamStack.push(username);
		sticklet.ParamStack.push(password);

		
		String param ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><RemoteService name=\"Login\""
			+	" sticklet=\"" + sticklet.aStickletName +"\">"

			+ "<username>" + username + "</username>" + "<password>" + password +"</password>";
		
		for( int loop = 0; loop < sticklet.ParamStack.size(); loop++ ) {
			param = param.concat("<p>");
			param = param.concat(sticklet.ParamStack.elementAt(loop));
			param = param.concat("</p>");
		}
		
		param = param.concat("</RemoteService>");
		
		try {
			RequestBuilder rqBuilder = new RequestBuilder(RequestBuilder.POST, "epay/login");
			rqBuilder.sendRequest(param, new RemoteResponse());
		} catch (RequestException e) {
			PosContext.Log("Request failed.");
			e.printStackTrace();
		}
		
		return super.enter(sticklet);
		
	}	
	
	public Composite getComposite() {
		if( composite == null )
			composite = new PromotionComposite(this);
		return composite;
	}
	
	public int failed(ASticklet sticklet) {
		Stick n = sticklet.HistoryNodesStack.pop();
		return n.rollback(sticklet);
	}
	
}
