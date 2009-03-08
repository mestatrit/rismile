package com.risetek.rismile.client.model;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public abstract class RismileTable {

	private String data[][];
	private int offset;
	private int limit;
	private int sum;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setData(String data[][]) {
		this.data = data;
	}

	public void setOffset(int offset) {
		if (offset > 0)
			this.offset = offset;
		else
			this.offset = 0;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public String[][] getData() {
		return data;
	}

	public int getOffset() {
		return offset;
	}

	public int getSum() {
		return sum;
	}

	protected String getElementText(Element item, String value) {
		String result = "";
		NodeList itemList = item.getElementsByTagName(value);
		if (itemList.getLength() > 0 && itemList.item(0).hasChildNodes()) {

			result = itemList.item(0).getFirstChild().getNodeValue();
		}
		return result;
	}

	
	public abstract void parseXML(String text);
	
}
