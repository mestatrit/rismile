package com.risetek.keke.client.nodes;

import java.util.HashMap;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.context.ClientEventBus.HIDControlEvent;
import com.risetek.keke.client.resources.IconManage;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.ui.D3View;

/*
 * 这个结构用来表达一系列串联的节点。并能存储到数据库中。这是一种变异了的树形结构。
 */

public abstract class Stick {
	
	public final static int NODE_OK		= 0;
	public final static int NODE_STAY	= 1;
	public final static int NODE_CANCEL	= 2;
	public final static int NODE_EXIT	= -1;
	
	private Stick children;
	public Stick next;
	public String Ticker;
	public String Promotion;
	public String imgName = null;

	Composite composite = null;
	
	HashMap<String, String> StickParams = new HashMap<String, String>();
	
	public Stick getChildren() {
		return children;
	}
	
	public abstract Composite getComposite();

	public Stick(String promotion) {
		this(promotion, IconManage.getDefault());
	}

	public Stick(String promotion, String params) {
		Ticker = getClass().getName().substring(30);
		Promotion = promotion;
		if( params != null )
		{
			try {
				Document doc = XMLParser.parse(params);
				NodeList list = doc.getElementsByTagName("p");
				if( list.getLength() > 0 ) {
					list = list.item(0).getChildNodes();
					for( int loop =0; loop < list.getLength(); loop++ ) {
						Node n = list.item(loop);
						Node valueNode = n.getAttributes().getNamedItem("v");
						if( valueNode != null )
							StickParams.put(n.getNodeName(), valueNode.getNodeValue());
						else
							StickParams.put(n.getNodeName(), "");
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
			imgName = StickParams.get("img");
		}
		if( imgName == null )
			imgName = params;
	}
	

	public int enter(D3Context context) {
		
		LogEnter();
		//ClientEventBus.INSTANCE.addHandler(context.controlCodehandler,HIDControlEvent.TYPE);
		
		Sticklet sticklet = context.getSticklet(); 
		Stick last = sticklet.getCurrentNode();
		if( last != null ){
			last.leave(context);
		}
		sticklet.setCurrentNode(this);
		ViewChanged(context);
		return NODE_STAY;
	}
	
	public void ViewChanged(D3Context context) {
		if( getComposite() != null ) {
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
		}
	}
	
	public int leave(D3Context context) {
		//ClientEventBus.INSTANCE.removeHandler(context.controlCodehandler,HIDControlEvent.TYPE);
		onHide(context);
		return 0;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(D3Context context) {
		LogAction();
		return NODE_OK;
	}
	
	public int rollback(D3Context context) {
		LogRollback();
		Sticklet sticklet = context.getSticklet();
		Stick last = sticklet.getCurrentNode();
		if( last != null ){
			last.leave(context);
		}
		sticklet.setCurrentNode(this);
		ViewChanged(context);
		return NODE_STAY;
	}

	public int failed(D3Context context) {
		LogFaild();
		return NODE_STAY;
	}	
	
	
	/*
	 * 链接一个节点到本节点的子孙
	 */
	public Stick addChildrenNode(Stick node) {
		if( children == null )
			children = node;
		else
			children.addNextNode(node);
		return node;
	}
	
	/*
	 * 链接一个弟兄到本节点
	 */
	
	private void addNextNode(Stick sticklet) {
		if( next == null )
			next = sticklet;
		else
			next.addNextNode(sticklet);
	}
	
	// 调试用。
	public String getClassName() {
		String name = getClass().getName();
		name = name.substring(30);
		return name;
	}

	public void LogEnter() {
		String name = getClassName();
		D3View.logger.Log("->"+name+ " "+ Promotion);
	}

	public void LogRollback() {
		String name = getClassName();
		D3View.logger.Log("<-- "+name+ " "+ Promotion);
	}

	public void LogFaild() {
		String name = getClassName();
		D3View.logger.Log("! "+name+ " " + Promotion);
	}


	public void LogAction() {
		/*
		String name = node.getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("  "+name+ " action");
		*/
		D3View.logger.Log( "@ "+ Promotion);
	}
	
	public void onShow(D3Context context) {
	}

	public void onHide(D3Context context) {
	}
}
