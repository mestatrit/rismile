package com.risetek.rismile.client.dialog;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;

public abstract class MyDialog extends DialogBox {

	//private HTML caption = new HTML();
	//protected Button close = new Button();
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

		setWidget(panel);
		//setStyleName("rismile-dialog");

		//close.setStylePrimaryName("close");
		//close.setTabIndex(103);

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


}
