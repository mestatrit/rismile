package com.risetek.keke.client.presenter;

import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.sticklet.Sticklet;
import com.risetek.keke.client.ui.D3View;
import com.risetek.keke.client.ui.KekesComposite;

public class Presenter {
	// TODO: 我们也许会连续收到多个更新消息，如果能够过滤这些过程，也许能提高显示速度。
	// 实际上，只有事件处理完成，没有新的事件缓冲存在的时候，才需要实际去更新界面。
	
	private KekesComposite view;
	public Presenter(KekesComposite view) {
		this.view = view;
	}

	public void upDate(D3Context context ) {
		D3View.caller.ShowStack(context.callerSticklets);
		D3View.history.ShowHistoryStack(context.getSticklet().HistoryNodesStack);
		Sticklet sticklet = context.getSticklet();
		Stick head = sticklet.HistoryNodesStack.peek();
		view.renderKekes( sticklet.getChildrenNode(head) , sticklet.getCurrentNode());
	}
}
