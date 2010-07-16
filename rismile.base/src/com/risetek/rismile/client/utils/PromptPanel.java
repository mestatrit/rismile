package com.risetek.rismile.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

public class PromptPanel extends Composite {

	private static PromptPanelUiBinder uiBinder = GWT
			.create(PromptPanelUiBinder.class);

	interface PromptPanelUiBinder extends UiBinder<Widget, PromptPanel> {
	}

	@UiField FlowPanel body;
	
	Grid main = new Grid(1, 1);
	public PromptPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		main.setWidth("170px");
//		main.getCellFormatter().setStyleName(0, 0, "prompt-title");
		main.getCellFormatter().setHeight(0, 0, "32px");
		main.setCellPadding(0);
		main.setCellSpacing(0);
		main.getCellFormatter().setStyleName(0, 0, "prompt-message");
		body.add(main);
	}

	public void setTitleText(String titleText){
//		main.setWidget(0, 0, new HTML(titleText));
	}
	
	public void setBody(Widget widget){
		main.setWidget(0, 0, widget);
	}
}
