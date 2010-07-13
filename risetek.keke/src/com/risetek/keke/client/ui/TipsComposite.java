package com.risetek.keke.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TipsComposite extends Composite {

	// TODO: 应该根据内容调整显示高度。
	
	private SlideAnimation slide;
	private VerticalPanel tipsPanel;
	
	public void show(String message) {
		tipsPanel.clear();
		slide.cancel();
		HTML htmlMessage = new HTML(message);
		tipsPanel.add(htmlMessage);
		slide.run(400);
	}

	public void hide() {
		tipsPanel.clear();
		slide.cancel();
		background.setWidgetPosition(this, 0, background.getOffsetHeight());
	}
	
	private AbsolutePanel background;
	public TipsComposite(AbsolutePanel background) {

		this.background = background;
		slide = new SlideAnimation();
		tipsPanel = new VerticalPanel();

		tipsPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		tipsPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);

		tipsPanel.setWidth("99%");
		tipsPanel.setHeight("98%");
		tipsPanel.getElement().getStyle().setBackgroundColor("#202050");
		tipsPanel.getElement().getStyle().setColor("white");
		
		VerticalPanel outPanel = new VerticalPanel();
		
		outPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		outPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);

		outPanel.setWidth("100%");
		outPanel.setHeight(UiKeke.kekeHeight * UiKeke.TipsHeight + "px");
		outPanel.add(tipsPanel);

		initWidget(outPanel);
		background.add(this);
		getElement().getStyle().setZIndex(UiKeke.Tips_INDEX);
		getElement().getStyle().setBackgroundColor("#9abeff");
		getElement().getStyle().setOpacity(0.9);
		tipsPanel.getElement().getStyle().setOpacity(0.9);
	}
	
	private class SlideAnimation extends Animation {
		@Override
		protected void onStart() {
			super.onStart();
		}

		@Override
		protected void onComplete() {
			super.onComplete();
		}

		@Override
		protected void onUpdate(double progress) {
//			background.setWidgetPosition(TipsComposite.this, 0, (int) (background.getOffsetHeight() - UiKeke.kekeHeight * UiKeke.TipsHeight * progress));
			background.setWidgetPosition(TipsComposite.this, (int) (background.getOffsetWidth() * progress)
					, (int) (background.getOffsetHeight() - UiKeke.kekeHeight * UiKeke.TipsHeight * 1));
		}
	}
	
}
