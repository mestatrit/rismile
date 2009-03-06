package com.risetek.rismile.log.client.view;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.view.MouseEventGrid;
import com.risetek.rismile.client.view.RismileTableView;
import com.risetek.rismile.log.client.control.RismileLogController;

public class RismileLogView extends RismileTableView {

	private boolean autoRefresh = true;
	private Button TogAutoRefresh;
	private Button clearButton = new Button("清除");
	boolean showing = false;
	public RismileLogController logController = new RismileLogController();

	public RismileLogView(String[] columns, String[] columnStyles, int rowCount) {
		super(columns, columnStyles, rowCount);
		TogAutoRefresh = new Button("", new AutoRefreshClick());
		super.addToolButton(TogAutoRefresh);
		TogAutoRefresh.addStyleName("rismile-Tool-Button");

		Button downloadButton = new Button("导出文件");
		super.addToolButton(downloadButton);
		downloadButton.addStyleName("rismile-Tool-Button");
		downloadButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Window.open("forms/exportlog", "_self", "");
			}

		});
		super.addToolButton(clearButton);
		clearButton.addStyleName("rismile-Tool-Button");
		clearButton.addClickListener(new ClearLogAction());
		//searchBar.searchListBox.addItem("主机", "HOST");
		//searchBar.searchListBox.addItem("类型", "TYPE");
		//searchBar.searchListBox.setSelectedIndex(0);
	}

	Timer refreshTimer = new Timer() {
		public void run() {
			update();
		}
	};

	public Grid getGrid() {
		return new MouseEventGrid() {
			public void onMouseOut(Element td, int column) {
			}

			public void onMouseOver(Element td, int column) {
			}
		};
	}

	public void start_refresh() {
		showing = true;
		refreshTimer.run();
	}

	public void update() {

		TogAutoRefresh.setText(autoRefresh ? "查看历史" : "自动更新");
		if (autoRefresh && showing) {
			loadModel();
			refreshTimer.schedule(10000);
		}
	}

	public void stop_refresh() {
		showing = false;
	}

	public void replaneNavBar(int total) {
		if (!autoRefresh)
			super.enabledNavBar(total);
	}


	public void loadModel() {
		logController.load( rowCount, startRow, new LoadTableAction());
	}
	
	class AutoRefreshClick implements ClickListener{

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			autoRefresh = !autoRefresh;
			if (autoRefresh) {
				TogAutoRefresh.setText("查看历史");
				setStartRow(0);
				update();
			} else {
				TogAutoRefresh.setText("自动更新");
				loadModel();
			}
		}	
	}
	
	class ClearLogAction extends ViewAction implements ClickListener {

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			if (Window.confirm("是否要清除日志?")) {
				clearButton.setEnabled(false);
				logController.emptyTable(this);
			}
		}

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			clearButton.setEnabled(true);
			loadModel();
		}
		
	}
}
