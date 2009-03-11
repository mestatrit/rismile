package com.risetek.rismile.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.risetek.rismile.client.control.RismileTableController;

public class NavBar extends Composite {
	
    private Button gotoFirst;
    private Button gotoLast;
    private Button gotoNext;
    private Button gotoPrev;
    
    public boolean enable = true;
    private final HorizontalPanel buttonsPanel = new HorizontalPanel();

    
    public NavBar(RismileTableController control) {
      initWidget(buttonsPanel);

	  // First
	  gotoFirst = new Button("&lt;&lt;", control.new navigatorFirstClick());
      buttonsPanel.add(gotoFirst);
      gotoFirst.setStyleName("dirbutton");
      // Prev
      gotoPrev = new Button("&lt;", control.new navigatorPrevClick());
      buttonsPanel.add(gotoPrev);
      gotoPrev.setStyleName("dirbutton");
      // Next
      gotoNext = new Button("&gt;", control.new navigatorNextClick());
      buttonsPanel.add(gotoNext);
      gotoNext.setStyleName("dirbutton");
      // Last
      gotoLast = new Button("&gt;&gt;", control.new navigatorLastLastClick());
      buttonsPanel.add(gotoLast);
      gotoLast.setStyleName("dirbutton");

      enabelNavbar(false,false,false,false);
    }

    public void enabelNavbar(boolean firstEnabled, boolean preEnabled,
    		boolean nextEnabled, boolean lastEnabled){
    	if( !enable ) return;
    	gotoFirst.setEnabled(firstEnabled);
    	gotoPrev.setEnabled(preEnabled);
    	gotoNext.setEnabled(nextEnabled);
    	gotoLast.setEnabled(lastEnabled);
    }
}
