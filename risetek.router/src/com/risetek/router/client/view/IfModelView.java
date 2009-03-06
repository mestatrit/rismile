package com.risetek.router.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.risetek.router.client.model.IfModel;

public class IfModelView extends Composite {
	final Grid pppGrid = new Grid();
	final Grid lcpGrid = new Grid();
	final Grid compressGrid = new Grid();
	final Grid timeoutGrid = new Grid();
	final Grid natGrid = new Grid();
	final Grid routerGrid = new Grid();
	final Grid dnsGrid = new Grid();
	final Grid linkGrid = new Grid();
	final Grid pptpGrid = new Grid();
	final Button editButton = new Button();
	final Button delButton = new Button();

	public IfModelView() {

		final VerticalPanel outerPanel = new VerticalPanel();
		initWidget(outerPanel);
		setWidth("100%");

		final HorizontalPanel mainPanel = new HorizontalPanel();
		mainPanel.setWidth("100%");
		outerPanel.add(mainPanel);

		final VerticalPanel leftPanel = new VerticalPanel();
		leftPanel.setStyleName("if-layout");
		mainPanel.add(leftPanel);
		mainPanel.setCellWidth(leftPanel, "90%");

		final Label pppLabel = new Label("PPP");
		leftPanel.add(pppLabel);

		leftPanel.add(pppGrid);
		pppGrid.resize(1, 1);
		pppGrid.setStyleName("if-Grid");
		
		final Label lcpLabel = new Label("LCP");
		leftPanel.add(lcpLabel);

		leftPanel.add(lcpGrid);
		lcpGrid.resize(1, 1);
		lcpGrid.setStyleName("if-Grid");

		final Label compressLabel = new Label("compress");
		leftPanel.add(compressLabel);

		leftPanel.add(compressGrid);
		compressGrid.resize(1, 1);
		compressGrid.setStyleName("if-Grid");

		final Label timeoutLabel = new Label("timeout");
		leftPanel.add(timeoutLabel);

		leftPanel.add(timeoutGrid);
		timeoutGrid.resize(1, 1);
		timeoutGrid.setStyleName("if-Grid");

		final Label natLabel = new Label("nat");
		leftPanel.add(natLabel);

		leftPanel.add(natGrid);
		natGrid.resize(1, 1);
		natGrid.setStyleName("if-Grid");

		//final VerticalPanel rightPanel = new VerticalPanel();
		//rightPanel.setStyleName("if-layout");
		//mainPanel.add(rightPanel);
		//mainPanel.setCellWidth(rightPanel, "50%");

		final Label routerLabel = new Label("router");
		leftPanel.add(routerLabel);

		leftPanel.add(routerGrid);
		routerGrid.resize(1, 1);
		routerGrid.setStyleName("if-Grid");

		final Label dnsLabel = new Label("dns");
		leftPanel.add(dnsLabel);

		leftPanel.add(dnsGrid);
		dnsGrid.resize(1, 1);
		dnsGrid.setStyleName("if-Grid");

		final Label linkLabel = new Label("link");
		leftPanel.add(linkLabel);

		leftPanel.add(linkGrid);
		linkGrid.resize(1, 1);
		linkGrid.setStyleName("if-Grid");

		/*final Label pptpLabel = new Label("pptp");
		leftPanel.add(pptpLabel);

		leftPanel.add(pptpGrid);
		pptpGrid.resize(1, 1);
		pptpGrid.setStyleName("if-Grid");*/

		final HorizontalPanel toolPanel = new HorizontalPanel();
		outerPanel.add(toolPanel);

		toolPanel.add(editButton);
		editButton.setWidth("2cm");
		editButton.setText("编辑");
		
		toolPanel.add(delButton);
		delButton.setWidth("2cm");
		delButton.setText("删除");
	}

	public IfModelView(IfModel ifModel){
		this();
		this.ifModel = ifModel;
		fillGrids();
	}
	
	private IfModel ifModel;
	
	private void fillGrids(){
		
		Element element = ifModel.getIfElement();
		
		fillPPPGrid(pppGrid, element.getElementsByTagName("ppp"));
		fillGrid(lcpGrid, element.getElementsByTagName("lcp"));
		//fillCompressGrid(compressGrid, element.getElementsByTagName("compress"));
		fillGrid(timeoutGrid, element.getElementsByTagName("timeout"));
		//fillGrid(natGrid, element.getElementsByTagName("nat"));
		fillGrid(routerGrid, element.getElementsByTagName("router"));
		fillGrid(dnsGrid, element.getElementsByTagName("dns"));
		fillGrid(linkGrid, element.getElementsByTagName("link"));
		
	}
	
	private void fillGrid(Grid grid, NodeList nodeList){
		if(nodeList == null || nodeList.getLength() == 0) return;
		
		Element element = (Element)nodeList.item(0);
		NodeList childList = element.getChildNodes();
		int length = childList.getLength();
		
		if(length < 2){
			grid.resize(length, 2);
		
			for(int i = 0; i < length; i++){
				Element child = (Element)childList.item(i);
				grid.setText(i, 0, child.getTagName());
				grid.setText(i, 1, child.getFirstChild().getNodeValue());
			}
		}else{
			grid.resize(2, length);
			
			for(int i = 0; i < length; i++){
				Element child = (Element)childList.item(i);
				grid.setText(0, i, child.getTagName());
				grid.setText(1, i, child.getFirstChild().getNodeValue());
			}
		}
	
		
	}
	private void fillPPPGrid(Grid grid, NodeList nodeList){
		
		if(nodeList == null || nodeList.getLength() == 0) return;
		
		Element element = (Element)nodeList.item(0);
		//NodeList childList = element.getChildNodes();
		//int length = childList.getLength();
		grid.resize(2, 3);
		
		String username = ((Element)element.getElementsByTagName("username").item(0)).getFirstChild().getNodeValue();
		String pwd = ((Element)element.getElementsByTagName("password").item(0)).getFirstChild().getNodeValue();
		grid.setText(0, 0, "username");
		grid.setText(1, 0, username);
		grid.setText(0, 1, "password");
		grid.setText(1, 1, pwd);
		
		String auth = null;
		
		NodeList authList = element.getElementsByTagName("authentication");
		for(int i = 0; i < authList.getLength(); i++){
			String value = ((Element)authList.item(i)).getFirstChild().getNodeValue();
			if(value.equals("Acceptable")){
				String type = ((Element)authList.item(i)).getAttribute("type");
				auth = auth == null ? type : auth + ","+type;
			}
		}
		grid.setText(0, 2, "authentication");
		grid.setText(1, 2, auth);
		
	}
	private void fillCompressGrid(Grid grid, NodeList nodeList){
		
		if(nodeList == null || nodeList.getLength() == 0) return;
		Element element = (Element)nodeList.item(0);
		
		String mppcOption = ((Element)element.getElementsByTagName("mppc").item(0)).getAttribute("option");
		if(mppcOption.equals("disable")){
			grid.resize(1, 1);
			grid.setText(0, 0, "无");
		}else{
			String stateless = ((Element)element.getElementsByTagName("stateless").item(0)).getFirstChild().getNodeValue();
			if(stateless.equals("disable")){
				grid.resize(1, 2);
				grid.setText(0, 0, "compress");
				grid.setText(0, 1, "mppc");
			}else{
				grid.resize(1, 3);
				grid.setText(0, 0, "compress");
				grid.setText(0, 1, "mppc");
				grid.setText(0, 2, "stateless");
			}
		}
	}
	public Button getEditButton(){
		return editButton;
	}
	
	public Button getDelButton(){
		return delButton;
	}

}
