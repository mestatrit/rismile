package com.risetek.keke.client.context;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

public class ClientEventBus {

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
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(CONTROL_KEY_DOWN));
					break;
				case KeyCodes.KEY_UP:
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(CONTROL_KEY_UP));
					break;
				case KeyCodes.KEY_LEFT:
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(CONTROL_KEY_LEFT));
					break;
				case KeyCodes.KEY_RIGHT:
					ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.HIDControlEvent(CONTROL_KEY_RIGHT));
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
	
	public static final int CONTROL_KEY_UP		= 0;
	public static final int CONTROL_KEY_DOWN	= 1;
	public static final int CONTROL_KEY_LEFT	= 2;
	public static final int CONTROL_KEY_RIGHT	= 3;
	
	
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
	
}

