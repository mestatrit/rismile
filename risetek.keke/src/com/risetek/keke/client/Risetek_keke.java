package com.risetek.keke.client;

import java.util.Vector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.datamodel.CardKeke;
import com.risetek.keke.client.datamodel.GameOverKeke;
import com.risetek.keke.client.datamodel.Kekes;
import com.risetek.keke.client.datamodel.PoemKeke;
import com.risetek.keke.client.datamodel.XinYunKeke;
import com.risetek.keke.client.events.PosInitDialogEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_keke implements EntryPoint, UiKeke {
	interface localUiBinder extends UiBinder<DockLayoutPanel, Risetek_keke> {}
	private static localUiBinder uiBinder = GWT.create(localUiBinder.class);
	/**
	 * This is the entry point method.
	 */
	
	@UiField static Grid keke;
	public static @UiField SpanElement logger;
	@UiField HTMLPanel skins;
	@UiField Button up;
	@UiField Button down;
	@UiField Button left;
	@UiField Button right;
	@UiField Button card;
	
	int kekesIndex = 0;
	
	public void onModuleLoad() {
	    Window.enableScrolling(false);

	    DockLayoutPanel outer = uiBinder.createAndBindUi(this);
	    RootLayoutPanel root = RootLayoutPanel.get();
	    skins.setPixelSize(kekeWidth, maxKeke*kekeHeight);
	    //keke.setPixelSize(kekeWidth, maxKeke*kekeHeight);
	    
	    keke.resize(maxKeke, 1);
	    //keke.setBorderWidth(1);
	    keke.setCellPadding(0);
	    keke.setCellSpacing(0);
	    
	    up.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Kekes.current.upOption();
			    renderKekes(Kekes.current);
			}
	    	
	    });
	    
	    down.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Kekes.current.downOption();
			    renderKekes(Kekes.current);
			}
	    	
	    });

	    right.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Kekes.current.moveRight();
			    renderKekes(Kekes.current);
			}
	    	
	    });

	    left.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Kekes.current.moveLeft();
			    renderKekes(Kekes.current);
			}
	    	
	    });

	    card.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if( Kekes.current.defaultOption instanceof CardKeke )
				{
					// Kekes.current.moveRight();
					Kekes.current.defaultOption.card();
				    renderKekes(Kekes.current);
				}
			}
	    	
	    });
	    
	    root.add(outer);

	    keke.sinkEvents(Event.ONMOUSEWHEEL | Event.ONMOUSEMOVE | Event.ONMOUSEOVER | Event.ONMOUSEOUT);
	    outer.sinkEvents(Event.ONMOUSEWHEEL | Event.ONMOUSEMOVE | Event.ONMOUSEOVER | Event.ONMOUSEOUT);
	    outer.animate(100);

	    loadKekes();
	    // renderKekes(Kekes.grand);
	    
	    
	    PosContext context = new PosContext();
	    context.eventStack().pushEvent(new PosInitDialogEvent());
	    context.eventStack().nextEvent();
	}
	
	public interface ViewStyle extends CssResource {
	    String hilight();
	}
	
	@UiField public static ViewStyle style;
	
	
	public static void renderKekes(Kekes k)
	{
		Vector<Kekes> kk = k.options;
		
		int begin = k.getDefault();
		int loop = begin - currentKeke;

		for( int spacekeke = 0; spacekeke < maxKeke ; spacekeke++, loop++)
		{
			if( loop < 0 || loop >= kk.size() )
				keke.setWidget(spacekeke, 0, new HTML(" "));
			else
				keke.setWidget(spacekeke, 0, new keke(kk.elementAt(loop)));
		}
		keke.getRowFormatter().setStyleName(currentKeke, style.hilight());
	}

	void loadKekes()
	{
	    Kekes begin = new Kekes(Kekes.grand, "从这里开始", "p2");
	    
	    (begin = new Kekes(begin, "您不会厌倦的...", "p3")).setDefaultOption();
	    (begin = new PoemKeke(begin, "我们来展现一种能力", "p2")).setDefaultOption();
	    (begin = new GameOverKeke(begin, "看见我的写给您的话了吗？现在结束了！", "p3")).setDefaultOption();

	    begin = new Kekes(Kekes.grand, "也可以从这里开始", "p5");
	    begin.setDefaultOption();
	    (begin = new Kekes(begin, "我们来试一试充值？", "p3")).setDefaultOption();
	    (begin = new CardKeke(begin, "刷卡吧！", "p4")).setDefaultOption();
	    (begin = new GameOverKeke(begin, "您被耍掉了100元哦！", "p2")).setDefaultOption();
	
	    begin = new Kekes(Kekes.grand, "爱看电影吗？", "p3");
	    Kekes yinpian = new Kekes(begin, "影片？", "p2");
	    yinpian.setDefaultOption();
	    new Kekes(yinpian, "拯救大兵可可", "p2");
	    new Kekes(yinpian, "看不见的战线", "p3");
	    (new Kekes(yinpian, "英雄白跑腿", "p4")).setDefaultOption();
	    new Kekes(yinpian, "未来时代", "p5");
	    
	    begin = new Kekes(begin, "院线？", "p3");
	    new Kekes(begin, "红星路坝坝电影院", "p5");
	    (new Kekes(begin, "火星叔叔的小屋", "p3")).setDefaultOption();
	    new Kekes(begin, "高级大广场", "p4");
	    new Kekes(begin, "路边店", "p2");

	
	    begin = new Kekes(Kekes.grand, "试一试手气", "p4");
	    begin = new Kekes(begin, "随机生产幸运号码", "p5");
	    begin = new XinYunKeke(begin, "随机生产幸运号码", "p5");
	    new Kekes(begin, "可可卡支付", "p2");
	    (new Kekes(begin, "可可币支付", "p2")).setDefaultOption();
	    new Kekes(begin, "可可银行支付", "p2");
	
	}

}
