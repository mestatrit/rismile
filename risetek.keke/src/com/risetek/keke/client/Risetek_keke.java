package com.risetek.keke.client;

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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.datamodel.CardKeke;
import com.risetek.keke.client.datamodel.Kekes;
import com.risetek.keke.client.events.PosInitEvent;
import com.risetek.keke.client.events.PosMoveDownEvent;
import com.risetek.keke.client.events.PosMoveRightEvent;
import com.risetek.keke.client.events.PosMoveUpEvent;
import com.risetek.keke.client.events.PosRenderEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Risetek_keke implements EntryPoint, UiKeke {
	interface localUiBinder extends UiBinder<DockLayoutPanel, Risetek_keke> {}
	private static localUiBinder uiBinder = GWT.create(localUiBinder.class);
	/**
	 * This is the entry point method.
	 */
	
	@UiField public static Grid keke;
	public static @UiField SpanElement logger;
	@UiField HTMLPanel skins;
	@UiField Button up;
	@UiField Button down;
	@UiField Button left;
	@UiField Button right;
	@UiField Button card;
	
	int kekesIndex = 0;
    PosContext context = new PosContext();
	
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
			    context.loadEvent(new PosMoveUpEvent());
			    context.eventStack().nextEvent();
			}
	    	
	    });
	    
	    down.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			    context.loadEvent(new PosMoveDownEvent());
			    context.eventStack().nextEvent();
			}
	    	
	    });

	    right.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			    context.loadEvent(new PosMoveRightEvent());
			    context.eventStack().nextEvent();
			}
	    	
	    });

	    left.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
//				Kekes.current.moveLeft();
			    context.loadEvent(new PosRenderEvent());
			    context.eventStack().nextEvent();
			}
	    	
	    });

	    card.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if( Kekes.current.defaultOption instanceof CardKeke )
				{
					// Kekes.current.moveRight();
					Kekes.current.defaultOption.card();
				    context.loadEvent(new PosRenderEvent());
				    context.eventStack().nextEvent();
				}
			}
	    	
	    });
	    
	    root.add(outer);

	    keke.sinkEvents(Event.ONMOUSEWHEEL | Event.ONMOUSEMOVE | Event.ONMOUSEOVER | Event.ONMOUSEOUT);
	    outer.sinkEvents(Event.ONMOUSEWHEEL | Event.ONMOUSEMOVE | Event.ONMOUSEOVER | Event.ONMOUSEOUT);
	    outer.animate(100);

	    context.loadEvent(new PosInitEvent());
	    context.eventStack().nextEvent();
	}
	
	public interface ViewStyle extends CssResource {
	    String hilight();
	}
	
	@UiField public static ViewStyle style;
	
}
