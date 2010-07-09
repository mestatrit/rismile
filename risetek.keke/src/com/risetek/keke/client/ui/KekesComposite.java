package com.risetek.keke.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.nodes.ui.StickComposite;

public class KekesComposite extends Composite implements UiKeke {

	private Grid keke = null;

	// 我们试图用这个作为背景。
	private final Grid hikeke = new Grid(maxKeke, 1);
	private AbsolutePanel background = new AbsolutePanel();

	public KekesComposite() {

		// 绘制背景
		hikeke.setWidth("100%");
		hikeke.setHeight(maxKeke * kekeHeight + "px");
		hikeke.setCellPadding(0);
		hikeke.setCellSpacing(0);
		hikeke.setBorderWidth(0);
		for (int spacekeke = 0; spacekeke < maxKeke; spacekeke++) {
			StickComposite empty = new StickComposite(null, false);
			hikeke.setWidget(spacekeke, 0, empty);
		}
		hikeke.getRowFormatter().setStyleName(hiKeke, "hilight");

		initWidget(background);
		background.setWidth("100%");
		background.setHeight(maxKeke * kekeHeight + "px");
		DOM.setStyleAttribute(background.getElement(), "overflow", "hidden");

		// hikeke用做背景。
		background.add(hikeke);

		setStyleName("KekesComposite");
	}

	static Stick oldhead = null;
	static int oldPoint;

	public void renderKekes(Stick head, Stick current) {
		int numberOfNodes = 0;
		int numberToCurrentNodes = 0;
		Stick p = head;
		while (p != null) {
			if (p == current)
				numberToCurrentNodes = numberOfNodes;
			numberOfNodes++;
			p = p.next;
		}

		// 如果头没变，说明
		boolean changed = (oldhead != head);
		oldhead = head;
		int delta = oldPoint - numberToCurrentNodes;
		oldPoint = numberToCurrentNodes;
		
		if (changed) {
			if( slide != null ) {
				slide.cancel();
				slide = null;
			}
			
			// 先消隐?
			if (keke != null) {
				background.remove(keke);
				keke = null;
			}

			keke = new Grid(maxKeke + numberOfNodes, 1);
			keke.setWidth("100%");
			keke.setHeight(maxKeke * kekeHeight + "px");
			keke.setCellPadding(0);
			keke.setCellSpacing(0);
			keke.setBorderWidth(0);
			// DOM.setStyleAttribute(keke.getElement(), "overflow", "hidden");

			// 绘制前空格
			int loop;
			for (loop = 0; loop < hiKeke; loop++) {
				StickComposite empty = new StickComposite(null, false);
				keke.setWidget(loop, 0, empty);
			}

			p = head;
			while (p != null) {
				keke.setWidget(loop++, 0, p.getComposite());
				p = p.next;
			}

			// 绘制后空格
			for (; loop < maxKeke + numberOfNodes; loop++) {
				StickComposite empty = new StickComposite(null, false);
				keke.setWidget(loop, 0, empty);
			}

			// keke 是前景。
			background.add(keke, 0, -(numberToCurrentNodes * kekeHeight));
		}
		else
		{
			// TODO: FIXME:
			// 如果连续按下键盘，会出错。
			// 关闭键盘消息？
			if( slide == null )
				slide = new SlideAnimation(keke, (oldPoint+delta));
			slide.cancel();
			slide.setDelta(delta);
			slide.run(400);
		}
	}

	private SlideAnimation slide;

	private class SlideAnimation extends Animation {

		private Grid card;
		private int delta =0;
		private double here;

		public void setDelta(int delta) {
			this.delta += delta;
		}

		protected void onStart() {
			super.onStart();
		}

		protected void onComplete() {
			super.onComplete();
			here = here + delta * kekeHeight;
			delta = 0;
		}

		public SlideAnimation(Grid card, int from) {
			this.card = card;
			here = -(from * kekeHeight);
		}

		@Override
		protected void onUpdate(double progress) {
			background.setWidgetPosition(card, 0, (int) (here + delta
					* progress * kekeHeight));
		}
	}
}
