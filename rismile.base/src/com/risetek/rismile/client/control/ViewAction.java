package com.risetek.rismile.client.control;

import com.risetek.rismile.client.utils.MessageConsole;


public abstract class ViewAction implements IAction{

	protected String startMessage = "正在向服务器发送请求...";;
	protected String successMessage = "请求成功返回！";;
	
	public ViewAction(){
		
	}
	public ViewAction(String startMessage, String successMessage){
		
		this.startMessage = startMessage;
		this.successMessage = successMessage;

	}
	public void onStart() {
		// TODO Auto-generated method stub
		MessageConsole.setText(startMessage);
	}

	public void onSuccess(){
		// TODO Auto-generated method stub
		MessageConsole.setText(successMessage);
	}
	
	public void onFailure(String message) {
		// TODO Auto-generated method stub
		MessageConsole.setText(message);
	}
	
	public void onUnreach(String message) {
		// TODO Auto-generated method stub
		MessageConsole.setText(message);
	}

	public void onFinish() {
		// TODO Auto-generated method stub
		
	}

}
