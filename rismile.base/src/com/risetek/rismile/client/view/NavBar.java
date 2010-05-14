package com.risetek.rismile.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class NavBar extends Composite {
	
    private final Button gotoFirst;
    private final Button gotoLast;
    private final Button gotoNext;
    private final Button gotoPrev;
    
    public boolean enable = true;
    private final HorizontalPanel buttonsPanel = new HorizontalPanel();

    public boolean ASC = true;
    
    public NavBar() {
      initWidget(buttonsPanel);

	  // First
	  gotoFirst = new Button("&lt;&lt;", new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			fireEvent(new NavEvent(0));
		}
	  }
	  );
      buttonsPanel.add(gotoFirst);
      gotoFirst.addStyleName("dir");
      // Prev
      gotoPrev = new Button("&lt;",  new ClickHandler(){

  		@Override
  		public void onClick(ClickEvent event) {
			fireEvent(new NavEvent(1));
  		}
  	  }
  	  );
      buttonsPanel.add(gotoPrev);
      gotoPrev.addStyleName("dir");
      // Next
      gotoNext = new Button("&gt;",  new ClickHandler(){

  		@Override
  		public void onClick(ClickEvent event) {
			fireEvent(new NavEvent(2));
  		}
  	  }
  	  );
      buttonsPanel.add(gotoNext);
      gotoNext.addStyleName("dir");
      // Last
      gotoLast = new Button("&gt;&gt;",  new ClickHandler(){

  		@Override
  		public void onClick(ClickEvent event) {
			fireEvent(new NavEvent(3));
  		}
  	  }
  	  );
      buttonsPanel.add(gotoLast);
      gotoLast.addStyleName("dir");

      enabelNavbar(false,false,false,false);
    }

    public void enabelNavbar(boolean firstEnabled, boolean preEnabled,
    		boolean nextEnabled, boolean lastEnabled){
    	if( !enable ) {
        	gotoFirst.setEnabled(false);
        	gotoPrev.setEnabled(false);
        	gotoNext.setEnabled(false);
        	gotoLast.setEnabled(false);
    		return;
    	}
    	gotoFirst.setEnabled(firstEnabled);
    	gotoPrev.setEnabled(preEnabled);
    	gotoNext.setEnabled(nextEnabled);
    	gotoLast.setEnabled(lastEnabled);
	}
    
	// 数据导航事件处理
    HandlerManager handler = new HandlerManager(this);
    
	public HandlerRegistration addNavHandler(NavHandler navHandler) {
		return addHandler(navHandler, NavEvent.getType());
	}
	
	public interface NavHandler extends EventHandler {
		void onNav(NavEvent event);
	}
	
	public static class NavEvent extends GwtEvent<NavHandler> {
		private static Type<NavHandler> TYPE;
		public static Type<NavHandler> getType() {
			if (TYPE == null) {
				TYPE = new Type<NavHandler>();
			}
			return TYPE;
		}

		@Override
		public final Type<NavHandler> getAssociatedType() {
			return TYPE;
		}

		int resultHtml;
		public NavEvent(int resultsHtml) {
			this.resultHtml = resultsHtml;
		}

		public int getResult() {
			return resultHtml;
		}
		
		@Override
		protected void dispatch(NavHandler handler) {
			handler.onNav(this);
		}
	}

}
