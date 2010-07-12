package com.risetek.keke.client.sticklet;

import java.util.HashMap;
import java.util.Vector;

import com.risetek.keke.client.context.D3Context;
import com.risetek.keke.client.nodes.CallerNode;
import com.risetek.keke.client.nodes.CancelNode;
import com.risetek.keke.client.nodes.ExitNode;
import com.risetek.keke.client.nodes.InputNode;
import com.risetek.keke.client.nodes.KVPairStick;
import com.risetek.keke.client.nodes.LogoutNode;
import com.risetek.keke.client.nodes.NamedNode;
import com.risetek.keke.client.nodes.ParamStick;
import com.risetek.keke.client.nodes.PasswordNode;
import com.risetek.keke.client.nodes.PromotionNode;
import com.risetek.keke.client.nodes.RemoteRequestNode;
import com.risetek.keke.client.nodes.SecurityCheckNode;
import com.risetek.keke.client.nodes.StayStick;
import com.risetek.keke.client.nodes.Stick;

public class Sticklets {

	private final HashMap<String, String[][]> stickletSources = new HashMap<String, String[][]>();
	HashMap<String, Sticklet> stickletInstances = new HashMap<String, Sticklet>();

	final static String[][] login = {
			{ "1", "Named", "epay.local.login" },
			{ "1", "Promotion", "登录ePay", "<p><img v=\"20090218213158800\" /><Descript v=\"安全检测的需要\" /></p>" },
			{ "0", "Caller", "epay.local.system.login", "20090218213211612" }, };

	private final static String[][] epay = {
			{ "1", "Named", "epay.local.epay" },
			{ "3", "SecurityCheck" },
			{ "0", "Stay", "服务订阅", "20090218213212220" },
			{ "0", "Promotion", "个性设置", "20090218213211718" },
			{ "1", "Logout", "退出登录", "20090218213212783" },
			// TODO: 系统调用则意味着历史记录的存在，这里应该启动一个新的sticklet来运行。
			{ "0", "Caller", "epay.local.demo", "20090218213213314" }, };

	private final static String[][] news = {
			{ "3", "Named", "epay.local.news" },
			{ "1", "Promotion", "cnBeta消息", "20090218213219741" },
			{ "1", "Promotion", "人民网消息", "20090218213219741" },
			{ "1", "Promotion", "我要帮助", "20090218213212220" },
			{ "1", "Param", "epay/news", "20090218213214862" },
			{ "1", "Param", "epay/people", "20090218213214862" },
			{ "1", "Param", "epay/help", "20090218213214862" },
			{ "1", "Promotion", "消息动态来源cnbeta", "" },
			{ "1", "Promotion", "新闻动态来源人民网", "" },
			{ "0", "RemoteRequest", "获取帮助信息...", "20090218213227509" },
			{ "1", "Promotion", "消息由后台动态提取", "" },
			{ "1", "Promotion", "新闻由后台动态提取", "" },
			{ "0", "RemoteRequest", "获取新信息中...", "" },
			{ "0", "RemoteRequest", "获取新闻中...", "20090218213227509" },
			};

	private final static String[][] pay = {
			{ "2", "Named", "epay.local.pay" },
			{ "3", "Promotion", "支付", "" },
			{ "1", "Promotion", "不予支付", "" },
			{ "1", "Promotion", "工商行", "" },
			{ "1", "Promotion", "中国银行", "" },
			{ "1", "Promotion", "建设银行", "" },
			{ "0", "Promotion", "该账单会消失", "" },
			{ "0", "Stay", "谁来实现", "" },
			{ "0", "Stay", "谁来实现", "" },
			{ "0", "Stay", "谁来实现", "" },
		};
	
	private final static String[][] bill = {
			{ "1", "Named", "epay.local.bill" },
			{ "3", "SecurityCheck" },
			{ "0", "Promotion", "牙膏一只", "<p><Descript v=\"单价 1.52\r\n总价1.52\r\n\" /></p>" },
			{ "0", "Promotion", "牙刷一把", "<p><Descript v=\"单价 2.52\r\n总价2.52\r\n\" /></p>" },
			{ "0", "Promotion", "毛巾一张", "<p><Descript v=\"单价 3.52\r\n总价3.52\r\n\" /></p>" },
			};
	
	private final static String[][] demo = {
			{ "6", "Named", "epay.local.demo" },
			{ "1", "Promotion", "我的 ePay", "<p><img v=\"20090218213217243\" /><Descript v=\"欢迎使用Risetek服务\" /></p>" },
			{ "1", "Promotion", "账单支付", "" },
			{ "1", "Promotion", "消息服务", "20090218213219741" },
			{ "0", "Stay", "400-000-001 服务电话", "<p><img v=\"20090218213211718\" /><Descript v=\"单价 3.52\r\n总价3.52\r\n\" /></p>" },
			{ "0", "Promotion", "测试点：没有子节点的执行会越界", "20090218213211718" },
			{ "0", "Exit", "退出程序", "20090218213212783" },
			{ "0", "Caller", "epay.local.epay", "" },
			{ "1", "Caller", "epay.local.bill", "" },
			{ "0", "Caller", "epay.local.news", "20090218213213314" },
			{ "0", "Caller", "epay.local.pay", "" },
	 		};
	private final static String[][] gameover = {
			{ "1", "Named", "epay.local.gameover" },
			{ "0", "Cancel", "Game Over 按F5重新运行", "20090218213214862" }, };

	private final static String[][] services_failed = {
			{ "1", "Named", "epay.local.services.failed" },
			{ "0", "Cancel", "远端服务失败", "20090218213217243" },};

	private final static String[][] invalid_src = {
			{ "1", "Named", "epay.local.system.nosrc" },
			{ "1", "Promotion", "未找到相应Stcklet", "20090218213218568" },
			{ "0", "Exit", "byebye..", "20090218213219389" }, };

	private final static String[][] runtime_error = {
			{ "1", "Named", "epay.local.system.runtime_error" },
			{ "1", "Promotion", "Runtimg Error", "20090218213219741" },
			{ "0", "Exit", "byebye..", "20090218213222605" }, };

	final static String[][] syslogin = {
			{ "1", "Named", "epay.local.system.login" },
			{ "1", "Input", "输入用户名称", "20090218213222671" },
			{ "1", "Password", "输入登录密码", "20090218213227180" },
			{ "1", "Param", "epay/login", "20090218213214862" },
			{ "0", "RemoteRequest", "登录ePay中...", "20090218213227509" }, };
	
	/*
	 * 注册名称与源。
	 */
	private void registeStick(String[][] src) {
		if( stickletSources.put(src[0][2], src) != null ) {
			D3Context.Log("Warning: 重复的 sticklet ");
			D3Context.Log(" ->" + src[0][2]);
		}
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
		registeStick(news);
		registeStick(bill);
		registeStick(pay);
	}

	public static Sticklet loadSticklet(String name) {
		Sticklet i;

		i = INSTANCE.stickletInstances.get(name);
		if (i != null) {
			// 我们不能直接给出i, 因为存在运行数据，要给出一个新的Sticklet上下文环境。
			D3Context.Log("cached sticklet:"+name);
			i = new Sticklet(i.rootNode);
			return i;
		}
		
		String[][] src = INSTANCE.stickletSources.get(name);
		if (src == null) {
			D3Context.Log("不存在的Sticklet:"+name);
			src = INSTANCE.stickletSources.get("epay.local.system.nosrc");
			return new Sticklet(loadNodes(src));
		}
		i = new Sticklet(loadNodes(src));
		INSTANCE.stickletInstances.put(name, i);
		return i;
	}

	public static Sticklet loadSticklet(String[][] src) {
		return new Sticklet(loadNodes(src));
	}

	public static Sticklets INSTANCE = new Sticklets();

	private static Stick createNode(String[] nodeDesc) {
		Stick node = null;
		if ("Named".equals(nodeDesc[1]))
			node = new NamedNode(nodeDesc[2]);
		else if ("Promotion".equals(nodeDesc[1]))
			node = new PromotionNode(nodeDesc[2], nodeDesc[3]);
		else if ("Stay".equals(nodeDesc[1]))
			node = new StayStick(nodeDesc[2], nodeDesc[3]);
		else if ("Cancel".equals(nodeDesc[1]))
			node = new CancelNode(nodeDesc[2]);
		else if ("Input".equals(nodeDesc[1]))
			node = new InputNode(nodeDesc[2], nodeDesc[3]);
		else if ("Password".equals(nodeDesc[1]))
			node = new PasswordNode(nodeDesc[2]);
		else if ("RemoteRequest".equals(nodeDesc[1]))
			node = new RemoteRequestNode(nodeDesc[2]);
		else if ("Param".equals(nodeDesc[1]))
			node = new ParamStick(nodeDesc[2]);
		else if ("Logout".equals(nodeDesc[1]))
			node = new LogoutNode();
		else if ("Exit".equals(nodeDesc[1]))
			node = new ExitNode();
		else if ("SecurityCheck".equals(nodeDesc[1]))
			node = new SecurityCheckNode();
		else if ("Caller".equals(nodeDesc[1]))
			node = new CallerNode(nodeDesc[2]);
		else if ("KVPair".equals(nodeDesc[1]))
			node = new KVPairStick();
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
			D3Context.Log("root node degree error");
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
				D3Context.Log("load notes error.");

			if (dn.degree > 0)
				fifoStack.add(dn);
			loop++;
		}

		// 检查Sticklet是否规范
		if (fifoStack.size() > 0)
			D3Context.Log("Sticklet: " + datas[0][2] + " error!");

		return top.node;
	}

}
