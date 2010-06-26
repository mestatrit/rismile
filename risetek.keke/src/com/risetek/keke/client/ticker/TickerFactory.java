package com.risetek.keke.client.ticker;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.nodes.Node;

public class TickerFactory {
	@SuppressWarnings("unchecked")
	public static Ticker Produce(Node node) {
		GWT.log(node.Ticker);
		
	//	Class c = tickerClasses.get(node.Ticker)
		if("PromotionTicker".equals(node.Ticker))
			return new PromotionTicker(node);
		
		return new PromotionTicker(node);
	}
	
	@SuppressWarnings("unchecked")
//	static HashMap<String, Class> tickerClasses = new HashMap<String, Class>();
	public void initTickerClass() {
//		tickerClasses.put("PromotionTicker", PromotionTicker.class);
	}
}
