package com.risetek.keke.client.ui;

public interface UiKeke {
	public static final int maxKeke		= 6;
	public static final int hiKeke		= 1;		// begin at 0;
	public static final int kekeHeight	= 69;		// 实际大小。
	public static final int IconWidth	= 70;		// 图标宽度。
	
	public static final int TipsHeight	= 2;		// 提示信息的高度（单元格）
	public static final int KeyPadHeight= 2;		// 输入界面的高度（单元格）
	
	public static final int background_INDEX	= 10;		// 背景的z-index。
	public static final int Sticks_INDEX		= 20;		// Sticks的z-index。
	public static final int Tips_INDEX			= 50;		// 提示信息的z-index。
	public static final int KeyPad_INDEX		= 100;		// 键盘的z-index。
}
