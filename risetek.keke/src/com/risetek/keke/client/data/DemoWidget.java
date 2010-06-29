package com.risetek.keke.client.data;

public class DemoWidget  extends AWidget {
	String[][] a = {
			{"3", "NamedNode", "Root ePay", ""},
			{"0", "Promotion", "服务创造价值", "p4"},
			{"0", "Promotion", "观念决定出路", "p2"},
			{"0", "Promotion", "退出登录", "p5"},
	};

	public DemoWidget() {
		rootNode = loadNodes(a);
	}
}
