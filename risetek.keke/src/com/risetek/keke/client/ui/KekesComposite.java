package com.risetek.keke.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.nodes.ui.StickComposite;

public class KekesComposite extends Composite implements UiKeke {

	private KeyInputComposite keyPad;
	
	// 我们试图用这个作为背景。
	private final AbsolutePanel background = new ePayBackground();

	class ePayBackground extends AbsolutePanel {
		// 我们可以在这个背景上做广告？
		public ePayBackground() {
			// 绘制背景
			Grid hikeke = new Grid(maxKeke, 1);
			hikeke.setWidth("100%");
			hikeke.setHeight("100%");
			hikeke.setCellPadding(0);
			hikeke.setCellSpacing(0);
			hikeke.setBorderWidth(0);
			for (int spacekeke = 0; spacekeke < maxKeke; spacekeke++) {
				hikeke.setWidget(spacekeke, 0, new StickComposite(null, false));
			}
			hikeke.getRowFormatter().setStyleName(hiKeke, "hilight");

			add(hikeke);
			setWidth("100%");
			setHeight(maxKeke * kekeHeight + "px");
			DOM.setStyleAttribute(getElement(), "overflow", "hidden");
			getElement().getStyle().setZIndex(UiKeke.background_INDEX);
			setStyleName("KekesComposite");
		}
	}
	
	public KekesComposite() {
		keyPad = new KeyInputComposite(background);
		initWidget(background);
	}

	static Stick oldhead = null;
	static int oldPoint;
	private Grid keke = null;

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
		int delta = oldPoint - numberToCurrentNodes;
		oldPoint = numberToCurrentNodes;
		
		if (oldhead != head) {
			oldhead = head;
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
			keke.getElement().getStyle().setZIndex(UiKeke.Sticks_INDEX);
			keke.setWidth("100%");
			keke.setHeight(maxKeke * kekeHeight + "px");
			keke.setCellPadding(0);
			keke.setCellSpacing(0);
			keke.setBorderWidth(0);

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
			if( slide == null )
				slide = new SlideAnimation(keke, (oldPoint+delta));
			slide.cancel();
			slide.setDelta(delta);
			slide.run(400);
		}
		
		if( current.hasKeyPad() == 1)
			keyPad.show();
		else
			keyPad.hide();
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
