package com.risetek.rismile.client.control;

import com.risetek.rismile.client.dialog.CustomDialog;
import com.risetek.rismile.client.view.IView;

public class DialogAction implements IAction {
	
	protected String startMessage = "正在向服务器发送请求...";
	protected String successMessage = "请求成功返回！";

	private CustomDialog dialog;
	private IView dialogParent;
	
	boolean reload;
	
	public DialogAction(CustomDialog dialog, IView dialogParent){
		
		reload = true;
		this.dialog = dialog;
		this.dialogParent = dialogParent;
		
	}
	public DialogAction(CustomDialog dialog, IView dialogParent, boolean reload){
		
		this.reload = reload;
		this.dialog = dialog;
		this.dialogParent = dialogParent;
		
	}
	public DialogAction(CustomDialog dialog, IView dialogParent, String startMessage,
			String successMessage){
		
		reload = true;
		this.dialog = dialog;
		this.dialogParent = dialogParent;
		this.startMessage = startMessage;
		this.successMessage = successMessage;
	}
	public void onStart() {
		// TODO Auto-generated method stub
		dialog.setMessage(startMessage);
		dialog.enableConfirm(false);
	}
	
	public void onSuccess(Object object) {
		// TODO Auto-generated method stub
		if(object != null && object instanceof String){
			successMessage = (String)object;
		}
		
		dialog.setMessage(successMessage);
		dialog.enableConfirm(true);
		//dialog.unmask();
		dialog.hide();
		if(reload){
			dialogParent.loadModel();
		}
	}

	public void onFailure(String message) {
		// TODO Auto-generated method stub
		dialog.setMessage(message);
		dialog.enableConfirm(true);
		dialog.setFirstFocus();
	}

	public void onUnreach(String message) {
		// TODO Auto-generated method stub
		dialog.setMessage(message);
		dialog.enableConfirm(true);
		dialog.setFirstFocus();
	}

	public void onFinish() {
		// TODO Auto-generated method stub
		
	}
	
}
