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

    public boolean ASC = true;
    
    public NavBar(RismileTableController control) {
      initWidget(buttonsPanel);

	  // First
	  gotoFirst = new Button("&lt;&lt;", control.new navigatorFirstClick());
      buttonsPanel.add(gotoFirst);
      gotoFirst.addStyleDependentName("dir");
      // Prev
      gotoPrev = new Button("&lt;", control.new navigatorPrevClick());
      buttonsPanel.add(gotoPrev);
      gotoPrev.addStyleDependentName("dir");
      // Next
      gotoNext = new Button("&gt;", control.new navigatorNextClick());
      buttonsPanel.add(gotoNext);
      gotoNext.addStyleDependentName("dir");
      // Last
      gotoLast = new Button("&gt;&gt;", control.new navigatorLastLastClick());
      buttonsPanel.add(gotoLast);
      gotoLast.addStyleDependentName("dir");

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
