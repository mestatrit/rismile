package com.risetek.rismile.client.control;

import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.risetek.rismile.client.http.RequestFactory;

public abstract class RismileTableController{
	static private String SELECT = "SqlSelect";
	static private String EXECTE = "SqlExecte";
	static private String EXECTE_MUL = "SqlExecteMultiple";
	
	RequestFactory objectFactory;
	String tableName;
	
	public RismileTableController(String tableName){
		this.tableName = tableName;
		objectFactory = RequestFactory.getInstance();
	}
	public void loadTableData(String formFunction, String query, IAction action){
		objectFactory.get(formFunction, query, new RismileTableCallback(action));
	}
	public void changeTableData(String formFunction, String query, IAction action){
		objectFactory.get(formFunction, query, new PlainCallback(action));
	}
	public void loadTableData(String condition, String orderBy, String order, 
			int limit, int offset, IAction action){

		String limitClause = " LIMIT " + limit + " OFFSET " + offset;
		String orderClause = " ORDER BY "+orderBy+" "+order;
		String dataSql;
		String countSql;
		if(condition != null){
			dataSql = "SELECT rowid, " + " * FROM " + tableName + " WHERE " + 
							condition + orderClause + limitClause + ";";
			countSql =  "SELECT count(*) as TOTAL FROM " + tableName + " WHERE " + condition + ";";
		}else{
			dataSql = "SELECT rowid, " + " * FROM " + tableName + orderClause + limitClause + ";";
			countSql =  "SELECT count(*) as TOTAL FROM " + tableName + ";";
		}
		String sqls = dataSql+"&"+countSql;
		select(sqls, new RismileTableCallback(action));
	}
	
	public void addRecord(String columns, String values, IAction action){
		
		String sql;
		if(columns != null){
			sql = "INSERT INTO " + tableName + " " + columns + " VALUES " + values + ";";
		} else {
			sql = "INSERT INTO " + tableName + " VALUES " + values + ";";
		}
		execte(sql, new PlainCallback(action));
	}
	
	public void updateRecord(String columns, String criteria, IAction action){
		String sql = "UPDATE " + tableName + " SET " + columns + " WHERE " + criteria + ";";
		execte(sql, new PlainCallback(action));
	}
	
	public void deleteRecord(String criteria, IAction action){
		String sql = "DELETE FROM " + tableName+ " WHERE " + criteria +";";
		execte(sql, new PlainCallback(action));
	}
	
	public void emptyTable(IAction action){
		String sql = "DELETE FROM "+tableName+";";
		execte(sql, new PlainCallback(action));
	}
	
	public void select(String sqls, ModelCallback handler){
		objectFactory.post(SELECT, sqls, handler );
	}
	
	public void execte(String sql, PlainCallback handler){
		objectFactory.post(EXECTE, sql, handler );
	}
	public void execteMultiple(String sqls, IAction action){
		objectFactory.post(EXECTE_MUL, sqls, new PlainCallback(action));
	}
	public String getAddSql(String columns, String values){
		String sql = "INSERT INTO " + tableName + " " + columns + " VALUES " + values + ";";
		if(columns != null){
			sql = "INSERT INTO " + tableName + " " + columns + " VALUES " + values + ";";
		} else {
			sql = "INSERT INTO " + tableName  + " VALUES " + values + ";";
		}
		return sql;
	}
	public String getUpdateSql(String columns, String criteria){
		String sql = "UPDATE " + tableName + " SET " + columns + " WHERE " + criteria + ";";
		return sql;
	}
	public String getDeleteSql(String criteria){
		String sql = "DELETE FROM " + tableName+ " WHERE " + criteria +";";
		return sql;
	}

	protected String getElementText( Element item, String value ) {
		String result = "";
		NodeList itemList = item.getElementsByTagName(value);
		if( itemList.getLength() > 0 && itemList.item(0).hasChildNodes()) {

			result = itemList.item(0).getFirstChild().getNodeValue();
		}
		return result;
	}
	
	abstract public void onSuccessResponse(Response response, IAction action);
	
	class RismileTableCallback extends ModelCallback{

		public RismileTableCallback(IAction action) {
			super(action);
			// TODO Auto-generated constructor stub
		}

		public void onResponse(Response response) {
			// TODO Auto-generated method stub
			if(response.getStatusCode() == 200){
				RismileTableController.this.onSuccessResponse(response, action);
			}else{
				action.onUnreach("请求失败！返回："+response.getStatusText()+"。");
			}
		}
		
	}
}
