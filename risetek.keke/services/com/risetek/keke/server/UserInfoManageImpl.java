package com.risetek.keke.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.risetek.keke.server.db.PMF;
import com.risetek.keke.server.db.UserInfo;
import com.risetek.keke.server.db.Util;
import com.risetek.keke.server.process.Help;
import com.risetek.keke.server.process.Login;
import com.risetek.keke.server.process.News;
import com.risetek.keke.server.process.PeopleRSS;

@SuppressWarnings("unchecked")
public class UserInfoManageImpl extends HttpServlet {

	private static final long serialVersionUID = 7192902650978408126L;
	private ArrayList<UserInfo> loginedUserList = new ArrayList<UserInfo>();
	private UserInfo lastLoginUserInfo;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setByteStream(req.getInputStream());
			Document doc = docBuilder.parse(is);
			NodeList nodelist = doc.getElementsByTagName("RemoteService");

			Node method = nodelist.item(0);
			if (method != null) {
				
				NodeList params = method.getChildNodes();
				Stack<String>  param =  new Stack<String>();
				for( int loop = 0; loop < params.getLength(); loop++ ) {
					if( params.item(loop).getFirstChild() == null )
						param.push("");
					else
						param.push(params.item(loop).getFirstChild().getNodeValue());
				}
				String value = null;
				if( param.size() > 0)
					value = param.pop();
					
				if ("epay/login".equals(value)) {
					Login.process(resp, param);
				}
				else if ("epay/news".equals(value)) {
					News.process(resp, param);
				}
				else if ("epay/people".equals(value)) {
					PeopleRSS.process(resp, param);
				}
				else if ("epay/help".equals(value)) {
					Help.process(resp, param);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void addUserInfo(UserInfo userInfo) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(userInfo != null){
			pm.makePersistent(userInfo);
		}
		pm.close();
	}

	public void delUserInfo(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(id!=null){
			String query = "select from " + UserInfo.class.getName() + " where id == " + id;
			List<UserInfo> result = (List<UserInfo>)pm.newQuery(query).execute(); 
			UserInfo ui = result.get(0);
			pm.deletePersistent(ui);
		}
		pm.close();
	}

	public UserInfo[] getAllUserInfo() {
		UserInfo[] result = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<UserInfo> list = (List<UserInfo>)pm.newQuery(UserInfo.class).execute();
		if(list!=null){
			result = new UserInfo[list.size()];
			for(int i=0;i<result.length;i++){
				result[i] = list.get(i);
			}
		} else {
			result = new UserInfo[0];
		}
		pm.close();
		return result;
	}

	public void updateUserInfo(UserInfo userInfo) {
		userInfo.setLastLoginAddress(getIpAddr());
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(userInfo!=null){
			pm.makePersistent(userInfo);
		}
		pm.close();
	}

	public UserInfo getLoginedUser(){
//		Cookies.getCookie("");
		UserInfo userInfo = null;
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		UserInfo[] uis = getAllUserInfo();
		for(int i=0;i<uis.length;i++){
			UserInfo ui = uis[i];
			Object value = ms.get(Long.toString(ui.getId()));
			if(value instanceof String){
				userInfo = stringToUserInfo((String)value);
			}
		}
		return userInfo;
	}
	
	public void setLoginedUser(UserInfo userInfo){
		String id = Long.toString(userInfo.getId());
		String value = userInfoToString(userInfo);
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		Expiration expiration = Expiration.byDeltaSeconds(Util.SESSION_MAX_LEFT);
		ms.put(id, value, expiration);
		lastLoginUserInfo = userInfo;
	}

	public ArrayList<UserInfo> getLoginedUserList() {
		return loginedUserList;
	}

	public void addLoginedUserList(UserInfo userInfo) {
		userInfo.setLastLoginAddress(getIpAddr());
		loginedUserList.add(userInfo);
	}

	public void removeLoginedUser() {
		/*
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession();
		Enumeration names = session.getAttributeNames();
		if(names.hasMoreElements()){
			String name = (String)names.nextElement();
			String value = (String)session.getAttribute(name);
			UserInfo ui = stringToUserInfo(value);
			removeLoginedUserToList(ui);
			session.removeAttribute(name);
			session = null;
			request = null;
		} else {
			removeLoginedUserToList(lastLoginUserInfo);
		}
		*/
	}

	public void removeLoginedUserToList(UserInfo userInfo) {
		userInfo.setLastLoginAddress(getIpAddr());
		UserInfo ui = userInfo;
		int index = 0;
		for(int i=0;i<loginedUserList.size();i++){
			UserInfo tui = loginedUserList.get(i);
			if(tui.getName().equals(ui.getName())){
				index = i;
				break;
			}
		}
		loginedUserList.remove(index);
	}

	public void refreshLoginedUserList(UserInfo userInfo) {
		userInfo.setLastLoginAddress(getIpAddr());
		removeLoginedUserToList(lastLoginUserInfo);
		loginedUserList.add(userInfo);
	}
	
	private String getIpAddr() {
		/*
		HttpServletRequest request = this.getThreadLocalRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip + ":" + request.getRemotePort() + "&" + request.getRemoteHost();
		*/
		return "1.1.1.1";
	}
	
	private String userInfoToString(UserInfo userInfo){
		String value = Long.toString(userInfo.getId()) + "," + 
		   userInfo.getName() + "," +
		   userInfo.getLoginName() + "," +
		   userInfo.getPass() + "," +
		   userInfo.getLastLoginAddress() + "," +
		   Long.toString(userInfo.getLastLoginTime().getTime()) + "," +
		   Integer.toString(userInfo.getCompetence());
		return value;
	}
	
	private UserInfo stringToUserInfo(String value){
		UserInfo userInfo = new UserInfo();
		String[] temp = value.split(",");
		if(temp.length<3){
			return null;
		} else {
			userInfo.setId(Long.parseLong(temp[0]));
			userInfo.setName(temp[1]);
			userInfo.setLoginName(temp[2]);
			userInfo.setPass(temp[3]);
			userInfo.setLastLoginAddress(temp[4]);
			userInfo.setLastLoginTime(new Date(Long.parseLong(temp[5])));
			userInfo.setCompetence(Integer.parseInt(temp[6]));
		}
		return userInfo;
	}
}
