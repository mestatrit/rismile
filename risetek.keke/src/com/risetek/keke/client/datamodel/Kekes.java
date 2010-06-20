package com.risetek.keke.client.datamodel;

import java.util.Vector;

import com.risetek.keke.client.keke;
import com.risetek.keke.client.context.PosContext;

public class Kekes {
	public Vector<keke> kekes = new Vector<keke>();
	public String imgName = null;
	public static Kekes grand = new Kekes(null, "grandpa", null);
	public static Kekes current = grand;
	
	public String name;
	public Vector<Kekes> options = new Vector<Kekes>();
	public Kekes parent;

	public Kekes defaultOption = null;
	
	public void setDefaultOption()
	{
		this.parent.defaultOption = this;
	}

	public Kekes(Kekes parent, String name, String img)
	{
		this.parent = parent;
		this.name = name;
		this.imgName = img;
		if( null != this.parent )
		{
			this.parent.options.add(this);
		}
	}
	
	public void ability()
	{
		PosContext.Log("玩玩");
		if( current.defaultOption.options.size() > 0 )
			current = current.defaultOption;
	}
	
	public void card()
	{
		
	}
}
