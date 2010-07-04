package com.risetek.keke.client.sticklet;

import java.util.HashMap;
import java.util.Vector;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.nodes.CallerNode;
import com.risetek.keke.client.nodes.CancelNode;
import com.risetek.keke.client.nodes.ExitNode;
import com.risetek.keke.client.nodes.InjectTokenNode;
import com.risetek.keke.client.nodes.InputNode;
import com.risetek.keke.client.nodes.LoginNode;
import com.risetek.keke.client.nodes.LogoutNode;
import com.risetek.keke.client.nodes.NamedNode;
import com.risetek.keke.client.nodes.Node;
import com.risetek.keke.client.nodes.PasswordNode;
import com.risetek.keke.client.nodes.PromotionNode;
import com.risetek.keke.client.nodes.SecurityCheckNode;

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
			{ "4", "NamedNode", "epay.local.gameover" },
			{ "0", "Promotion", "Game is Over", "20090218213214862" },
			{ "0", "Promotion", "D3View", "20090218213215625" },
			{ "0", "Promotion", "连接世界", "20090218213215859" },
			{ "0", "Exit", "Goodbye ePay", "20090218213216656" }, };

	private final static String[][] services_failed = {
			{ "1", "NamedNode", "epay.local.services.failed" },
			{ "1", "Promotion", "远端服务失败", "20090218213217243" },
			{ "0", "Exit", "byebye..", "20090218213218178" }, };

	private final static String[][] invalid_src = {
			{ "1", "NamedNode", "epay.local.system.nosrc" },
			{ "1", "Promotion", "未找到相应Stcklet", "20090218213218568" },
			{ "0", "Exit", "byebye..", "20090218213219389" }, };

	private final static String[][] runtime_error = {
			{ "1", "NamedNode", "epay.local.system.runtime_error" },
			{ "1", "Promotion", "Runtimg Error", "20090218213219741" },
			{ "0", "Exit", "byebye..", "20090218213222605" }, };

	final static String[][] syslogin = {
			{ "1", "NamedNode", "epay.local.system.login" },
			{ "1", "Username", "输入用户名称", "20090218213222671" },
			{ "1", "Password", "输入登录密码", "20090218213227180" },
			{ "0", "Login", "登录ePay", "20090218213227509" }, };

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

	private static Node createNode(String[] nodeDesc) {
		Node node = null;
		if ("NamedNode".equals(nodeDesc[1]))
			node = new NamedNode(nodeDesc[2]);
		else if ("Promotion".equals(nodeDesc[1]))
			node = new PromotionNode(nodeDesc[2], nodeDesc[3]);
		else if ("Cancel".equals(nodeDesc[1]))
			node = new CancelNode(nodeDesc[2], nodeDesc[3]);
		else if ("Username".equals(nodeDesc[1]))
			node = new InputNode(nodeDesc[2], nodeDesc[3]);
		else if ("Password".equals(nodeDesc[1]))
			node = new PasswordNode(nodeDesc[2], nodeDesc[3]);
		else if ("Login".equals(nodeDesc[1]))
			node = new LoginNode(nodeDesc[2]);
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

	public static Node loadNodes(String[][] datas) {

		// 该函数中应该实现对 src 的基本检查，如果错误，应该抛出 ePay Runtime Error.

		class NodeDegree {
			int degree;
			Node node;

			public NodeDegree(int degree, Node node) {
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

}
