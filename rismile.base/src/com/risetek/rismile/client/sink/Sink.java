package com.risetek.rismile.client.sink;

import com.google.gwt.user.client.ui.Composite;

/**
 * A 'sink' is a single panel of the kitchen sink. They are meant to be lazily
 * instantiated so that the application doesn't pay for all of them on startup.
 */
public abstract class Sink extends Composite {

	/**
	 * Encapsulated information about a sink. Each sink is expected to have a
	 * static <code>init()</code> method that will be called by the kitchen sink
	 * on startup.
	 */
	public abstract static class SinkInfo {
		private Sink instance;
		private String name, description;
		private String tag;
		public int link_index;
		public SinkInfo(String tag, String name, String desc) {
			this.tag = tag;
			this.name = name;
			description = desc;

		}

		public abstract Sink createInstance();

		public String getColor() {
			return "#2a8ebf";
		}

		public String getDescription() {
			return description;
		}

		public final Sink getInstance() {
			if (instance != null) {
				return instance;
			}
			return (instance = createInstance());
		}

		public String getName() {
			return name;
		}

		public String getTag() {
			return tag;
		}
	}

	/**
	 * Called just before this sink is hidden.
	 */
	public void onHide() {
	}

	/**
	 * Called just after this sink is shown.
	 */
	public void onShow() {
	}

	public Sink() {
	}
}
