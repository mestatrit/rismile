package com.risetek.keke.client.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;

public class KeyInputComposite extends Grid {
	private SlideAnimation slide;

	public void show() {
		slide.run(400);
	}

	public void hide() {
		slide.cancel();
		background.setWidgetPosition(KeyInputComposite.this, 0, background.getOffsetHeight());
	}
	
	private AbsolutePanel background;
	public KeyInputComposite(AbsolutePanel background) {
		this.background = background;
		slide = new SlideAnimation();
		resize(3, 4);
		setWidth("100%");
		setHeight(UiKeke.kekeHeight * 2 + "px");
		setWidget(0, 0, new Button("0"));
		setWidget(0, 1, new Button("1"));
		setWidget(0, 2, new Button("2"));
		setWidget(0, 3, new Button("3"));

		setWidget(1, 0, new Button("4"));
		setWidget(1, 1, new Button("5"));
		setWidget(1, 2, new Button("6"));
		setWidget(1, 3, new Button("7"));

		setWidget(2, 0, new Button("8"));
		setWidget(2, 1, new Button("9"));
		setWidget(2, 2, new Button("0"));
		setWidget(2, 3, new Button("0"));
		getElement().getStyle().setZIndex(UiKeke.KeyPad_INDEX);
		background.add(this);
		setStyleName("keyPad");
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
			background.setWidgetPosition(KeyInputComposite.this, 0, (int) (background.getOffsetHeight() - UiKeke.kekeHeight * 2 * progress));
		}
	}
	
}
