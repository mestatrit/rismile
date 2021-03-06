package com.risetek.keke.client.context;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.risetek.keke.client.sticklet.Sticklet;

public class ClientEventBus {

	public static void fireControlKey(int keyCode) {
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(keyCode));
	}
	
	
	public static ClientEventBus INSTANCE = new ClientEventBus();
	
	private class kekeNativePreviewHandler implements NativePreviewHandler {

		@Override
		public void onPreviewNativeEvent(NativePreviewEvent event) {
			
			if (event.getTypeInt() == Event.ONKEYUP )
				return;

			if (event.getTypeInt() == Event.ONKEYDOWN )
			{
				int keyCode = event.getNativeEvent().getKeyCode();
				switch(keyCode) {
				case KeyCodes.KEY_DOWN:
					fireControlKey(CONTROL_KEY_DOWN);
					break;
				case KeyCodes.KEY_UP:
					fireControlKey(CONTROL_KEY_UP);
					break;
				case KeyCodes.KEY_LEFT:
					fireControlKey(CONTROL_KEY_LEFT);
					break;
				case KeyCodes.KEY_RIGHT:
					fireControlKey(CONTROL_KEY_RIGHT);
					break;
				case 96:	// "0"
				case 97:	// "1"
				case 98:	// "2"
				case 99:	// "3"
				case 100:	// "4"
				case 101:	// "5"
				case 102:	// "6"
				case 103:	// "7"
				case 104:	// "8"
				case 105:	// "9"
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDNumberEvent(keyCode - 96));
					break;

				case 48:	// "0"
				case 49:	// "1"
				case 50:	// "2"
				case 51:	// "3"
				case 52:	// "4"
				case 53:	// "5"
				case 54:	// "6"
				case 55:	// "7"
				case 56:	// "8"
				case 57:	// "9"
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDNumberEvent(keyCode - 48));
					break;
					
				case 18:	// "ALT"
				case 144:	// "Num Lock"
					break;
				default:
					// PosContext.Log("Press: " + keyCode);
					break;
				}
			}
		}
	}
	
	NativePreviewHandler nphandler = new kekeNativePreviewHandler();
	
	private ClientEventBus(){
		Event.addNativePreviewHandler(nphandler);
	}
	
	HandlerManager handleManager = new HandlerManager(this);
	
	public void fireEvent(GwtEvent<?> event) {
		handleManager.fireEvent(event);
	}

	public final <H extends EventHandler> HandlerRegistration addHandler(
			final H handler, GwtEvent.Type<H> type) {
		return handleManager.addHandler(type, handler);
	}

	public final <H extends EventHandler> void removeHandler(
			final H handler, GwtEvent.Type<H> type) {
		handleManager.removeHandler(type, handler);
	}
	
	// 处理  刷卡 事件
	public interface HIDCARDHandler extends EventHandler {
		void onEvent(HIDCARDEvent event);
	}
	
	public static class HIDCARDEvent extends GwtEvent<HIDCARDHandler> {
		public static Type<HIDCARDHandler> TYPE = new Type<HIDCARDHandler>();

		private String magic;
		public HIDCARDEvent(String magic) {
			this.magic = magic;
		}
		
		public String getMagic() {
			return magic;
		}
		
		@Override
		public final Type<HIDCARDHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(HIDCARDHandler handler) {
			handler.onEvent(this);
		}
	}
	
	// 处理 界面刷新事件
	public interface ViewChangedHandler extends EventHandler {
		void onEvent(ViewChangedEvent event);
	}
	
	public static class ViewChangedEvent extends GwtEvent<ViewChangedHandler> {
		public static Type<ViewChangedHandler> TYPE = new Type<ViewChangedHandler>();

		@Override
		public final Type<ViewChangedHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(ViewChangedHandler handler) {
			handler.onEvent(this);
		}
	}
	
	
	// 处理 数字输入 事件
	public interface HIDNumberHandler extends EventHandler {
		void onEvent(HIDNumberEvent event);
	}
	
	public static class HIDNumberEvent extends GwtEvent<HIDNumberHandler> {
		public static Type<HIDNumberHandler> TYPE = new Type<HIDNumberHandler>();

		private int keyCode;
		public HIDNumberEvent(int keyCode) {
			this.keyCode = keyCode;
		}
		
		public int getKeyCode() {
			return keyCode;
		}
		
		@Override
		public final Type<HIDNumberHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(HIDNumberHandler handler) {
			handler.onEvent(this);
		}
	}
	
	public static final int CONTROL_KEY_UP					= 0;
	public static final int CONTROL_KEY_DOWN				= 1;
	public static final int CONTROL_KEY_LEFT				= 2;
	public static final int CONTROL_KEY_RIGHT				= 3;
	public static final int CONTROL_SYSTEM_ENGAGE			= 4;
	public static final int CONTROL_SYSTEM_ENGAGE_BY_CANCEL	= 5;
	public static final int CONTROL_SYSTEM_ROLLBACK			= 6;
	
	
	// 处理 控制输入事件
	public interface HIDControlHandler extends EventHandler {
		void onEvent(HIDControlEvent event);
	}
	
	public static class HIDControlEvent extends GwtEvent<HIDControlHandler> {
		public static Type<HIDControlHandler> TYPE = new Type<HIDControlHandler>();

		private int keyCode;
		public HIDControlEvent(int keyCode) {
			this.keyCode = keyCode;
		}
		
		public int getControlCode() {
			return keyCode;
		}
		
		@Override
		public final Type<HIDControlHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(HIDControlHandler handler) {
			handler.onEvent(this);
		}
	}
	
	// 处理 sticklet调用事件
	public interface CallerHandler extends EventHandler {
		void onEvent(CallerEvent event);
	}
	
	public static class CallerEvent extends GwtEvent<CallerHandler> {
		public static Type<CallerHandler> TYPE = new Type<CallerHandler>();

		private Sticklet sticklet;
		public CallerEvent(Sticklet sticklet) {
			this.sticklet = sticklet;
		}
		
		public Sticklet getSticklet() {
			return sticklet;
		}
		
		@Override
		public final Type<CallerHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(CallerHandler handler) {
			handler.onEvent(this);
		}
	}

	// 处理 RemoteRequestResponse事件
	public interface RemoteResponseHandler extends EventHandler {
		void onEvent(ResponseEvent event);
	}
	
	public static class ResponseEvent extends GwtEvent<RemoteResponseHandler> {
		public static Type<RemoteResponseHandler> TYPE = new Type<RemoteResponseHandler>();

		private String[][] sticklet_src;
		public ResponseEvent(String[][] src) {
			sticklet_src = src;
		}
		
		public String[][] getSticklet_src() {
			return sticklet_src;
		}
		
		@Override
		public final Type<RemoteResponseHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(RemoteResponseHandler handler) {
			handler.onEvent(this);
		}
	}

}

