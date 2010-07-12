package com.risetek.keke.client.presenter;

import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.ui.KekesComposite;

public class Presenter {
	// TODO: 我们也许会连续收到多个更新消息，如果能够过滤这些过程，也许能提高显示速度。
	// 实际上，只有事件处理完成，没有新的事件缓冲存在的时候，才需要实际去更新界面。
	
	// 通过判断传入的头部是不是发生了变化来确定是不是更新显示数据的办法，避免了事件多次
	// 发送而造成的担忧。
	
	private KekesComposite view;
	
	public void showKeyPad(boolean onoff) {
		if( onoff )
			view.keyPad.show();
		else
			view.keyPad.hide();
	}

	public void showTips(String message) {
			view.tips.show(message);
	}

	public void hideTips() {
		view.tips.hide();
}

	public Presenter(KekesComposite view) {
		this.view = view;
	}

	public void upDate(D3Context context ) {
		Sticklet sticklet = context.getSticklet();
		Stick head = sticklet.HistoryNodesStack.peek();
		Stick current = sticklet.getCurrentNode();
		view.renderKekes( sticklet.getChildrenNode(head) , current);
		current.onShow(context);
	}
}
