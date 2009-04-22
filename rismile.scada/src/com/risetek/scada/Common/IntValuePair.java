package com.risetek.scada.Common;

public class IntValuePair {
	public IntValuePair(int dataTypes, String dataTypeDescript) {

		this.dataTypes = dataTypes;
		this.dataTypeDescript = dataTypeDescript;
	}
	public int getDataTypes() {
		return dataTypes;
	}
	public void setDataTypes(int dataTypes) {
		this.dataTypes = dataTypes;
	}
	public String getDataTypeDescript() {
		return dataTypeDescript;
	}
	public void setDataTypeDescript(String dataTypeDescript) {
		this.dataTypeDescript = dataTypeDescript;
	}
	private int dataTypes;
	private String dataTypeDescript;
	
}
