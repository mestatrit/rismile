package com.risetek.router.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.view.IView;
import com.risetek.rismile.system.client.view.SystemView.Images;
import com.risetek.router.client.control.IfController;
import com.risetek.router.client.model.IfModel;

public class InterfaceView2 extends Composite implements IView{
	final Button addIfButton = new Button();
	final StackPanel ifStackPanel = new StackPanel();
	final Label infoLabel = new Label("还没有配置接口。");

	public InterfaceView2() {

		final VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		//verticalPanel.addStyleName("outer-panel");

		verticalPanel.add(addIfButton);
		addIfButton.setText("添加接口");
		addIfButton.setWidth("3cm");
		addIfButton.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				String ifcmd = "interface=interface Dialer 0";
				String commands = "commands=link type pppoe iface eth0";
				ifController.setDialer(new AddIfAction(), ifcmd + "&" +commands);
				addIfButton.setEnabled(false);
			}
			
		});
		
		verticalPanel.add(ifStackPanel);
		ifStackPanel.setWidth("100%");
		ifStackPanel.setStyleName("sys-stack-panel");
		
		verticalPanel.add(infoLabel);
		infoLabel.setVisible(false);
		infoLabel.setStyleName("if-info");
	}
	private int nextHeaderIndex = 0;
	public IfController ifController = new IfController();
	private List<IfModel> ifModelList = new ArrayList<IfModel>();
	private static final Images images = (Images) GWT.create(Images.class);
	
	protected void onLoad(){
		loadModel();
	}
	
	public void loadModel(){
		ifController.getIf(new IfModelAction());
	}
	class AddIfAction extends ViewAction{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			super.onSuccess();
			ifModelList = new ArrayList<IfModel>();
			loadModel();
		}
		
	}
	class IfModelAction extends ViewAction{

		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			boolean hasIf = false;
			super.onSuccess();
			ifModelList = new ArrayList<IfModel>();
			ifStackPanel.clear();
			if(object instanceof List){
				List list = (List)object; 
				for(int i = 0; i < list.size(); i++){
					Object model = list.get(i);
					if(model instanceof IfModel){
						IfModel ifModel = (IfModel)model;
						HTML title = new HTML(ifModel.getName());
						title.setStyleName("systool-title");
						IfModelView2 ifModelView2 = new IfModelView2(ifModel, ifController, InterfaceView2.this); 
						ifStackPanel.add(ifModelView2, createHeaderHTML(images, title.toString()), true);
						//ifStackPanel.add(ifModelView2, title.toString(), true);
						ifModelList.add(ifModel);
						hasIf = true;
					}
				}
			}
			addIfButton.setVisible(!hasIf);
			infoLabel.setVisible(!hasIf);
			
		}
		
	}
	
	private String createHeaderHTML(Images images, String caption) {
		Grid captionGrid = new Grid();
		captionGrid.setWidth("100%");
		captionGrid.resize(1, 2);

		HTML captionHtml = new HTML(caption);
		captionHtml.setStyleName("stack-caption");
		HTML figureHtml = new HTML("&nbsp;");
		figureHtml.setStyleName("stack-figure");

		captionGrid.setWidget(0, 0, captionHtml);
		captionGrid.getCellFormatter().setWidth(0, 0, "90%");
		captionGrid.getCellFormatter().setVerticalAlignment(0, 0,
				HasVerticalAlignment.ALIGN_TOP);
		captionGrid.setWidget(0, 1, figureHtml);
		captionGrid.getCellFormatter().setWidth(0, 1, "10%");
		captionGrid.getCellFormatter().setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);

		String innerHtml = DOM.getInnerHTML(captionGrid.getElement());

		boolean isTop = (nextHeaderIndex == 0);
		nextHeaderIndex++;

		String captionInnerHtml = "<table class='caption' cellpadding='0' cellspacing='0'>"
				+ "<tr><td class='lcaption'>"
				+ "</td><td class='rcaption'><b style='white-space:nowrap'>"
				+ innerHtml + "</b></td></tr></table>";

		return "<table align='left' cellpadding='0' cellspacing='0'"
				+ (isTop ? " class='is-top'" : "") + "><tbody>"
				+ "<tr><td class='box-00'>" + images.leftCorner().getHTML()
				+ "</td>" + "<td class='box-10'>&nbsp;</td>"
				+ "<td class='box-20'>" + images.rightCorner().getHTML()
				+ "</td>" + "</tr><tr>" + "<td class='box-01'>&nbsp;</td>"
				+ "<td class='box-11'>" + captionInnerHtml + "</td>"
				+ "<td class='box-21'>&nbsp;</td>" + "</tr></tbody></table>";
	}
}
