package com.risetek.rismile.client.model;


public abstract class RismileTable {

	public RismileTable(boolean dir) {
		ASC = dir;
	}
	
	public void moveDir(int dir) {
		switch(dir)
		{
		case 0:
			if( ASC )
				offset = 0;
			else
				offset = sum - limit;
				
			break;
		case 1:
			if( !ASC )
				offset = offset - limit;
			else
				offset = offset + limit;
			break;
		case 2:
			if( ASC )
				offset = offset - limit;
			else
				offset = offset + limit;

			break;
		case 3:
			if( !ASC )
				offset = 0;
			else
				offset = sum - limit;
			break;
		default:
			break;
		}
		if( offset < 0 )
			offset = 0;
	}
	
	private String data[][];
	private int offset;
	private int limit;
	private int sum;

	public boolean ASC = true;
	
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

	public abstract void parseXML(String text);
	
}
