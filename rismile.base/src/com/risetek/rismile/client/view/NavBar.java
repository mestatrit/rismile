package com.risetek.rismile.client.view;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NavBar extends Composite implements ClickListener {
	
    private final Button gotoFirst = new Button("&lt;&lt;", this);
	private final Button gotoLast = new Button("&gt;&gt;", this);
    private final Button gotoNext = new Button("&gt;", this);
    private final Button gotoPrev = new Button("&lt;", this);
    private final Label statisticLabel = new Label("");
    private final HorizontalPanel buttonsPanel = new HorizontalPanel();

    private NavBarListenerCollection listeners = new NavBarListenerCollection();
    
    public NavBar() {
      initWidget(buttonsPanel);
      
      buttonsPanel.add(statisticLabel);
      statisticLabel.setStyleName("rismile-Nav-Label");
	  statisticLabel.setWidth("6em");
	  
      buttonsPanel.add(gotoFirst);
      gotoFirst.setStyleName("rismile-Nav-Button");
      gotoFirst.setWidth("4em");
      buttonsPanel.add(gotoPrev);
      gotoPrev.setStyleName("rismile-Nav-Button");
      gotoPrev.setWidth("4em");
      buttonsPanel.add(gotoNext);
      gotoNext.setStyleName("rismile-Nav-Button");
      gotoNext.setWidth("4em");
      buttonsPanel.add(gotoLast);
      gotoLast.setStyleName("rismile-Nav-Button");
      gotoLast.setWidth("4em");


      // Initialize prev & first button to disabled.
      //
      gotoFirst.setEnabled(false);
	  gotoPrev.setEnabled(false);
	  gotoNext.setEnabled(false);
	  gotoLast.setEnabled(false);
    }

    private class NavBarListenerCollection extends ArrayList<NavBarListener> {
    	
		private static final long serialVersionUID = 1L;

		public void fireClick(Widget sender) {
    	    for (Iterator<NavBarListener> it = iterator(); it.hasNext();) {
    	    	NavBarListener listener = it.next();
    	        if (sender == gotoNext) {
    	            listener.gotoNext();
    	        } else if (sender == gotoPrev) {
    	            listener.gotoPrev();
    	        } else if (sender == gotoFirst) {
    	            listener.gotoFirst();
    	        } else if (sender == gotoLast){
    	        	listener.gotoLast();
    	        }
    	    }
    	}
    }
    public void onClick(Widget sender) {
    	listeners.fireClick(sender);
    }
    public void addNavBarListener(NavBarListener listener){
    	listeners.add(listener);
    }
    public void removeNavBarListener(NavBarListener listener){
    	listeners.remove(listener);
    }

    public void setStatisticText(String text){
    	statisticLabel.setText(text);
    }
    public void enabelNavbar(boolean firstEnabled, boolean preEnabled,
    		boolean nextEnabled, boolean lastEnabled){
    	
    	gotoFirst.setEnabled(firstEnabled);
    	gotoPrev.setEnabled(preEnabled);
    	gotoNext.setEnabled(nextEnabled);
    	gotoLast.setEnabled(lastEnabled);
    }
}
