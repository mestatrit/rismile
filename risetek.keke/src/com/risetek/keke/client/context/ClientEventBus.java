package com.risetek.keke.client.context;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class ClientEventBus {

	public static ClientEventBus INSTANCE = new ClientEventBus();
	
	private ClientEventBus(){
		
	}
	
	HandlerManager handleManager = new HandlerManager(this);

	public void fireEvent(GwtEvent<?> event) {
		handleManager.fireEvent(event);
	}

	final <H extends EventHandler> HandlerRegistration addHandler(
			final H handler, GwtEvent.Type<H> type) {
		return handleManager.addHandler(type, handler);
	}
	
	// 处理 UP 事件
	public interface HIDUPHandler extends EventHandler {
		void onEvent(HIDUPEvent event);
	}
	
	public static class HIDUPEvent extends GwtEvent<HIDUPHandler> {
		private static Type<HIDUPHandler> TYPE = new Type<HIDUPHandler>();

		@Override
		public final Type<HIDUPHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(HIDUPHandler handler) {
			handler.onEvent(this);
		}
	}
	
	// 处理  DOWN 事件
	public interface HIDDOWNHandler extends EventHandler {
		void onEvent(HIDDOWNEvent event);
	}
	
	public static class HIDDOWNEvent extends GwtEvent<HIDDOWNHandler> {
		private static Type<HIDDOWNHandler> TYPE = new Type<HIDDOWNHandler>();

		@Override
		public final Type<HIDDOWNHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(HIDDOWNHandler handler) {
			handler.onEvent(this);
		}
	}

	// 处理  LEFT 事件
	public interface HIDLEFTHandler extends EventHandler {
		void onEvent(HIDLEFTEvent event);
	}
	
	public static class HIDLEFTEvent extends GwtEvent<HIDLEFTHandler> {
		private static Type<HIDLEFTHandler> TYPE = new Type<HIDLEFTHandler>();

		@Override
		public final Type<HIDLEFTHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(HIDLEFTHandler handler) {
			handler.onEvent(this);
		}
	}

	// 处理  RIGHT 事件
	public interface HIDRIGHTHandler extends EventHandler {
		void onEvent(HIDRIGHTEvent event);
	}
	
	public static class HIDRIGHTEvent extends GwtEvent<HIDRIGHTHandler> {
		private static Type<HIDRIGHTHandler> TYPE = new Type<HIDRIGHTHandler>();

		@Override
		public final Type<HIDRIGHTHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(HIDRIGHTHandler handler) {
			handler.onEvent(this);
		}
	}


}

