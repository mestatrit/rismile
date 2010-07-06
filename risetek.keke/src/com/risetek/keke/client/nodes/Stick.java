package com.risetek.keke.client.nodes;

import com.google.gwt.user.client.ui.Composite;
import com.risetek.keke.client.context.ClientEventBus;
import com.risetek.keke.client.resources.IconManage;
import com.risetek.keke.client.sticklet.ASticklet;
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
	public String imgName;

	Composite composite = null;
	
	public Stick getChildren() {
		return children;
	}
	
	public abstract Composite getComposite();

	public Stick(String promotion) {
		this(promotion, IconManage.getDefault());
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
	
	private void addNextNode(Stick sticklet) {
		if( next == null )
			next = sticklet;
		else
			next.addNextNode(sticklet);
	}

	public int enter(ASticklet sticklet) {
		LogEnter();
		
		Stick last = sticklet.getCurrentNode();
		if( last != null ){
			last.leave(sticklet);
		}
		sticklet.setCurrentNode(this);
		
		if( getComposite() != null )
			ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.ViewChangedEvent());
		return NODE_STAY;
	}
	
	public int leave(ASticklet sticklet) {
		return 0;
	}
	
	// 我们离开这个节点进入下一步的时候，执行该动作。
	public int action(ASticklet sticklet) {
		LogAction();
		return NODE_OK;
	}
	
	public void press(int keyCode) {
		
	}
	
	public int rollback(ASticklet sticklet) {
		LogRollback();
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
		LogFaild();
		return NODE_STAY;
	}
	
	
	// 调试用。
	public void LogEnter() {
		String name = getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("->"+name+ " "+ Promotion);
	}

	public void LogRollback() {
		String name = getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("<-- "+name+ " "+ Promotion);
	}

	public void LogFaild() {
		String name = getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("["+name+ "] failed "+ Promotion);
	}


	public void LogAction() {
		/*
		String name = node.getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("  "+name+ " action");
		*/
		D3View.logger.logger.addItem( "["+ Promotion + "] action");
	}
	
	
}
