package com.risetek.rismile.client.dialog;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A form of popup that has a caption area at the top and can be dragged by the
 * user.
 * <p>
 * <img class='gallery' src='DialogBox.png'/>
 * </p>
 * <h3>CSS Style Rules</h3> <ul class='css'> <li>.gwt-DialogBox { the outside of
 * the dialog }</li> <li>.gwt-DialogBox .Caption { the caption }</li> </ul>
 * <p>
 * <h3>Example</h3>
 * {@example com.google.gwt.examples.DialogBoxExample}
 * </p>
 */
public abstract class MyDialog extends PopupPanel implements HasHTML,
		MouseListener, ClickListener {

	private HTML caption = new HTML();
	protected Button close = new Button();
	private Widget child;
	private boolean dragging;
	private int dragStartX, dragStartY;
	private FlexTable panel = new FlexTable();
	// 用来实现半透明屏蔽
	Element mask = DOM.createDiv();

	/*
	 * 将自己灰色屏蔽取消。
	 */
	public void unmask() {
		mask.getParentElement().removeChild(mask);
	}

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

		mask.setPropertyString("className", "rismile-mask");
		super.setWidget(panel);
		setStyleName("rismile-dialog");

		Grid head = new Grid(1,2);

		head.setWidth("100%");
//		head.setHeight("25px");

		head.setWidget(0, 1, close);
		head.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
		head.getCellFormatter().setWidth(0, 1, "22px");
		head.setStyleName("caption");
		panel.setWidget(0, 0, head);

		// close.addStyleName("user-close-button");
		close.setStylePrimaryName("close");
		close.addClickListener(this);
		close.setTabIndex(103);

		// head.add(caption, DockPanel.WEST);
		// head.setCellVerticalAlignment(caption,
		// HasVerticalAlignment.ALIGN_MIDDLE);
		head.setWidget(0, 0, caption);
		head.getCellFormatter().setVerticalAlignment(0, 0,
				HasVerticalAlignment.ALIGN_BOTTOM);
		caption.setWidth("100%");
		caption.addMouseListener(this);
		// panel.setWidget(0, 0, caption);

		panel.setHeight("100%");
		panel.setBorderWidth(0);
		panel.setCellPadding(0);
		panel.setCellSpacing(0);

		// panel.setWidget(0, 1, close);
		// panel.getCellFormatter().setHorizontalAlignment(0, 1,
		// HasHorizontalAlignment.ALIGN_RIGHT);

		panel.getFlexCellFormatter().setColSpan(1, 0, 2);
		panel.getCellFormatter().setHeight(1, 0, "100%");
		panel.getCellFormatter().setWidth(1, 0, "100%");
		panel.getCellFormatter().setAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER,
				HasVerticalAlignment.ALIGN_MIDDLE);

		// caption.setStyleName("Caption");

	}

	public String getHTML() {
		return caption.getHTML();
	}

	public String getText() {
		return caption.getText();
	}

	public boolean onEventPreview(Event event) {
		// We need to preventDefault() on mouseDown events (outside of the
		// DialogBox content) to keep text from being selected when it
		// is dragged.
		if (DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
			if (DOM.isOrHasChild(caption.getElement(), DOM
					.eventGetTarget(event))) {
				DOM.eventPreventDefault(event);
			}
		}

		return super.onEventPreview(event);
	}

	public void onMouseDown(Widget sender, int x, int y) {
		dragging = true;
		DOM.setCapture(caption.getElement());
		dragStartX = x;
		dragStartY = y;
	}

	public void onMouseEnter(Widget sender) {
	}

	public void onMouseLeave(Widget sender) {
	}

	public void onMouseMove(Widget sender, int x, int y) {
		if (dragging) {
			int absX = x + getAbsoluteLeft();
			int absY = y + getAbsoluteTop();
			setPopupPosition(absX - dragStartX, absY - dragStartY);
		}
	}

	public void onMouseUp(Widget sender, int x, int y) {
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

	public void hide() {
		super.hide();
		unmask();
	}

	public void onClick(Widget sender) {
		hide();
	}
}
