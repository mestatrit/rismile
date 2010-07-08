package com.risetek.keke.server.db;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/*
@SuppressWarnings("unchecked")
public class UserInfoManageImpl extends RemoteServiceServlet implements
		UserInfoManageService {

	private static final long serialVersionUID = 7192902650978408126L;
	
	private ArrayList<UserInfo> loginedUserList = new ArrayList<UserInfo>();
	
	private UserInfo lastLoginUserInfo;
	
//	private HttpSession session;
	
//	private HttpServletRequest request;

	@Override
	public void addUserInfo(UserInfo userInfo) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(userInfo != null){
			pm.makePersistent(userInfo);
		}
		pm.close();
	}

	@Override
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

	@Override
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

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfo.setLastLoginAddress(getIpAddr());
		PersistenceManager pm = PMF.get().getPersistenceManager();
		if(userInfo!=null){
			pm.makePersistent(userInfo);
		}
		pm.close();
	}

	@Override
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
//		Cache cache = null;
//		try {
//			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
//			cache = cacheFactory.createCache(Collections.emptyMap());
//		} catch (CacheException e) {
//			// ...
//		}
//		Map<String, String> props = Collections.emptyMap();
//		cache.getAll(props);
//		HttpServletRequest request = this.getThreadLocalRequest();
//		HttpSession session = request.getSession();
//		Enumeration names = session.getAttributeNames();
//		if(names.hasMoreElements()){
//			String name = (String)names.nextElement();
//			String value = (String)session.getAttribute(name);
//			UserInfo ui = stringToUserInfo(value);
//			return ui;
//		} else {
//			return null;
//		}
	}
	
	@Override
	public void setLoginedUser(UserInfo userInfo){
		String id = Long.toString(userInfo.getId());
		String value = userInfoToString(userInfo);
		MemcacheService ms = MemcacheServiceFactory.getMemcacheService();
		Expiration expiration = Expiration.byDeltaSeconds(Util.SESSION_MAX_LEFT);
		ms.put(id, value, expiration);
//		Cache cache = null;
//		Map props = new HashMap();
//		props.put(GCacheFactory.EXPIRATION_DELTA, Util.SESSION_MAX_LEFT);
//
//		try {
//			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
//			cache = cacheFactory.createCache(props);
//		} catch (CacheException e) {
//			// ...
//		}
//		cache.put(id, value);
//		Date left = new Date(new Date().getTime() + Util.SESSION_MAX_LEFT);
//		Cookies.setCookie(id, value, left);
//		HttpServletRequest request = this.getThreadLocalRequest();
//		HttpSession session = request.getSession();
//		userInfo.setLastLoginAddress(getIpAddr());
//		session.setMaxInactiveInterval(Util.SESSION_MAX_LEFT);
//		session.setAttribute(id, value);
		lastLoginUserInfo = userInfo;
	}

	@Override
	public ArrayList<UserInfo> getLoginedUserList() {
		return loginedUserList;
	}

	@Override
	public void addLoginedUserList(UserInfo userInfo) {
		userInfo.setLastLoginAddress(getIpAddr());
		loginedUserList.add(userInfo);
	}

	@Override
	public void removeLoginedUser() {
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

	@Override
	public void refreshLoginedUserList(UserInfo userInfo) {
		userInfo.setLastLoginAddress(getIpAddr());
		removeLoginedUserToList(lastLoginUserInfo);
		loginedUserList.add(userInfo);
	}
	
	private String getIpAddr() {
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
*/
