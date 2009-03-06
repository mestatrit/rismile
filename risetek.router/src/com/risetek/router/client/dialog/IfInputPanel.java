package com.risetek.router.client.dialog;

import java.util.List;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.risetek.router.client.model.IfModel;

public class IfInputPanel extends Composite implements ChangeListener{
	final TextBox dialerTextBox = new TextBox();
	final Label   dialerInfo  = new Label();
	final TextBox nameTextBox = new TextBox();
	final Label   nameInfo = new Label();
	final PasswordTextBox pwdTextBox = new PasswordTextBox();
	final Label   pwdInfo = new Label();
	final PasswordTextBox repwdTextBox = new PasswordTextBox();
	final Label   repwdInfo = new Label();
	final CheckBox chapmd5CheckBox = new CheckBox();
	final CheckBox eapCheckBox = new CheckBox();
	final CheckBox mschapCheckBox = new CheckBox();
	final CheckBox papCheckBox = new CheckBox();
	final TextBox keepaliveTextBox = new TextBox();
	final Label   keepaliveInfo = new Label();
	final TextBox redialTextBox = new TextBox();
	final Label   redialInfo = new Label();
	public IfInputPanel(List<String> dialerNameList) {
		final VerticalPanel outerPanel = new VerticalPanel();
		initWidget(outerPanel);
		outerPanel.setWidth("100%");
		
		final Grid ifCmdGrid = new Grid();
		ifCmdGrid.resize(1, 3);
		ifCmdGrid.setStyleName("ifdialog-Grid");
		ifCmdGrid.getColumnFormatter().setWidth(0, "20%");
		ifCmdGrid.getColumnFormatter().setWidth(1, "30%");
		ifCmdGrid.getColumnFormatter().setWidth(2, "50%");
		
		outerPanel.add(ifCmdGrid);
		final Label dialerLabel = new Label("Dialer: ");
		ifCmdGrid.setWidget(0, 0, dialerLabel);
		ifCmdGrid.setWidget(0, 1, dialerTextBox);
		dialerTextBox.addChangeListener(this);
		ifCmdGrid.setWidget(0, 2, dialerInfo);
		dialerInfo.setStyleName("ifdialog-info");
		
		final DecoratedStackPanel decoratedStackPanel = new DecoratedStackPanel();
		outerPanel.add(decoratedStackPanel);
		decoratedStackPanel.setWidth("100%");
		decoratedStackPanel.setHeight("100%");

		final VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setWidth("100%");
		decoratedStackPanel.add(verticalPanel_1, "PPP");
		verticalPanel_1.setStyleName("ifdialog-ppp-layout");

		final Label label_1 = new Label("user");
		verticalPanel_1.add(label_1);

		//final Label pppLabel = new Label("PPP");
		//verticalPanel_1.add(pppLabel);

		final Grid pppGrid = new Grid();
		verticalPanel_1.add(pppGrid);
		pppGrid.setStyleName("ifdialog-Grid");
		pppGrid.resize(3, 3);
		
		pppGrid.getColumnFormatter().setWidth(0, "20%");
		pppGrid.getColumnFormatter().setWidth(1, "30%");
		pppGrid.getColumnFormatter().setWidth(2, "50%");
		pppGrid.setText(0, 0, "用户名");
		pppGrid.setText(1, 0, "密码");
		pppGrid.setText(2, 0, "重复密码");

		pppGrid.setWidget(0, 1, nameTextBox);
		nameTextBox.setWidth("100%");

		pppGrid.setWidget(1, 1, pwdTextBox);
		pwdTextBox.setWidth("100%");
		
		pppGrid.setWidget(2, 1, repwdTextBox);
		repwdTextBox.setWidth("100%");
		repwdTextBox.addChangeListener(this);

		pppGrid.setWidget(0, 2, nameInfo);
		pppGrid.setWidget(1, 2, pwdInfo);
		pppGrid.setWidget(2, 2, repwdInfo);
		
		nameInfo.setStyleName("ifdialog-info");
		pwdInfo.setStyleName("ifdialog-info");
		repwdInfo.setStyleName("ifdialog-info");
		
		final Label label = new Label("authentication");
		verticalPanel_1.add(label);

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel);
		horizontalPanel.setStyleName("ifdialog-auth-layout");
		
		horizontalPanel.add(chapmd5CheckBox);
		chapmd5CheckBox.setText("chapmd5");

		horizontalPanel.add(eapCheckBox);
		eapCheckBox.setText("eap");

		horizontalPanel.add(mschapCheckBox);
		mschapCheckBox.setText("ms-chap");

		horizontalPanel.add(papCheckBox);
		papCheckBox.setText("pap");

		final Label label_2 = new Label("LCP");
		verticalPanel_1.add(label_2);

		final Grid lcpGrid = new Grid();
		verticalPanel_1.add(lcpGrid);
		lcpGrid.setStyleName("ifdialog-Grid");
		lcpGrid.resize(2, 3);
		lcpGrid.getColumnFormatter().setWidth(0, "20%");
		lcpGrid.getColumnFormatter().setWidth(1, "30%");
		lcpGrid.getColumnFormatter().setWidth(2, "50%");
		final Label label_3 = new Label("keepalive");
		lcpGrid.setWidget(0, 0, label_3);

		lcpGrid.setWidget(0, 1, keepaliveTextBox);
		keepaliveTextBox.setWidth("100%");

		final Label label_4 = new Label("redial");
		lcpGrid.setWidget(1, 0, label_4);

		lcpGrid.setWidget(1, 1, redialTextBox);
		redialTextBox.setWidth("100%");

		lcpGrid.setWidget(0, 2, keepaliveInfo);
		lcpGrid.setWidget(1, 2, redialInfo);
		keepaliveInfo.setStyleName("ifdialog-info");
		redialInfo.setStyleName("ifdialog-info");
		
		final VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setWidth("100%");
		decoratedStackPanel.add(verticalPanel_2, "LINK");

		//final Label linkLabel = new Label("link");
		//verticalPanel_2.add(linkLabel);

		final Grid linkGrid = new Grid();
		verticalPanel_2.add(linkGrid);
		linkGrid.setStyleName("ifdialog-Grid");
		linkGrid.resize(2, 3);

		final Label mtuLabel = new Label("mtu");
		linkGrid.setWidget(0, 0, mtuLabel);

		final Label mruLabel = new Label("mru");
		linkGrid.setWidget(0, 1, mruLabel);

		final Label mrruLabel = new Label("mrru");
		linkGrid.setWidget(0, 2, mrruLabel);

		final TextBox mtuTextBox = new TextBox();
		linkGrid.setWidget(1, 0, mtuTextBox);
		mtuTextBox.setWidth("100%");

		final TextBox mruTextBox = new TextBox();
		linkGrid.setWidget(1, 1, mruTextBox);
		mruTextBox.setWidth("100%");

		final TextBox mrruTextBox = new TextBox();
		linkGrid.setWidget(1, 2, mrruTextBox);
		mrruTextBox.setWidth("100%");

		final VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_3.setWidth("100%");
		decoratedStackPanel.add(verticalPanel_3, "ROUTER");

		//final Label routerLabel = new Label("router");
		//verticalPanel_3.add(routerLabel);

		final Grid routerGrid = new Grid();
		verticalPanel_3.add(routerGrid);
		routerGrid.setStyleName("ifdialog-Grid");
		routerGrid.resize(2, 2);

		final Label ipLabel = new Label("ip");
		routerGrid.setWidget(0, 0, ipLabel);

		final Label maskLabel = new Label("mask");
		routerGrid.setWidget(0, 1, maskLabel);

		final TextBox ipTextBox = new TextBox();
		routerGrid.setWidget(1, 0, ipTextBox);
		ipTextBox.setWidth("100%");

		final TextBox maskTextBox = new TextBox();
		routerGrid.setWidget(1, 1, maskTextBox);
		maskTextBox.setWidth("100%");

		final VerticalPanel verticalPanel_4 = new VerticalPanel();
		verticalPanel_4.setWidth("100%");
		decoratedStackPanel.add(verticalPanel_4, "NAT");

		//final Label natLabel = new Label("nat");
		//verticalPanel_4.add(natLabel);

		final Grid natGrid = new Grid();
		verticalPanel_4.add(natGrid);
		natGrid.setStyleName("ifdialog-Grid");
		natGrid.resize(1, 2);

		final Label dmzLabel = new Label("dmz");
		natGrid.setWidget(0, 0, dmzLabel);
		natGrid.getCellFormatter().setWidth(0, 0, "50%");

		final TextBox dmzTextBox = new TextBox();
		natGrid.setWidget(0, 1, dmzTextBox);
		dmzTextBox.setWidth("100%");
		
		this.dialerNameList = dialerNameList;
	}

	public IfInputPanel(IfModel ifModel){
		this((List<String>)null);
		this.ifModel = ifModel;
		String num = ifModel.getName().substring("Dialer".length());
		dialerTextBox.setText(num);
		dialerTextBox.setEnabled(false);
		showModel();
	}
	private IfModel ifModel;
	private List<String> dialerNameList;
	
	private void showModel(){
		
		Element element = ifModel.getIfElement();
		
		showPPP(element.getElementsByTagName("ppp"));
		//fillCompressGrid(compressGrid, element.getElementsByTagName("compress"));
		//fillGrid(timeoutGrid, element.getElementsByTagName("timeout"));
		//fillGrid(natGrid, element.getElementsByTagName("nat"));
		//fillGrid(routerGrid, element.getElementsByTagName("router"));
		//fillGrid(dnsGrid, element.getElementsByTagName("dns"));
		//fillGrid(linkGrid, element.getElementsByTagName("link"));
		
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
	private void showPPP(NodeList nodeList){
		
		if(nodeList == null || nodeList.getLength() == 0) return;
		
		Element element = (Element)nodeList.item(0);
		//NodeList childList = element.getChildNodes();
		//int length = childList.getLength();
		
		String username = ((Element)element.getElementsByTagName("username").item(0)).getFirstChild().getNodeValue();
		nameTextBox.setText(username);
		
		NodeList authList = element.getElementsByTagName("authentication");
		for(int i = 0; i < authList.getLength(); i++){
			String value = ((Element)authList.item(i)).getFirstChild().getNodeValue();
			if(value.equals("Acceptable")){
				String type = ((Element)authList.item(i)).getAttribute("type");
				if(type.equals("chapmd5")){
					chapmd5CheckBox.setChecked(true);
				}else if(type.equals("eap")){
					eapCheckBox.setChecked(true);
				}else if(type.equals("ms-chap")){
					mschapCheckBox.setChecked(true);
				}else if(type.equals("pap")){
					papCheckBox.setChecked(true);
				}
			}
		}
		
	}
	public String getInfCmd(){
		
		String text = null;
		if(dialerTextBox.getText() != null && !dialerTextBox.getText().trim().equals("")){
			text = "interface Dialer " + dialerTextBox.getText().trim();
		}
		return text;
	}
	public String getCommands(){
		String ifSetting = null;
		
		//int num = Integer.parseInt(ifModel.getName().substring("Dialer".length()));
		//ifSetting = "interface Dialer " + num + "\r\n";
		String nameText = nameTextBox.getText();
		String nameTextTrim = nameTextBox.getText().trim();
		if(nameTextBox.getText() != null && !nameTextBox.getText().trim().equals("")){
			ifSetting = "ppp username " + nameTextBox.getText();
		}
		if(pwdTextBox.getText() != null && !pwdTextBox.getText().trim().equals("")){
			ifSetting += ";ppp password " + pwdTextBox.getText();
		}
		String auth  = null;
		if(chapmd5CheckBox.isChecked()){
			auth = "chap";
		}
		if(eapCheckBox.isChecked()){
			auth = auth == null ? "eap" : auth + " eap";
		}
		if(mschapCheckBox.isChecked()){
			auth = auth == null ? "ms-chap" : auth + " ms-chap";
		}
		if(papCheckBox.isChecked()){
			auth = auth == null ? "pap" : auth + " pap";
		}
		if(auth != null){
			ifSetting += ";ppp authentication " + auth;
		}
		
		if(keepaliveTextBox.getText() != null && !keepaliveTextBox.getText().trim().equals("")){
			ifSetting += ";lcp keepalive " + keepaliveTextBox.getText().trim();
		}
		if(redialTextBox.getText() != null && !redialTextBox.getText().trim().equals("")){
			ifSetting += ";lcp redial " + redialTextBox.getText().trim();
		}
		return ifSetting;
	}
	
	String digitalPattern ="^\\d+$";
	
	private String checkDigital(String digital, int min, int max, boolean emptiable){
		String result = null;
		
		if(digital == null || digital.equals("")){
			if(!emptiable) result = "数字不能为空";
		}else if(!digital.matches(digitalPattern)){
			result = "包含了非数字字符";
		}else if(Integer.parseInt(digital) > max || Integer.parseInt(digital) < min){
			int t = Integer.parseInt(digital);
			result = "数值不在允许的范围内";
		}
		return result;
	}
	
	private String checkPassword(){
		String result = null;
		
		if(!pwdTextBox.getText().equals(repwdTextBox.getText())){
			result = "两次输入的密码不符,请重新输入!";
		}
		return result;
	}
	private String checkDialerName(){
		String result = null;
		
		if(dialerTextBox.isEnabled() && dialerNameList != null && dialerNameList.size() > 0){
			String dialerName = "Dialer" + dialerTextBox.getText().trim();
			for(int i = 0; i < dialerNameList.size(); i++){
				if(dialerNameList.get(i).equals(dialerName)){
					result = dialerName + "已经存在！";
					break;
				}
			}
		}
		return result;
	}
	public boolean checkAll(){
		
		boolean result = true;

		String check = checkDigital(dialerTextBox.getText().trim(), 0, 255, false);
		
		if(check != null){
			dialerInfo.setText(check);
			result = false;
		}else if((check = checkDialerName()) != null){
			dialerInfo.setText(check);
			result = false;
		}else{
			dialerInfo.setText("");
		}
		check = checkPassword();
		if(check != null){
			repwdInfo.setText(check);
			result = false;
		}else{
			repwdInfo.setText("");
		}
		if(nameTextBox.getText().equals("") && !pwdTextBox.getText().equals("")){
			check = "有密码，没有用户名！";
			nameInfo.setText(check);
			result = false;
		}else if(!nameTextBox.getText().equals("") && pwdTextBox.getText().equals("")){
			check = "有用户名，没有密码！";
			pwdInfo.setText(check);
			result = false;
		}else{
			nameInfo.setText("");
			pwdInfo.setText("");
		}
		check = checkDigital(keepaliveTextBox.getText().trim(), 4, 40, true);
		if(check != null){
			keepaliveInfo.setText(check);
			result = false;
		}else{
			keepaliveInfo.setText("");
		}
		
		check = checkDigital(redialTextBox.getText().trim(), 4, 40, true);
		if(check != null){
			redialInfo.setText(check);
			result = false;
		}else{
			redialInfo.setText("");
		}
		
		return result;
	}
	public void onChange(Widget sender) {
		// TODO Auto-generated method stub
		if(sender == dialerTextBox){
			
			String check = checkDigital(dialerTextBox.getText().trim(), 0, 255, false);
			if(check != null){
				dialerInfo.setText(check);
				//Window.alert(check);
				//dialerTextBox.setFocus(true);
			}else{
				dialerInfo.setText("");
			}
		}else if(sender == repwdTextBox){
			
			String check = checkPassword();
			if(check != null){
				repwdInfo.setText(check);
				//Window.alert(check);
			}else {
				repwdInfo.setText("");
			}
		}else if(sender == keepaliveTextBox){
			String check = checkDigital(keepaliveTextBox.getText().trim(), 4, 40, true);
			if(check != null){
				keepaliveInfo.setText(check);
			}else{
				keepaliveInfo.setText("");
			}
		}else if(sender == redialTextBox){
			String check = checkDigital(redialTextBox.getText().trim(), 4, 40, true);
			if(check != null){
				redialInfo.setText(check);
			}else{
				redialInfo.setText("");
			}
		}
	}
	
}
