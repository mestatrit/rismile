package com.risetek.keke.client.ui;

import java.util.Vector;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.risetek.keke.client.keke;
import com.risetek.keke.client.context.UiKeke;

public class KekesComposite extends Composite implements UiKeke {

	Grid keke = new Grid(maxKeke,1);
	public KekesComposite() {
		initWidget(keke);
		setPixelSize(kekeWidth, maxKeke*kekeHeight);
	    keke.setCellPadding(0);
	    keke.setCellSpacing(0);
	}

	public void clearStickers() {
		for( int spacekeke = 0; spacekeke < maxKeke; spacekeke++ )
		{
			HTMLPanel p = new HTMLPanel("");
			p.setPixelSize(kekeWidth, kekeHeight);
			keke.setWidget(spacekeke, 0, p);
		}
	}
	
	public void renderKekes(Vector<keke> kekes, int currentKeke)
	{
		int mid = hiKeke;
		
		// 应该计算多少个可可，然后安排合适的位置进行显示。
		if( kekes.size() == 0 )
			return;

		// 首先清除显示内容。
		clearStickers();
		
		// 显示活动Keke前的Kekes
		int index = currentKeke-1;
		for( int spacekeke = mid-1; spacekeke >= 0 && index >= 0; spacekeke--, index-- )
		{
			keke.setWidget(spacekeke, 0, (kekes.elementAt(index)));
		}

		// 显示活动Keke后的Kekes
		index = currentKeke+1;
		for( int spacekeke = mid+1; spacekeke < maxKeke && index < kekes.size(); spacekeke++, index++ )
		{
			keke.setWidget(spacekeke, 0, (kekes.elementAt(index)));
		}
		
		// 绘制当前有效的Keke。
		keke.setWidget(mid, 0, (kekes.elementAt(currentKeke)));
		keke.getRowFormatter().setStyleName(mid, "hilight");
	}

}
