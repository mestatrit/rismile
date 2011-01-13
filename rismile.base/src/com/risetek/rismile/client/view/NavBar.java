package com.risetek.rismile.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.risetek.rismile.client.conf.UIConfig;
import com.risetek.rismile.client.utils.KEY;

public class NavBar extends Composite {

	private final Button gotoFirst;
	private final Button gotoLast;
	private final Button gotoNext;
	private final Button gotoPrev;

	public boolean enable = true;
	private final HorizontalPanel buttonsPanel = new HorizontalPanel();

	public boolean ASC = true;

	public NavBar() {
		initWidget(buttonsPanel);
		buttonsPanel.setBorderWidth(0);
		buttonsPanel.setHeight("100%");
		buttonsPanel.getElement().getStyle().setPaddingRight(10, Unit.PX);
		buttonsPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		setHeight(UIConfig.TitleHeight);
		// First
		gotoFirst = new Button("&lt;&lt;", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fireEvent(new NavEvent(DIR.FIRST));
			}
		});
		buttonsPanel.add(gotoFirst);
		Style style = gotoFirst.getElement().getStyle();
		style.setFontWeight(FontWeight.BOLD);
		style.setWidth(UIConfig.DIR_BUTTON_WIDTH, Unit.PX);

		// Prev
		gotoPrev = new Button("&lt;", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fireEvent(new NavEvent(DIR.PREV));
			}
		});
		buttonsPanel.add(gotoPrev);
		style = gotoPrev.getElement().getStyle();
		style.setFontWeight(FontWeight.BOLD);
		style.setWidth(UIConfig.DIR_BUTTON_WIDTH, Unit.PX);
		// Next
		gotoNext = new Button("&gt;", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fireEvent(new NavEvent(DIR.NEXT));
			}
		});
		buttonsPanel.add(gotoNext);
		style = gotoNext.getElement().getStyle();
		style.setFontWeight(FontWeight.BOLD);
		style.setWidth(UIConfig.DIR_BUTTON_WIDTH, Unit.PX);

		// Last
		gotoLast = new Button("&gt;&gt;", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fireEvent(new NavEvent(DIR.LAST));
			}
		});
		buttonsPanel.add(gotoLast);
		style = gotoLast.getElement().getStyle();
		style.setFontWeight(FontWeight.BOLD);
		style.setWidth(UIConfig.DIR_BUTTON_WIDTH, Unit.PX);

		enabelNavbar(false, false, false, false);
	}

	public void enabelNavbar(boolean firstEnabled, boolean preEnabled,
			boolean nextEnabled, boolean lastEnabled) {
		if (!enable) {
			gotoFirst.setEnabled(false);
			gotoPrev.setEnabled(false);
			gotoNext.setEnabled(false);
			gotoLast.setEnabled(false);
			return;
		}
		gotoFirst.setEnabled(firstEnabled);
		gotoPrev.setEnabled(preEnabled);
		gotoNext.setEnabled(nextEnabled);
		gotoLast.setEnabled(lastEnabled);
	}

	// 数据导航事件处理
	HandlerManager handler = new HandlerManager(this);

	public HandlerRegistration addNavHandler(NavHandler navHandler) {
		return addHandler(navHandler, NavEvent.getType());
	}

	public interface NavHandler extends EventHandler {
		void onNav(NavEvent event);
	}

	public enum DIR {FIRST, PREV, NEXT, LAST};
	
	public static class NavEvent extends GwtEvent<NavHandler> {
		private static Type<NavHandler> TYPE;

		public static Type<NavHandler> getType() {
			if (TYPE == null) {
				TYPE = new Type<NavHandler>();
			}
			return TYPE;
		}

		@Override
		public final Type<NavHandler> getAssociatedType() {
			return TYPE;
		}

		DIR resultHtml;

		public NavEvent(DIR resultsHtml) {
			this.resultHtml = resultsHtml;
		}

		public DIR getResult() {
			return resultHtml;
		}

		@Override
		protected void dispatch(NavHandler handler) {
			handler.onNav(this);
		}
	}

	public void doAction(int keyCode) {
		switch (keyCode) {
		case KEY.HOME:
			if (gotoFirst.isEnabled()) {
				gotoFirst.click();
			}
			break;
		case KEY.END:
			if (gotoLast.isEnabled()) {
				gotoLast.click();
			}
			break;
		case KEY.PAGEUP:
			if (gotoPrev.isEnabled()) {
				gotoPrev.click();
			}
			break;
		case KEY.PAGEDOWN:
			if (gotoNext.isEnabled()) {
				gotoNext.click();
			}
			break;
		default:
			break;
		}
	}

}
