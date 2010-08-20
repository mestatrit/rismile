package com.risetek.rismile.client.model;

import com.google.gwt.xml.client.NodeList;
import com.risetek.rismile.client.view.NavBar.DIR;


public abstract class RismileTable {

	public NodeList nodes;
	
	public RismileTable(boolean dir) {
		ASC = dir;
	}
	
	public void moveDir(DIR dir) {
		switch(dir)
		{
		// GoFirst
		case FIRST:
			if( ASC )
				offset = 0;
			else
				offset = sum - limit;
				
			break;
		case PREV:
			if( ASC )
				offset = offset - limit;
			else
				offset = offset + limit;
			break;
		case NEXT:
			if( ASC )
				offset = offset + limit;
			else
				offset = offset - limit;

			break;
		case LAST:
			// Go last.
			if( ASC )
				offset = sum - limit;
			else
				offset = 0;
			break;
		default:
			break;
		}
		if( offset < 0 )
			offset = 0;
	}
	
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

	public void setOffset(int offset) {
		if (offset > 0)
			this.offset = offset;
		else
			this.offset = 0;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getOffset() {
		return offset;
	}

	public int getSum() {
		return sum;
	}

	public abstract void parseXML(String text);
	
}
