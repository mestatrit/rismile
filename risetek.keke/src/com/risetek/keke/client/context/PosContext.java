package com.risetek.keke.client.context;

import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.risetek.keke.client.context.ClientEventBus.CallerEvent;
import com.risetek.keke.client.context.ClientEventBus.CallerHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDCARDHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDControlEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDControlHandler;
import com.risetek.keke.client.context.ClientEventBus.HIDNumberEvent;
import com.risetek.keke.client.context.ClientEventBus.HIDNumberHandler;
import com.risetek.keke.client.context.ClientEventBus.ViewChangedEvent;
import com.risetek.keke.client.context.ClientEventBus.ViewChangedHandler;
import com.risetek.keke.client.nodes.Stick;
import com.risetek.keke.client.presenter.Presenter;
import com.risetek.keke.client.sticklet.ASticklet;
import com.risetek.keke.client.sticklet.Sticklets;
import com.risetek.keke.client.ui.D3View;
import com.risetek.keke.client.ui.KekesComposite;

public class PosContext {

	// TODO: 
	// 我们应该用 key value pair 来维系系统级别的信息。
	
	// 运行密钥。
	public static String Token = null;
	
	public static void Log(String message) {
		GWT.log(message);
		D3View.logger.logger.addItem(message);
	}

	public static void LogEnter(Stick node) {
		String name = node.getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("->"+name+ " "+node.Promotion);
	}

	public static void LogRollback(Stick node) {
		String name = node.getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("<-- "+name+ " "+node.Promotion);
	}

	public static void LogFaild(Stick node) {
		String name = node.getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("["+name+ "] failed "+node.Promotion);
	}
	
	
	public static void LogAction(Stick node) {
		/*
		String name = node.getClass().getName();
		name = name.substring(30);
		D3View.logger.logger.addItem("  "+name+ " action");
		*/
		D3View.logger.logger.addItem( "["+node.Promotion + "] action");
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
        ClientEventBus.INSTANCE.addHandler(callerhandler, CallerEvent.TYPE);

        presenter = new Presenter(view);
        executeWidget.push(Sticklets.loadSticklet("epay.local.demo"));
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
    		PosContext.Log("D3View GAMEOVER");
            executeWidget.push(Sticklets.loadSticklet("epay.local.gameover"));
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

	CallerHandler callerhandler = new CallerHandler() {

		@Override
		public void onEvent(CallerEvent event) {
			getSticklet().Call(event.getSticklet());
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
			case ClientEventBus.CONTROL_SYSTEM_ROLLBACK:
				getSticklet().control(ASticklet.STICKLET_ROLLBACK);
				break;
			case ClientEventBus.CONTROL_KEY_RIGHT:
			case ClientEventBus.CONTROL_SYSTEM_ENGAGE:
			case ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL:
				int controlCode = ASticklet.STICKLET_ENGAGE;
				if( controlKey == ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL )
					controlCode = ASticklet.STICKLET_ENGAGE_BY_CANCEL;
				ASticklet sticklet = getSticklet();
				int state = sticklet.control(controlCode);
				if( state == Stick.NODE_EXIT ) {
					// 上一个sticklet执行完毕。
					PosContext.Log("Run out of sticklet:"+sticklet.aStickletName);
					// TODO: 这里应该退出程序。
					Executer();
				}
				else if( state == Stick.NODE_CANCEL) {
					PosContext.Log("engage canceled.");
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(ClientEventBus.CONTROL_SYSTEM_ENGAGE_BY_CANCEL));

				}
				break;
			default:
				PosContext.Log("Control Code Overrun");
				break;
			}
		}
	};
}


