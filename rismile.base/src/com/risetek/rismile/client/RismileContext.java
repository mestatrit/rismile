package com.risetek.rismile.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

public class RismileContext {
	private static final RismileContext INSTANCE = new RismileContext();

	private RismileContext(){}
	
	public enum OEM {
		risetek, tongfa
	};

	public static OEM OEMFlag = OEM.risetek;

	HandlerManager RisetekHandleManager = new HandlerManager(this);

	public static void fireEvent(GwtEvent<?> event) {
		INSTANCE.RisetekHandleManager.fireEvent(event);
	}

	private final <H extends EventHandler> HandlerRegistration addHandler(
			final H handler, GwtEvent.Type<H> type) {
		return RisetekHandleManager.addHandler(type, handler);
	}

	// 心跳事件处理
	public static HandlerRegistration addHeartbeatHandler(RismileHandler handler) {
		return INSTANCE.addHandler(handler,
				HeartbeatEvent.getType());
	}

	public interface RismileHandler extends EventHandler {
		void onHearbeatOK(HeartbeatEvent event);
	}

	public static class HeartbeatEvent extends GwtEvent<RismileHandler> {
		private static Type<RismileHandler> TYPE;
		public static Type<RismileHandler> getType() {
			if (TYPE == null) {
				TYPE = new Type<RismileHandler>();
			}
			return TYPE;
		}

		private String resultHtml;

		public HeartbeatEvent(String resultsHtml) {
			this.resultHtml = resultsHtml;
		}

		@Override
		public final Type<RismileHandler> getAssociatedType() {
			return TYPE;
		}

		public String getResults() {
			return resultHtml;
		}

		@Override
		protected void dispatch(RismileHandler handler) {
			handler.onHearbeatOK(this);
		}
	}

	// 心跳事件运行时处理
	public static HandlerRegistration addRunTimeHandler(RismileRuntimeHandler handler) {
		return INSTANCE.addHandler(handler,RuntimeEvent.getType());
	}

	public interface RismileRuntimeHandler extends EventHandler {
		void onRuntime(RuntimeEvent event);
	}

	public static class RuntimeEvent extends GwtEvent<RismileRuntimeHandler> {
		private static Type<RismileRuntimeHandler> TYPE;
		public static Type<RismileRuntimeHandler> getType() {
			if (TYPE == null) {
				TYPE = new Type<RismileRuntimeHandler>();
			}
			return TYPE;
		}

		private String resultHtml;

		public RuntimeEvent(String resultsHtml) {
			this.resultHtml = resultsHtml;
		}

		@Override
		public final Type<RismileRuntimeHandler> getAssociatedType() {
			return TYPE;
		}

		public String getResults() {
			return resultHtml;
		}

		@Override
		protected void dispatch(RismileRuntimeHandler handler) {
			handler.onRuntime(this);
		}
	}	
	
	// 进入/退出私有模式处理
	public static HandlerRegistration addEnablePrivaterHandler(EnablePrivateHandler handler) {
		return INSTANCE.addHandler(handler, EnablePrivateEvent.getType());
	}
	
	
	public interface EnablePrivateHandler extends EventHandler {
		void onEnablePrivate(EnablePrivateEvent event);
	}
	
	public static class EnablePrivateEvent extends GwtEvent<EnablePrivateHandler> {
		private static Type<EnablePrivateHandler> TYPE;
		public static Type<EnablePrivateHandler> getType() {
			if (TYPE == null) {
				TYPE = new Type<EnablePrivateHandler>();
			}
			return TYPE;
		}

		@Override
		public final Type<EnablePrivateHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(EnablePrivateHandler handler) {
			handler.onEnablePrivate(this);
		}
	}

}
