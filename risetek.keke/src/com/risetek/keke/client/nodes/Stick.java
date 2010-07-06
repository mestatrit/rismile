package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.sticklet.ASticklet;

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
	public String imgName;

	Composite composite = null;
	
	public Stick getChildren() {
		return children;
	}
	
	public abstract Composite getComposite();

	public Stick(String promotion) {
		this(promotion,"20090218213218568");
	}

	public Stick(String promotion, String imgName) {
		Ticker = getClass().getName().substring(30);
		Promotion = promotion;
		this.imgName = imgName;
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
	
	private void addNextNode(Stick node) {
		if( next == null )
			next = node;
		else
			next.addNextNode(node);
	}

	public int enter(ASticklet sticklet) {
		PosContext.LogEnter(this);
		
		Stick last = sticklet.getCurrentNode();
		if( last != null ){
			last.leave(sticklet);
		}
		sticklet.setCurrentNode(this);
		
		if( getComposite() != null )
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
		return NODE_STAY;
	}
	
	public int leave(ASticklet widget) {
		return 0;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet widget) {
		PosContext.LogAction(this);
		return NODE_OK;
	}
	
	public void press(int keyCode) {
		
	}
	
	public int rollback(ASticklet sticklet) {
		PosContext.LogRollback(this);
		Stick last = sticklet.getCurrentNode();
		if( last != null ){
			last.leave(sticklet);
		}
		sticklet.setCurrentNode(this);
		
		if( getComposite() != null )
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
		return NODE_STAY;
	}

	public int failed(ASticklet sticklet) {
		return NODE_STAY;
	}
}