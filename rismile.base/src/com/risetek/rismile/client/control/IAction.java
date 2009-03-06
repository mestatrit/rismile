package com.risetek.rismile.client.control;

public interface IAction {

	void onStart();
	void onSuccess(Object object);
	void onFailure(String message);
	void onUnreach(String message);
	void onFinish();
	
}
