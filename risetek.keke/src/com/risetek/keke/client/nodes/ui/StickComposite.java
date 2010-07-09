package com.risetek.keke.client.nodes.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.resources.IconManage;
import com.risetek.keke.client.ui.UiKeke;

public class StickComposite extends Composite {

	// TODO: 个点的其它地方装饰。
	Grid outgrid = new Grid(3, 3);
	DockPanel outPanel = new DockPanel();

	public StickComposite(Stick node) {
		this(node, true);
	}

	public StickComposite(Stick node, boolean a) {
		if (a) {
			outgrid.getCellFormatter().addStyleName(0, 0, "topleft");
			outgrid.getCellFormatter().addStyleName(0, 1, "topmiddler");
			outgrid.getCellFormatter().addStyleName(0, 2, "topright");

			outgrid.getCellFormatter().addStyleName(1, 0, "midleft");
			outgrid.getCellFormatter().addStyleName(1, 1, "midmiddler");
			outgrid.getCellFormatter().setStyleName(1, 2, "midright");

			outgrid.getCellFormatter().addStyleName(2, 0, "bottomleft");
			outgrid.getCellFormatter().addStyleName(2, 1, "bottommiddler");
			outgrid.getCellFormatter().addStyleName(2, 2, "bottomright");
		} else {
			outgrid.getCellFormatter().addStyleName(0, 0, "atopleft");
			outgrid.getCellFormatter().addStyleName(0, 1, "atopmiddler");
			outgrid.getCellFormatter().addStyleName(0, 2, "atopright");

			outgrid.getCellFormatter().addStyleName(1, 0, "amidleft");
			outgrid.getCellFormatter().addStyleName(1, 1, "amidmiddler");
			outgrid.getCellFormatter().setStyleName(1, 2, "amidright");

			outgrid.getCellFormatter().addStyleName(2, 0, "abottomleft");
			outgrid.getCellFormatter().addStyleName(2, 1, "abottommiddler");
			outgrid.getCellFormatter().addStyleName(2, 2, "abottomright");
		}
		outgrid.setCellPadding(0);
		outgrid.setCellSpacing(0);
		outgrid.setWidth("100%");

		outgrid.setHTML(0, 0, "");
		outgrid.setHTML(0, 1, "");
		outgrid.setHTML(0, 2, "");

		outgrid.setHTML(2, 0, "");
		outgrid.setHTML(2, 1, "");
		outgrid.setHTML(2, 2, "");
		outgrid.setHeight(UiKeke.kekeHeight + "px");

		outgrid.setWidget(1, 1, outPanel);
		outPanel.setWidth("100%");
		outPanel.setHeight("100%");

		// ICON 的处理。
		if (node != null) {
			Image img = new Image();
			img.setResource(IconManage.getIcon(node.imgName));
			outPanel.add(img, DockPanel.WEST);
			outPanel.setCellWidth(img, "70px");
			outPanel.setCellHorizontalAlignment(img,
					HasHorizontalAlignment.ALIGN_CENTER);
			outPanel.setCellVerticalAlignment(img,
					HasVerticalAlignment.ALIGN_MIDDLE);

		}
		initWidget(outgrid);
		getElement().setAttribute("overflow", "hidden");
		setStyleName("stick");
	}
}
