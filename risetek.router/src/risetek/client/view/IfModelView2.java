package risetek.client.view;

import risetek.client.control.IfController;
import risetek.client.model.IfModel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.risetek.rismile.client.control.ViewAction;
import com.risetek.rismile.client.utils.MessageConsole;
import com.risetek.rismile.client.utils.Validity;

public class IfModelView2 extends Composite implements ChangeListener{
	final TextBox userTextBox = new TextBox();
	final PasswordTextBox passwordTextBox = new PasswordTextBox();
	final PasswordTextBox repasswordTextBox = new PasswordTextBox();
	final Label userLabel = new Label("");
	final Label passwordLabel = new Label("");
	final Label repasswordLabel = new Label("");
	final CheckBox chapmdCheckBox = new CheckBox();
	final CheckBox eapCheckBox = new CheckBox();
	final CheckBox mschapCheckBox = new CheckBox();
	final CheckBox papCheckBox = new CheckBox();
	final CheckBox keepaliveCheckBox = new CheckBox();
	final TextBox keepaliveTextBox = new TextBox();
	final TextBox redialTextBox = new TextBox();
	final Label keepaliveLabel = new Label("");
	final Label redialLabel = new Label("");
	final TextBox mtuTextBox = new TextBox();
	final TextBox mruTextBox = new TextBox();
	final TextBox mrruTextBox = new TextBox();
	final Label mtuLabel = new Label("");
	final Label mruLabel = new Label("");
	final Label mrruLabel = new Label("");
	final CheckBox natCheckBox = new CheckBox();
	final CheckBox ondemandCheckBox = new CheckBox();
	final TextBox idleTextBox = new TextBox();
	final TextBox sessionTextBox = new TextBox();
	final Label idleLabel = new Label("");
	final Label sessionLabel = new Label("");
	final Grid routeListGrid = new Grid();
	final TextBox destTextBox = new TextBox();
	final TextBox maskTextBox = new TextBox();
	final Label destLabel = new Label("");
	final Label maskLabel = new Label("");
	final Button addRouteButton = new Button("添加路由");
	//final RadioButton nocompressRadioButton = new RadioButton("compress-group");
	final CheckBox mppcRadioButton = new CheckBox();
	final CheckBox dnsCheckBox = new CheckBox();
	final CheckBox vjcompCheckBox = new CheckBox();
	final Button comfirmButton = new Button();
	public IfModelView2() {

		final VerticalPanel outerPanel = new VerticalPanel();
		initWidget(outerPanel);
		outerPanel.setStyleName("if-layout");
		
		//final Label pppLabel = new Label("PPP");
		//outerPanel.add(pppLabel);

		final Grid pppGrid = new Grid();
		outerPanel.add(pppGrid);
		pppGrid.resize(3, 3);
		pppGrid.setStyleName("if-Grid");
		pppGrid.getColumnFormatter().setStyleName(0, "head");
		pppGrid.getColumnFormatter().setWidth(0, "30%");
		pppGrid.getColumnFormatter().setWidth(1, "30%");
		pppGrid.getColumnFormatter().setWidth(2, "40%");
		pppGrid.setText(0, 0, "用户名:");
		pppGrid.setText(1, 0, "密码:");
		pppGrid.setText(2, 0, "重复密码:");
		
		pppGrid.setWidget(0, 1, userTextBox);
		pppGrid.setWidget(1, 1, passwordTextBox);
		pppGrid.setWidget(2, 1, repasswordTextBox);
		repasswordTextBox.addChangeListener(this);
		
		pppGrid.setWidget(0, 2, userLabel);
		pppGrid.setWidget(1, 2, passwordLabel);
		pppGrid.setWidget(2, 2, repasswordLabel);

		final HorizontalPanel authPanel = new HorizontalPanel();
		outerPanel.add(authPanel);
		
		final Label authLabel = new Label("认证方式:");
		authPanel.add(authLabel);
		
		authPanel.add(chapmdCheckBox);
		chapmdCheckBox.setText("chapmd5");
		
		authPanel.add(eapCheckBox);
		eapCheckBox.setText("eap");

		authPanel.add(mschapCheckBox);
		mschapCheckBox.setText("ms-chap");

		authPanel.add(papCheckBox);
		papCheckBox.setText("pap");

		final Label lcpLabel = new Label("LCP");
		outerPanel.add(lcpLabel);

		final Grid lcpGrid = new Grid();
		outerPanel.add(lcpGrid);
		lcpGrid.resize(2, 3);
		lcpGrid.setStyleName("if-Grid");
		lcpGrid.getColumnFormatter().setStyleName(0, "head");
		lcpGrid.getColumnFormatter().setWidth(0, "30%");
		lcpGrid.getColumnFormatter().setWidth(1, "30%");
		lcpGrid.getColumnFormatter().setWidth(2, "40%");
		keepaliveCheckBox.setText("keepalive(4-40 seconds):");
		keepaliveCheckBox.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				if(keepaliveCheckBox.isChecked()){
					keepaliveTextBox.setEnabled(true);
				}else{
					keepaliveTextBox.setText("");
					keepaliveTextBox.setEnabled(false);
					keepaliveLabel.setText("");
				}
			}
			
		});
		lcpGrid.setWidget(0, 0, keepaliveCheckBox);
		lcpGrid.setText(1, 0, "redial:");
		
		lcpGrid.setWidget(0, 1, keepaliveTextBox);
		keepaliveTextBox.setWidth("100%");
		keepaliveTextBox.addChangeListener(this);
		
		lcpGrid.setWidget(1, 1, redialTextBox);
		redialTextBox.setWidth("100%");

		lcpGrid.setWidget(0, 2, keepaliveLabel);
		lcpGrid.setWidget(1, 2, redialLabel);

		final Label linkLabel = new Label("LINK");
		outerPanel.add(linkLabel);

		final Grid linkGrid = new Grid();
		outerPanel.add(linkGrid);
		linkGrid.resize(3, 3);
		linkGrid.setStyleName("if-Grid");
		linkGrid.getColumnFormatter().setStyleName(0, "head");
		linkGrid.getColumnFormatter().setWidth(0, "30%");
		linkGrid.getColumnFormatter().setWidth(1, "30%");
		linkGrid.getColumnFormatter().setWidth(2, "40%");
		linkGrid.setText(0, 0, "mtu(296-1500):");
		linkGrid.setText(1, 0, "mru(296-1500):");
		linkGrid.setText(2, 0, "mrru(296-4096):");
		
		linkGrid.setWidget(0, 1, mtuTextBox);
		mtuTextBox.setWidth("100%");
		mtuTextBox.addChangeListener(this);
		
		linkGrid.setWidget(1, 1, mruTextBox);
		mruTextBox.setWidth("100%");
		mruTextBox.addChangeListener(this);
		
		linkGrid.setWidget(2, 1, mrruTextBox);
		mrruTextBox.setWidth("100%");
		mrruTextBox.addChangeListener(this);
		
		linkGrid.setWidget(0, 2, mtuLabel);
		linkGrid.setWidget(1, 2, mruLabel);
		linkGrid.setWidget(2, 2, mrruLabel);

		final Label natLabel = new Label("NAT");
		outerPanel.add(natLabel);

		outerPanel.add(natCheckBox);
		natCheckBox.setText("NAT");

		final Label ondemandLabel = new Label("ON-DEMAND");
		outerPanel.add(ondemandLabel);

		outerPanel.add(ondemandCheckBox);
		ondemandCheckBox.setText("ON-DEMAND");

		final Label timeoutLabel = new Label("TIMEOUT");
		outerPanel.add(timeoutLabel);

		final Grid timeoutGrid = new Grid();
		outerPanel.add(timeoutGrid);
		timeoutGrid.resize(2, 3);
		timeoutGrid.setStyleName("if-Grid");
		timeoutGrid.getColumnFormatter().setStyleName(0, "head");
		timeoutGrid.getColumnFormatter().setWidth(0, "30%");
		timeoutGrid.getColumnFormatter().setWidth(1, "30%");
		timeoutGrid.getColumnFormatter().setWidth(2, "40%");
		timeoutGrid.setText(0, 0, "idle(0-60000):");
		timeoutGrid.setText(1, 0, "session(0-71582787):");

		timeoutGrid.setWidget(0, 1, idleTextBox);
		idleTextBox.setWidth("100%");
		idleTextBox.addChangeListener(this);
		
		timeoutGrid.setWidget(1, 1, sessionTextBox);
		sessionTextBox.setWidth("100%");
		sessionTextBox.addChangeListener(this);
		
		timeoutGrid.setWidget(0, 2, idleLabel);
		timeoutGrid.setWidget(1, 2, sessionLabel);

		

		final Label compressLabel = new Label("COMPRESS");
		outerPanel.add(compressLabel);

		final HorizontalPanel compressPanel = new HorizontalPanel();
		outerPanel.add(compressPanel);

		//compressPanel.add(nocompressRadioButton);
		//nocompressRadioButton.setText("no-compress");

		compressPanel.add(mppcRadioButton);
		mppcRadioButton.setText("mppc");

		final Label ipcpLabel = new Label("IPCP");
		outerPanel.add(ipcpLabel);

		final HorizontalPanel ipcpPanel = new HorizontalPanel();
		outerPanel.add(ipcpPanel);
	
		ipcpPanel.add(dnsCheckBox);
		dnsCheckBox.setText("request_dns");

		ipcpPanel.add(vjcompCheckBox);
		vjcompCheckBox.setText("vjcomp");

		final DockPanel toolPanel = new DockPanel();
		outerPanel.add(toolPanel);
		toolPanel.setStyleName("if-horizontal");
		
		final Button delButton = new Button();
		toolPanel.add(delButton, DockPanel.EAST);
		delButton.setText("删除");
		delButton.setWidth("3cm");
		
		toolPanel.add(comfirmButton, DockPanel.EAST);
		comfirmButton.setText("确定");
		comfirmButton.setWidth("3cm");
		comfirmButton.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				checkAll();
				if(isPass){
					String infCmd = getInfCmd(); 
					String commands = getCommands();
					String text = "interface=" + infCmd + "&commands=" + commands;
					ifController.setDialer(new SettingAction(comfirmButton), text);
				}else {
					Window.alert("输入有错误，请修改！");
				}
			}
			
		});
		
		
		//final Label routeLabel = new Label("ROUTE");
		//outerPanel.add(routeLabel);
		VerticalPanel routePanel = new VerticalPanel();
		routePanel.addStyleName("if-layout");
		
		DisclosurePanel disclosurePanel = new DisclosurePanel("ROUTE");
		outerPanel.add(disclosurePanel);
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.setContent(routePanel);
		disclosurePanel.setWidth("100%");
		
		routePanel.add(routeListGrid);
		routeListGrid.setStyleName("if-Grid");
		
		
		final Grid routeGrid = new Grid();
		routePanel.add(routeGrid);
		routeGrid.resize(3, 3);
		routeGrid.setStyleName("if-Grid");
		routeGrid.getColumnFormatter().addStyleName(0, "head");
		routeGrid.getColumnFormatter().setWidth(0, "30%");
		routeGrid.getColumnFormatter().setWidth(1, "30%");
		routeGrid.getColumnFormatter().setWidth(2, "40%");
		routeGrid.setText(0, 0, "目的地址:");
		routeGrid.setText(1, 0, "掩码:");

		routeGrid.setWidget(0, 1, destTextBox);
		destTextBox.setWidth("100%");
		destTextBox.addChangeListener(this);

		routeGrid.setWidget(1, 1, maskTextBox);
		maskTextBox.setWidth("100%");
		maskTextBox.addChangeListener(this);

		routeGrid.setWidget(0, 2, destLabel);
		routeGrid.setWidget(1, 2, maskLabel);
		
		routeGrid.setWidget(2, 1, addRouteButton);
		addRouteButton.addClickListener(new AddRouteListener());
		addRouteButton.setWidth("3cm");
	}
	public IfModelView2(IfModel ifModel, IfController ifController, InterfaceView2 parent){
		this();
		this.ifModel = ifModel;
		this.ifController = ifController;
		this.parent = parent;
		showModel();
	}
	private IfModel ifModel;
	private IfController ifController;
	private InterfaceView2 parent;
	boolean isPass = true;
	String digitalPattern ="^\\d+$";
	private boolean userChanged = false;
	
	private void showModel(){
		
		Element element = ifModel.getIfElement();
		
		showPPP(element.getElementsByTagName("ppp"));
		showLcp(element.getElementsByTagName("lcp"));
		showLink(element.getElementsByTagName("link"));
		showNat(element.getElementsByTagName("nat"));
		showOndemand(element.getElementsByTagName("on-demand"));
		showTimeout(element.getElementsByTagName("timeout"));
		showRoute(element.getElementsByTagName("route"));
		showCompress(element.getElementsByTagName("compress"));
		showIpcp(element.getElementsByTagName("ipcp"));
	}
	private void showPPP(NodeList nodeList){
		
		if(nodeList == null || nodeList.getLength() == 0) return;
		
		Element element = (Element)nodeList.item(0);
		//NodeList childList = element.getChildNodes();
		//int length = childList.getLength();
		if(element.getElementsByTagName("username").item(0).hasChildNodes()){
			String username = ((Element)element.getElementsByTagName("username").item(0)).getFirstChild().getNodeValue();
			userTextBox.setText(username);
		}
		NodeList authList = element.getElementsByTagName("authentication");
		for(int i = 0; i < authList.getLength(); i++){
			String value = ((Element)authList.item(i)).getFirstChild().getNodeValue();
			if(value.equals("Acceptable")){
				String type = ((Element)authList.item(i)).getAttribute("type");
				if(type.equals("chapmd5")){
					chapmdCheckBox.setChecked(true);
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
	private void showLcp(NodeList nodeList){
		if(nodeList == null || nodeList.getLength() == 0) return;
		
		Element element = (Element)nodeList.item(0);
		String defaultAttr = ((Element)element.getElementsByTagName("keepalive").item(0)).getAttribute("default");
		String value = ((Element)element.getElementsByTagName("keepalive").item(0)).getFirstChild().getNodeValue();
		if(value.equals(defaultAttr)){
			keepaliveCheckBox.setChecked(false);
			keepaliveTextBox.setText("");
			keepaliveTextBox.setEnabled(false);
		}else{
			keepaliveCheckBox.setChecked(true);
			keepaliveTextBox.setText(value);
			keepaliveTextBox.setEnabled(true);
		}
		
		value = ((Element)element.getElementsByTagName("redial").item(0)).getFirstChild().getNodeValue();
		redialTextBox.setText(value);
	}
	private void showLink(NodeList nodeList){
		if(nodeList == null || nodeList.getLength() == 0) return;
		
		Element element = (Element)nodeList.item(0);
		String value = ((Element)element.getElementsByTagName("mtu").item(0)).getFirstChild().getNodeValue();
		mtuTextBox.setText(value);
		value = ((Element)element.getElementsByTagName("mru").item(0)).getFirstChild().getNodeValue();
		mruTextBox.setText(value);
		value = ((Element)element.getElementsByTagName("mrru").item(0)).getFirstChild().getNodeValue();
		mrruTextBox.setText(value);
	}
	private void showNat(NodeList nodeList){
		if(nodeList == null || nodeList.getLength() == 0) return;

		String value = ((Element)nodeList.item(0)).getFirstChild().getNodeValue();
		if(value.equals("disable")){
			natCheckBox.setChecked(false);
		}else{
			natCheckBox.setChecked(true);
		}
	}
	private void showOndemand(NodeList nodeList){
		if(nodeList == null || nodeList.getLength() == 0) return;

		String value = ((Element)nodeList.item(0)).getFirstChild().getNodeValue();
		if(value.equals("disable")){
			ondemandCheckBox.setChecked(false);
		}else{
			ondemandCheckBox.setChecked(true);
		}
	}
	private void showTimeout(NodeList nodeList){
		if(nodeList == null || nodeList.getLength() == 0) return;
		
		Element element = (Element)nodeList.item(0);
		String value = ((Element)element.getElementsByTagName("idle").item(0)).getFirstChild().getNodeValue();	
		idleTextBox.setText(value);
		value = ((Element)element.getElementsByTagName("session").item(0)).getFirstChild().getNodeValue();
		sessionTextBox.setText(value);
	}
	private void showRoute(NodeList nodeList){
		if(nodeList == null || nodeList.getLength() == 0 ) return;
		Element element = (Element)nodeList.item(0);
		if(!element.hasChildNodes()) return;
		NodeList childList = element.getChildNodes();
		int length = childList.getLength();
		routeListGrid.resize(length/2+1, 3);
		routeListGrid.getRowFormatter().setStyleName(0, "head");
		routeListGrid.getColumnFormatter().setWidth(0, "30%");
		routeListGrid.getColumnFormatter().setWidth(1, "30%");
		routeListGrid.getColumnFormatter().setWidth(2, "40%");
		routeListGrid.setText(0, 0, "目的地址");
		routeListGrid.setText(0, 1, "掩码");
		routeListGrid.setText(0, 2, "操作");
		
		for(int i = 0; i < length; i += 2){
			String dest = ((Element)childList.item(i)).getFirstChild().getNodeValue();
			Button delButton = new Button("删除");
			delButton.setWidth("2cm");
			delButton.addClickListener(new DelRouteListener(dest));
			routeListGrid.setText(i/2 + 1, 0, dest);
			routeListGrid.setText(i/2 + 1, 1, ((Element)childList.item(i)).getFirstChild().getNodeValue());
			routeListGrid.setWidget(i/2 + 1, 2, delButton);
		}
	}
	private class AddRouteListener implements ClickListener{

		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			boolean checkPass = true;
			String check = Validity.validIpAddress(destTextBox.getText());
			if( check != null){
				destLabel.setText(check);
				checkPass = false;
			}else{
				destLabel.setText("");
			}
			
			check = Validity.validIpAddress(maskTextBox.getText());
			if( check != null){
				maskLabel.setText(check);
				checkPass = false;
			}else{
				maskLabel.setText("");
			}
			
			if( checkPass){
				String ifcmd = "interface=" + getInfCmd();
				String commands = "commands=route " + destTextBox.getText().trim() + " " + maskTextBox.getText().trim();
				String text = ifcmd + "&" + commands;
				ifController.setDialer(new SettingAction(sender), text);
			}else {
				Window.alert("输入有错误，请修改！");
			}
		}
		
	}
	private class DelRouteListener implements ClickListener{
		String address;
		public DelRouteListener(String address){
			this.address = address;
		}
		public void onClick(Widget sender) {
			// TODO Auto-generated method stub
			if(Window.confirm("是否要删除路由:"+address+"?")){
				String ifcmd = "interface=" + getInfCmd();
				String commands = "commands=no route " + address;
				String text = ifcmd + "&" + commands;
				ifController.setDialer(new SettingAction(sender), text);
			}
		}
		
	}
	private void showCompress(NodeList nodeList){
		if(nodeList == null || nodeList.getLength() == 0) return;
		Element element = (Element)nodeList.item(0);
		if(element.getElementsByTagName("mppc").item(0).hasChildNodes()){
			String value = ((Element)element.getElementsByTagName("mppc").item(0)).getFirstChild().getNodeValue();
			if(value.equals("disable")){
				mppcRadioButton.setChecked(false);
			}else{
				mppcRadioButton.setChecked(true);
			}
		}else{
			//nocompressRadioButton.setChecked(true);
		}
	}
	private void showIpcp(NodeList nodeList){
		
		if(nodeList == null || nodeList.getLength() == 0) return;
		Element element = (Element)nodeList.item(0);
		NodeList childList = element.getElementsByTagName("request_dns");
		String value = ((Element)childList.item(0)).getFirstChild().getNodeValue();
		if(value.equals("disable")){
			dnsCheckBox.setChecked(false);
		}else{
			dnsCheckBox.setChecked(true);
		}
		childList = element.getElementsByTagName("vjcomp");
		value = ((Element)childList.item(0)).getFirstChild().getNodeValue();
		if(value.equals("disable")){
			vjcompCheckBox.setChecked(false);
		}else{
			vjcompCheckBox.setChecked(true);
		}
	}

	public void onChange(Widget sender) {
		// TODO Auto-generated method stub
		if(sender == userTextBox){
			userChanged = true;
		}else if(sender == passwordTextBox){
			userChanged = true;
		}else if(sender == repasswordTextBox){
			checkPassword();
			userChanged = true;
		}else if(sender == keepaliveTextBox){
			checkDigital(keepaliveTextBox, keepaliveLabel, 4, 40);
		}else if(sender == redialTextBox){
			
		}else if(sender == mtuTextBox){
			checkDigital(mtuTextBox, mtuLabel, 296, 1500);
		}else if(sender == mruTextBox){
			checkDigital(mruTextBox, mruLabel, 296, 1500);
		}else if(sender == mrruTextBox){
			checkDigital(mrruTextBox, mrruLabel, 296, 4096);
		}else if(sender == idleTextBox){
			checkDigital(idleTextBox, idleLabel, 0, 60000);
		}else if(sender == sessionTextBox){
			checkDigital(sessionTextBox, sessionLabel, 0, 71582787);
		}else if(sender == destTextBox){
			if(destTextBox.getText().trim().equals("")) return;
			String check = Validity.validIpAddress(destTextBox.getText());
			if( check != null){
				destLabel.setText(check);
			}else{
				destLabel.setText("");
			}
		}else if(sender == maskTextBox){
			if(maskTextBox.getText().trim().equals("")) return;
			String check = Validity.validIpAddress(maskTextBox.getText());
			if( check != null){
				maskLabel.setText(check);
			}else{
				maskLabel.setText("");
			}
		}
	}

	
	private void checkAll(){
		isPass = true;
		
		if(userChanged){
			checkPPP();
		}
		if(keepaliveCheckBox.isChecked()){			
			checkDigital(keepaliveTextBox, keepaliveLabel, 4, 40);
		}
		checkDigital(mtuTextBox, mtuLabel, 296, 1500);
		checkDigital(mruTextBox, mruLabel, 296, 1500);
		checkDigital(mrruTextBox, mrruLabel, 296, 4096);
		
		checkDigital(idleTextBox, idleLabel, 0, 60000);
		checkDigital(sessionTextBox, sessionLabel, 0, 71582787);
		
		
	}
	private void checkDigital(TextBox inTextBox, Label outLabel, int min, int max){
		String result = "";
		String digital = inTextBox.getText();
		
		if(digital == null || digital.equals("")){
			outLabel.setText(result);
			return;
		}	
		if(!digital.matches(digitalPattern)){
			result = "包含了非数字字符";
			isPass = false;
		}else if(Integer.parseInt(digital) > max || Integer.parseInt(digital) < min){
			result = "数值不在允许的范围内";
			isPass = false;
		}
		outLabel.setText(result);
	}
	private void checkPassword(){
		String result = "";
		
		if(!passwordTextBox.getText().equals(repasswordTextBox.getText())){
			result = "两次输入的密码不符,请重新输入!";
			isPass = false;
		}
		repasswordLabel.setText(result);
		
	}
	public void checkPPP(){
		String result = "";
		
		if(userTextBox.getText().equals("") && !passwordTextBox.getText().equals("")){
			result = "有密码，没有用户名！";
			userLabel.setText(result);
			isPass = false;
		}else if(!userTextBox.getText().equals("") && passwordTextBox.getText().equals("")){
			result = "有用户名，没有密码！";
			passwordLabel.setText(result);
			isPass = false;
		}else if(!userTextBox.getText().equals("") && !passwordTextBox.getText().equals("")){
			checkPassword();
			userLabel.setText("");
			passwordLabel.setText("");
		}else{
			userLabel.setText("");
			passwordLabel.setText("");
		}
	}
	
	public String getInfCmd(){
		
		String text = "interface Dialer " + ifModel.getName().substring("Dialer".length());
		
		return text;
	}
	public String getCommands(){
		String ifSetting = null;
		
		if(userChanged){
			if( !userTextBox.getText().trim().equals("")){
				ifSetting = "ppp username " + userTextBox.getText().trim();
			}
			if( !passwordTextBox.getText().trim().equals("")){
				ifSetting += ";ppp password " + passwordTextBox.getText().trim();
			}
		}
		String auth  = null;
		String noauth = null;
		if(chapmdCheckBox.isChecked()){
			auth = "chap";
		}else{
			noauth = "chap";
		}
		if(eapCheckBox.isChecked()){
			auth = auth == null ? "eap" : auth + " eap";
		}else{
			noauth = noauth == null ? "eap" : noauth + " eap";
		}
		if(mschapCheckBox.isChecked()){
			auth = auth == null ? "ms-chap" : auth + " ms-chap";
		}else{
			noauth = noauth == null ? "ms-chap" : noauth + " ms-chap";
		}
		if(papCheckBox.isChecked()){
			auth = auth == null ? "pap" : auth + " pap";
		}else{
			noauth = noauth == null ? "pap" : noauth + " pap";
		}
		if(noauth != null){
			ifSetting += ";no ppp authentication " + noauth;
		}
		if(auth != null){
			ifSetting += ";ppp authentication " + auth;
		}
		if(keepaliveCheckBox.isChecked()){
			if( !keepaliveTextBox.getText().trim().equals("")){
				ifSetting += ";lcp keepalive " + keepaliveTextBox.getText().trim();
			}
		}else{
			ifSetting += ";no lcp keepalive";
		}
		if( !redialTextBox.getText().trim().equals("")){
			ifSetting += ";lcp redial " + redialTextBox.getText().trim();
		}
		if( !mtuTextBox.getText().trim().equals("")){
			ifSetting += ";link type mtu " + mtuTextBox.getText().trim();
		}
		if( !mruTextBox.getText().trim().equals("")){
			ifSetting += ";link type mru " + mruTextBox.getText().trim();
		}
		if( !mrruTextBox.getText().trim().equals("")){
			ifSetting += ";link type mrru " + mrruTextBox.getText().trim();
		}
		if(natCheckBox.isChecked()){
			ifSetting += ";nat outside";
		}else {
			ifSetting += ";no nat";
		}
		if(ondemandCheckBox.isChecked()){
			ifSetting += ";dialer-on-demand";
		}else {
			ifSetting += ";no dialer-on-demand";
		}
		if( !idleTextBox.getText().trim().equals("")){
			ifSetting += ";timeout idle " + idleTextBox.getText().trim();
		}
		if( !sessionTextBox.getText().trim().equals("")){
			ifSetting += ";timeout session " + sessionTextBox.getText().trim();
		}
		
		
		if(mppcRadioButton.isChecked()){
			ifSetting += ";compress mppc stateless";
		}else{
			ifSetting += ";no compress mppc";
		}
		if(dnsCheckBox.isChecked()){
			ifSetting += ";request dns";
		}else{
			ifSetting += ";no request dns";
		}
		if(vjcompCheckBox.isChecked()){
			ifSetting += ";vjcomp";
		}else{
			ifSetting += ";no vjcomp";
		}
		return ifSetting;
	}
	
	class SettingAction extends ViewAction{

		Widget sender;
		
		public SettingAction(Widget sender){
			this.sender = sender;
		}
		public void onSuccess(Object object) {
			// TODO Auto-generated method stub
			if (sender != null) {
				((Button) sender).setEnabled(true);
			}
			if(object instanceof String){
				MessageConsole.setText((String)object);
			}
			parent.loadModel();
		}
		
	}
}
