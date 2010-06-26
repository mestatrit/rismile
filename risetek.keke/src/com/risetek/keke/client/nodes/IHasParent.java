package com.risetek.keke.client.nodes;

import com.risetek.keke.client.context.PosContext;
import com.risetek.keke.client.data.AWidget;

public interface IHasParent {
	public Node getParent(PosContext pc);
	public Node getParent(AWidget widget);
}
