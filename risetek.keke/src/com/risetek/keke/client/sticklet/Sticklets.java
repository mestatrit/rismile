package com.risetek.keke.client.sticklet;

import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.CallerNode;
import com.risetek.keke.client.nodes.CancelNode;
import com.risetek.keke.client.nodes.ExitNode;
import com.risetek.keke.client.nodes.InjectTokenNode;
import com.risetek.keke.client.nodes.InputNode;
import com.risetek.keke.client.nodes.LogoutNode;
import com.risetek.keke.client.nodes.NamedNode;
import com.risetek.keke.client.nodes.PasswordNode;
import com.risetek.keke.client.nodes.PromotionNode;
import com.risetek.keke.client.nodes.RemoteRequestNode;
import com.risetek.keke.client.nodes.SecurityCheckNode;
import com.risetek.keke.client.nodes.Stick;

public class Sticklets {

	HashMap<String, String[][]> stickletSources = new HashMap<String, String[][]>();
	HashMap<String, Sticklet> stickletInstances = new HashMap<String, Sticklet>();

	final static String[][] login = {
			{ "2", "NamedNode", "epay.local.login" },
			{ "1", "Promotion", "登录ePay", "20090218213158800" },
			{ "0", "Cancel", "取消登录", "20090218213158872" },
			{ "0", "Caller", "epay.local.system.login", "20090218213211612" }, };

	private final static String[][] epay = {
			{ "1", "NamedNode", "epay.local.epay" },
			{ "3", "SecurityCheck" },
			{ "0", "Promotion", "服务创造价值", "20090218213211718" },
			{ "0", "Promotion", "观念决定出路", "20090218213212220" },
			{ "1", "Logout", "退出登录", "20090218213212783" },
			{ "0", "Caller", "epay.local.demo", "20090218213213314" }, };

	private final static String[][] demo = {
			{ "5", "NamedNode", "epay.local.demo" },
			{ "1", "Promotion", "我的 ePay", "20090218213217243" },
			{ "0", "Promotion", "新服务消息", "20090218213219741" },
			{ "0", "Promotion", "我要帮助", "20090218213212220" },
			{ "0", "Promotion", "400-000-001 服务电话", "20090218213211718" },
			{ "0", "Exit", "退出程序", "20090218213212783" },
			{ "0", "Caller", "epay.local.epay", "20090218213213314" },
	 		};
	private final static String[][] gameover = {
			{ "1", "NamedNode", "epay.local.gameover" },
			{ "0", "Cancel", "Game Over F5 to reLoad", "20090218213214862" }, };

	private final static String[][] services_failed = {
			{ "1", "NamedNode", "epay.local.services.failed" },
			{ "0", "Cancel", "远端服务失败", "20090218213217243" },};

	private final static String[][] invalid_src = {
			{ "1", "NamedNode", "epay.local.system.nosrc" },
			{ "1", "Promotion", "未找到相应Stcklet", "20090218213218568" },
			{ "0", "Exit", "byebye..", "20090218213219389" }, };

	private final static String[][] runtime_error = {
			{ "1", "NamedNode", "epay.local.system.runtime_error" },
			{ "1", "Promotion", "Runtimg Error", "20090218213219741" },
			{ "0", "Exit", "byebye..", "20090218213222605" }, };
/*
	final static String[][] syslogin = {
			{ "1", "NamedNode", "epay.local.system.login" },
			{ "1", "Input", "输入用户名称", "20090218213222671" },
			{ "1", "Password", "输入登录密码", "20090218213227180" },
			{ "0", "Login", "登录ePay", "20090218213227509" }, };
*/
	final static String[][] syslogin = {
		{ "1", "NamedNode", "epay.local.system.login" },
		{ "1", "Input", "输入用户名称", "20090218213222671" },
		{ "1", "Password", "输入登录密码", "20090218213227180" },
		{ "0", "RemoteRequest", "登录ePay中...", "20090218213227509" }, };
	
	/*
	 * 注册名称与源。
	 */
	private void registeStick(String[][] src) {
		stickletSources.put(src[0][2], src);
	}

	private Sticklets() {
		// 系统对象，应该早期实例化
		registeStick(runtime_error);
		registeStick(invalid_src);

		// lazy 调用。
		registeStick(login);
		registeStick(demo);
		registeStick(epay);
		registeStick(gameover);
		registeStick(services_failed);
		registeStick(syslogin);
	}

	public static Sticklet loadSticklet(String name) {
		Sticklet i;
		/*
		i = INSTANCE.stickletInstances.get(name);
		if (i != null)
			return i;
		*/
		
		String[][] src = INSTANCE.stickletSources.get(name);
		if (src == null) {
			src = INSTANCE.stickletSources.get("epay.local.system.nosrc");
			return new Sticklet(loadNodes(src));
		}
		i = new Sticklet(loadNodes(src));
		//INSTANCE.stickletInstances.put(name, i);
		return i;
	}

	public static Sticklet loadSticklet(String[][] src) {
		return new Sticklet(loadNodes(src));
	}

	public static Sticklets INSTANCE = new Sticklets();

	private static Stick createNode(String[] nodeDesc) {
		Stick node = null;
		if ("NamedNode".equals(nodeDesc[1]))
			node = new NamedNode(nodeDesc[2]);
		else if ("Promotion".equals(nodeDesc[1]))
			node = new PromotionNode(nodeDesc[2], nodeDesc[3]);
		else if ("Cancel".equals(nodeDesc[1]))
			node = new CancelNode(nodeDesc[2], nodeDesc[3]);
		else if ("Input".equals(nodeDesc[1]))
			node = new InputNode(nodeDesc[2], nodeDesc[3]);
		else if ("Password".equals(nodeDesc[1]))
			node = new PasswordNode(nodeDesc[2], nodeDesc[3]);
		else if ("RemoteRequest".equals(nodeDesc[1]))
			node = new RemoteRequestNode(nodeDesc[2]);
		else if ("Logout".equals(nodeDesc[1]))
			node = new LogoutNode();
		else if ("Exit".equals(nodeDesc[1]))
			node = new ExitNode();
		else if ("SecurityCheck".equals(nodeDesc[1]))
			node = new SecurityCheckNode();
		else if ("Caller".equals(nodeDesc[1]))
			node = new CallerNode(nodeDesc[2]);
		else if ("InjectToken".equals(nodeDesc[1]))
			node = new InjectTokenNode("name",nodeDesc[2]);
		else
			node = new PromotionNode(nodeDesc[2], nodeDesc[3]);
		return node;
	}

	public static Stick loadNodes(String[][] datas) {

		// 该函数中应该实现对 src 的基本检查，如果错误，应该抛出 ePay Runtime Error.

		class NodeDegree {
			int degree;
			Stick node;

			public NodeDegree(int degree, Stick node) {
				this.degree = degree;
				this.node = node;
			}
		}

		// FIFO 结构的节点计数记录栈。
		Vector<NodeDegree> fifoStack = new Vector<NodeDegree>();
		NodeDegree dn = new NodeDegree(Integer.parseInt(datas[0][0]),
				createNode(datas[0]));
		if (dn.degree <= 0) {
			// 命名节点的度必须大于 0 。
			PosContext.Log("root node degree error");
			return null;
		}
		fifoStack.add(dn);
		NodeDegree top = dn;

		int loop = 1;
		while (loop < datas.length) {
			dn = new NodeDegree(Integer.parseInt(datas[loop][0]),
					createNode(datas[loop]));
			if (fifoStack.size() > 0) {
				NodeDegree topdn = fifoStack.elementAt(0);
				topdn.node.addChildrenNode(dn.node);
				topdn.degree--;
				if (topdn.degree <= 0)
					fifoStack.remove(0);
			} else
				PosContext.Log("load notes error.");

			if (dn.degree > 0)
				fifoStack.add(dn);
			loop++;
		}

		// 检查Sticklet是否规范
		if (fifoStack.size() > 0)
			PosContext.Log("Sticklet: " + datas[0][2] + " error!");

		return top.node;
	}

	public static String stickletToXML(String[][] sticklet) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ePay>";
		for( int loop=0; loop < sticklet.length; loop++ ) {
			String stick = "<stick type=\"";
			stick = stick.concat(sticklet[loop][1]);
			stick = stick.concat("\" d=\"");
			stick = stick.concat(sticklet[loop][0]);
			stick = stick.concat("\" p=\"");
			stick = stick.concat(sticklet[loop][2]);
			stick = stick.concat("\">");

			stick = stick.concat("<img>");
			stick = stick.concat(sticklet[loop][3]);
			stick = stick.concat("</img>");
		
			stick = stick.concat("</stick>");
			
			xml = xml.concat(stick);
		}
		
		xml = xml.concat("</ePay>");
		return xml;
	}
	
	
	private static String getStickAttribute(Node node, String attribute) {
		Node n = node.getAttributes().getNamedItem(attribute);
		if( n != null )
			return n.getNodeValue();
		return null;
	}
	
	public static String[][] xmlToSticklet(String xml) {
		Vector<String[]> sticklet = new Vector<String[]>();
		Document doc = XMLParser.parse(xml);
		NodeList list = doc.getElementsByTagName("stick");
		
		for(int loop=0; loop < list.getLength(); loop++) {
			String[] nodes = new String[4];
			Node node = list.item(loop);
			nodes[0] = getStickAttribute(node,"d");
			nodes[1] = getStickAttribute(node,"type");
			nodes[2] = getStickAttribute(node,"p");
			nodes[3] = node.getChildNodes().toString();
			sticklet.add(nodes);
		}
		
		String[][] a = new String[sticklet.size()][4];
		
		for( int loop = 0; loop < sticklet.size(); loop++) {
			String[] t = sticklet.elementAt(loop);
			a[loop][0] = t[0];
			a[loop][1] = t[1];
			a[loop][2] = t[2];
			a[loop][3] = t[3];
		}
		return a;
	}
}
