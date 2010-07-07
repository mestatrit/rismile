package com.risetek.keke.client.sticklet;

import java.util.Stack;

import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.Stick;

public class Sticklet {
	public String aStickletName = null;
	
	public Sticklet(Stick root) {
		rootNode = root;
		aStickletName = root.Promotion;
	}
	
	/*
	 * 节点移动路径记录表
	 * 用来获取当前节点的上一级节点信息，并通过上级节点获取本级节点的首项节点信息。
	 * 也用于rollback中。
	 */
	
	public Stack<Stick>	HistoryNodesStack = new Stack<Stick>();
	/*
	 * 标志当前活动中的节点。 
	 */
	private Stick currentNode;
	public Stick getCurrentNode() {
		return currentNode;
	}
	
	public void setCurrentNode(Stick n) {
			currentNode = n;
	}
	/*
	 * 本Sticklet的入口节点。
	 */
	private Stick rootNode;
	
	/*
	 * 双链路，钩挂调用者和被调用者的层级关系。
	 */
	public Sticklet	calledSticklet = null;
	public Sticklet	callerSticklet = null;

	/*
	 * 获取Sticklet调用层级关系中的当前活动目标。
	 */
	public Sticklet getActiveSticklet() {
		if( calledSticklet != null )
			return calledSticklet.getActiveSticklet();
		return this;
	}
	
	// 执行其间的运行参数堆栈。
	public Stack<String> ParamStack = new Stack<String>();
	
	public int Execute(D3Context context) {
		return rootNode.enter(context);
	}

	/*
	 * 我们或者得到节点的直接子孙。
	 * 或者得到了CallerNode的直接子孙。
	 */
	public Stick getChildrenNode(Stick p) {
		return p.getChildren();
	}

	/*
	 * 调用执行。
	 * 调用者的参数是不是也应该压入子sticklet中，以便执行？
	 */
	public int Call(Sticklet called, D3Context context) {
		calledSticklet = called;
		called.callerSticklet = this;
		return called.Execute(context);
	}
	
	public int Clean() {
		D3Context.Log(aStickletName+" Clean up");
		return 0;
	}
}
