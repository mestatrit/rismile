package com.risetek.keke.server.db;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;



public class Util {

	public static String CREDIT_CARD = "creditCard";
	
	public static String CREDIT_CARD_BILL = "creditCardBill";
	
	public static String CARD_FINANCE_MONTH = "CardFinanceMonth";
	
	public static String DIALY_FINANCE_MONTH = "DailyFinanceMonth";
	
	public static String FINANCE_TYPE_CONFIG = "financeTypeConfig";
	
	public static String INPUT_ACCOUNTS = "inputAccounts";
	
	public static String OUTPUT_ACCOUNTS = "outputAccounts";
	
	public static String USER_INFO_MANAGE = "userInfoManage";
	
	public static String CHANGE_PASS = "changePass";
	
	public static String LOGOUT = "logout";
	
	public static int FINANCE_TYPE_IS_CASH = 0;
	
	public static int FINANCE_TYPE_IS_CARD = 1;
	
	public static String FINANCE_TYPE_IS_CASH_NAME = "现金";
	
	public static String FINANCE_TYPE_IS_CARD_NAME = "消费卡";
	
	public static int FINANCE_TYPE_AMOUNT_IN = 0;
	
	public static int FINANCE_TYPE_AMOUNT_OUT = 1;
	
	public static String FINANCE_TYPE_AMOUNT_IN_NAME = "收入";
	
	public static String FINANCE_TYPE_AMOUNT_OUT_NAME = "支出";
	
	public static int DIALOG_CONTROL_ADD = 0;
	
	public static int DIALOG_CONTROL_EDIT = 1;
	
	public static int USER_COMPETENCE_ULTIMATE = 4;
	
	public static int USER_COMPETENCE_LOW = 1;
	
	public static int USER_COMPETENCE_MIDDLE = 2;
	
	public static int USER_COMPETENCE_HEIGHT = 3;
	
	public static String USER_COMPETENCE_ULTIMATE_NAME = "无限制";
	
	public static String USER_COMPETENCE_LOW_NAME = "初级权限";
	
	public static String USER_COMPETENCE_MIDDLE_NAME = "一般权限";
	
	public static String USER_COMPETENCE_HEIGHT_NAME = "最高权限";
	
	public static int INPUT_TEXT_MAX_LENGTH = 10;
	
	public static int SESSION_MAX_LEFT = 60 * 10;
	
	public static String AMOUNT_PATTERN = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
	
	public static String getThisMonth(){
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM");
		return dateTimeFormat.format(new Date());
	}
	
	public static String validAmount(String text){
		if ( null == text || "".equals(text)) {
			return "不能为空！";
		}
		if( text.matches(AMOUNT_PATTERN) ) {
			return null;
		}
		return "金额不符合要求！";
	}
	
	public static String validInput(String text){
		if ( null == text || "".equals(text)) {
			return "不能为空！";
		}
		return null;
	}
	
	public static String strFormat(String str){
		StringBuffer sb = new StringBuffer(str);
		sb.insert(str.length()-2, ".");
		return sb.toString();
	}
	
	public static int strToInt(String str){
		int temp = Integer.parseInt(str);
		temp = temp * 100;
		return temp;
	}
	
	public static String intToStr(int lo){
		String temp = Integer.toString(lo);
		if(lo == 0){
			temp = "00";
		}
		if(temp.length()<3){
			temp = "0" + temp;
		}
		return strFormat(temp);
	}
}
