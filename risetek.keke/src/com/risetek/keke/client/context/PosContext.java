package com.risetek.keke.client.context;

import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDControlEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDControlHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDNumberEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDNumberHandler;
import com.risetek.keke.client.context.ClientEventBus.ViewChangedEvent;
import com.risetek.keke.client.context.ClientEventBus.ViewChangedHandler;
import com.risetek.keke.client.presenter.Presenter;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.DemoSticklet;
import com.risetek.keke.client.ui.KekesComposite;


public class PosContext {

	// 运行密钥。
	public static String Token = null;
	
	public static void Log(String message) {
		GWT.log(message);
	}

    /*
     * 表现层
     */
    Presenter	presenter;
    /*
     * 运行的Sticklet
     */
    private ASticklet runningSticklet;
    
    ASticklet getSticklet() {
    	return runningSticklet.getActiveSticklet();
    }
    
    Stack<ASticklet> executeWidget = new Stack<ASticklet>();
    
    public PosContext(KekesComposite view) {
        ClientEventBus.INSTANCE.addHandler(cardhandler, HIDCARDEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(viewchangedhandler, ViewChangedEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(keyCodehandler, HIDNumberEvent.TYPE);
        ClientEventBus.INSTANCE.addHandler(controlCodehandler, HIDControlEvent.TYPE);

        presenter = new Presenter(view);
        executeWidget.push(new DemoSticklet());
        Executer();
    }

    void Executer() {
    	if( executeWidget.size() > 0 )
    	{
    		runningSticklet = executeWidget.pop();
	        runningSticklet.Execute();
    	}
    	else
    	{
    		GWT.log("D3View GAMEOVER");
            executeWidget.push(new DemoSticklet());
            Executer();
    	}
    }
    
    private void updateView() {
        presenter.upDate(getSticklet());
    }

    /**
     *Returns the current logged on user.
     */
    public String toString() {
           return "logon";
    }

	HIDCARDHandler cardhandler = new HIDCARDHandler() {
		@Override
		public void onEvent(HIDCARDEvent event) {
		}
	};
	
	
	ViewChangedHandler viewchangedhandler = new ViewChangedHandler() {

		@Override
		public void onEvent(ViewChangedEvent event) {
	        updateView();
		}
	};

	HIDNumberHandler keyCodehandler = new HIDNumberHandler() {

		@Override
		public void onEvent(HIDNumberEvent event) {
			getSticklet().press(event.getKeyCode());
		}
	};

	HIDControlHandler controlCodehandler = new HIDControlHandler() {

		@Override
		public void onEvent(HIDControlEvent event) {
			int controlKey = event.getControlCode();
			switch( controlKey ) {
			case ClientEventBus.CONTROL_KEY_DOWN:
				getSticklet().control(ASticklet.STICKLET_DOWN);
				break;
			case ClientEventBus.CONTROL_KEY_UP:
				getSticklet().control(ASticklet.STICKLET_UP);
				break;
			case ClientEventBus.CONTROL_KEY_LEFT:
				getSticklet().control(ASticklet.STICKLET_ROLLBACK);
				break;
			case ClientEventBus.CONTROL_KEY_RIGHT:
				ASticklet sticklet = getSticklet(); 
				if( sticklet.control(ASticklet.STICKLET_ENGAGE) == ASticklet.STICKLET_EXIT ) {
					// 上一个widget执行完毕。
					Executer();
				}
				break;
				
			default:
				GWT.log("Control Code Overrun");
				break;
			}
		}
	};
}


