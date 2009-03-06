package com.risetek.rismile.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class SearchBar extends Composite{
	public final ListBox searchListBox = new ListBox();
	final Label equalLabel = new Label("=");
	final TextBox inputTextBox = new TextBox();
	final Button searchButton = new Button();
	final Button backButton = new Button();
	public SearchBar() {

		final DockPanel dockPanel = new DockPanel();
		initWidget(dockPanel);
		dockPanel.setStyleName("rismile-search-bar");
		dockPanel.add(searchListBox, DockPanel.WEST);
		dockPanel.setCellWidth(searchListBox, "4em");

		dockPanel.add(equalLabel, DockPanel.WEST);
		dockPanel.setCellWidth(equalLabel, "1em");

		dockPanel.add(inputTextBox, DockPanel.WEST);
		dockPanel.setCellWidth(inputTextBox, "6em");

		dockPanel.add(searchButton, DockPanel.CENTER);
		searchButton.setText("查询");
		searchButton.addStyleName("rismile-Tool-Button");
		searchButton.setWidth("6em");

		dockPanel.add(backButton, DockPanel.EAST);
		backButton.setText("退出查询");
		backButton.addStyleName("rismile-Tool-Button");
		backButton.setWidth("6em");
		backButton.setVisible(false);
	}
}
