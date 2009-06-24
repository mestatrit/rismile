package com.risetek.rismile.client.dialog;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class MyDialog extends PopupPanel implements HasHTML,
		MouseDownHandler, MouseMoveHandler, MouseUpHandler {

	private HTML caption = new HTML();
	protected Button close = new Button();
	private Widget child;
	private boolean dragging;
	private int dragStartX, dragStartY;
	private FlexTable panel = new FlexTable();

	/**
	 * Creates an empty dialog box. It should not be shown until its child
	 * widget has been added using {@link #add(Widget)}.
	 */
	public MyDialog() {
		this(false);
	}

	/**
	 * Creates an empty dialog box specifying its "auto-hide" property. It
	 * should not be shown until its child widget has been added using
	 * {@link #add(Widget)}.
	 * 
	 * @param autoHide
	 *            <code>true</code> if the dialog should be automatically hidden
	 *            when the user clicks outside of it
	 */
	public MyDialog(boolean autoHide) {
		this(autoHide, true);
	}

	/**
	 * Creates an empty dialog box specifying its "auto-hide" property. It
	 * should not be shown until its child widget has been added using
	 * {@link #add(Widget)}.
	 * 
	 * @param autoHide
	 *            <code>true</code> if the dialog should be automatically hidden
	 *            when the user clicks outside of it
	 * @param modal
	 *            <code>true</code> if keyboard and mouse events for widgets not
	 *            contained by the dialog should be ignored
	 */
	public MyDialog(boolean autoHide, boolean modal) {
		super(autoHide, modal);

		super.setWidget(panel);
		setStyleName("rismile-dialog");

		Grid head = new Grid(1,2);

		head.setWidth("100%");

		head.setWidget(0, 1, close);
		head.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
		head.getCellFormatter().setWidth(0, 1, "22px");
		head.setStyleName("caption");
		panel.setWidget(0, 0, head);

		close.setStylePrimaryName("close");
		close.setTabIndex(103);

		head.setWidget(0, 0, caption);
		head.getCellFormatter().setVerticalAlignment(0, 0,
				HasVerticalAlignment.ALIGN_BOTTOM);
		caption.setWidth("100%");

		caption.addMouseDownHandler(this);
		caption.addMouseUpHandler(this);
		caption.addMouseMoveHandler(this);
		
		panel.setHeight("100%");
		panel.setBorderWidth(0);
		panel.setCellPadding(0);
		panel.setCellSpacing(0);

		panel.getFlexCellFormatter().setColSpan(1, 0, 2);
		panel.getCellFormatter().setHeight(1, 0, "100%");
		panel.getCellFormatter().setWidth(1, 0, "100%");
		panel.getCellFormatter().setAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER,
				HasVerticalAlignment.ALIGN_MIDDLE);
	}

	public String getHTML() {
		return caption.getHTML();
	}

	public String getText() {
		return caption.getText();
	}

	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		// We need to preventDefault() on mouseDown events (outside of the
		// DialogBox content) to keep text from being selected when it
		// is dragged.
		if( event.getTypeInt() == Event.ONMOUSEDOWN ) {
			if( DOM.isOrHasChild(caption.getElement(), DOM.eventGetTarget(Event.as(event.getNativeEvent()))))
			{
				Event.as(event.getNativeEvent()).preventDefault();
			}
		}
		super.onPreviewNativeEvent(event);
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		dragging = true;
		DOM.setCapture(caption.getElement());
		dragStartX = event.getX();
		dragStartY = event.getY();
	}
	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (dragging) {
			int absX = event.getX() + getAbsoluteLeft();
			int absY = event.getY() + getAbsoluteTop();
			setPopupPosition(absX - dragStartX, absY - dragStartY);
		}
	}
	@Override
	public void onMouseUp(MouseUpEvent event) {
		dragging = false;
		DOM.releaseCapture(caption.getElement());
	}

	public boolean remove(Widget w) {
		if (child != w) {
			return false;
		}

		panel.remove(w);
		return true;
	}

	public void setHTML(String html) {
		caption.setHTML(html);
	}

	public void setText(String text) {
		caption.setText(text);
	}

	public void setWidget(Widget w) {
		// If there is already a widget, remove it.
		if (child != null) {
			panel.remove(child);
		}

		// Add the widget to the center of the cell.
		if (w != null) {
			panel.setWidget(1, 0, w);
		}

		child = w;
	}

	/**
	 * Override, so that interior panel reflows to match parent's new width.
	 * 
	 * @Override
	 */
	public void setWidth(String width) {
		super.setWidth(width);

		// note that you CANNOT call panel.setWidth("100%") until parent's width
		// has been explicitly set, b/c until then parent's width is
		// unconstrained
		// and setting panel's width to 100% will flow parent to 100% of browser
		// (i.e. can't do this in constructor)
		panel.setWidth("100%");
	}

}
