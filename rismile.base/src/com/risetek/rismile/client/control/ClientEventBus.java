package com.risetek.rismile.client.control;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

public class ClientEventBus {

	public static void fireControlKey(int keyCode, boolean alt) {
		ClientEventBus.INSTANCE.fireEvent(new ClientEventBus.DirectKeyEvent(keyCode, alt));
	}
	public static ClientEventBus INSTANCE = new ClientEventBus();
	
	private class DirectKeyNativePreviewHandler implements NativePreviewHandler {

		@Override
		public void onPreviewNativeEvent(NativePreviewEvent event) {
			if( !event.isFirstHandler() )
				return;
			boolean alt =event.getNativeEvent().getAltKey();
			if (event.getTypeInt() == Event.ONKEYDOWN )
			{
				int keyCode = event.getNativeEvent().getKeyCode();
				fireControlKey(keyCode, alt);
			}
		}
	}
	
	NativePreviewHandler nphandler = new DirectKeyNativePreviewHandler();
	
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
	
	// 处理 数字输入 事件
	public interface DirectKeyHandler extends EventHandler {
		void onEvent(DirectKeyEvent event);
	}
	
	public static class DirectKeyEvent extends GwtEvent<DirectKeyHandler> {
		public static Type<DirectKeyHandler> TYPE = new Type<DirectKeyHandler>();

		private int keyCode;
		private boolean ALT;
		public DirectKeyEvent(int keyCode, boolean Alt) {
			this.keyCode = keyCode;
			ALT = Alt;
		}
		
		public int getKeyCode() {
			return keyCode;
		}
		
		public boolean isAlt() {
			return ALT;
		}
		
		@Override
		public final Type<DirectKeyHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(DirectKeyHandler handler) {
			handler.onEvent(this);
		}
	}
	
}

