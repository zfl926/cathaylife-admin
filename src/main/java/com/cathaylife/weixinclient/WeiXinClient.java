package com.cathaylife.weixinclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cathaylife.weixinclient.entity.WAccessToken;
import com.cathaylife.weixinclient.entity.WCreateGroup;
import com.cathaylife.weixinclient.entity.WErrorMsg;
import com.cathaylife.weixinclient.entity.WGroup;
import com.cathaylife.weixinclient.entity.WGroups;
import com.cathaylife.weixinclient.entity.WMoveUserToGroup;
import com.cathaylife.weixinclient.entity.WUser;
import com.cathaylife.weixinclient.entity.WUserIds;
import com.cathaylife.weixinclient.http.HttpClientManager;
import com.cathaylife.weixinclient.utils.JsonUtils;

public class WeiXinClient {
	
	private static HttpClientManager httpManager = HttpClientManager.getInstatnce();
	private String appId;
	private String appSecret;
	private String baseUrl;
	private volatile String appToken = "";
	
	public WeiXinClient(String baseUrl, String appId, String appSecret){
		this.baseUrl = baseUrl;
		this.appId = appId;
		this.appSecret = appSecret;
	}
	
	public static enum RequestPath {
		
		CREATE_GROUP("/groups/create"),
		GET_ALL_GROUP("/groups/get"),
		UPDATE_GROUP("/groups/update"),
		MOVETO_GROUP("/groups/members/update"),
		GET_USERINFO("/user/info"),
		GET_ALLUSERID("/user/get");
		
		private String name;
		
		private RequestPath(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
	}
	
	public static enum RequestType {
		GET, POST;
	}
	
	
	public static enum ActionType {
		
		CREATE_GROUP(RequestPath.CREATE_GROUP, RequestType.POST),
		GET_ALL_GROUP(RequestPath.GET_ALL_GROUP, RequestType.GET),
		UPDATE_GROUP(RequestPath.UPDATE_GROUP, RequestType.POST),
		MOVETO_GROUP(RequestPath.MOVETO_GROUP, RequestType.POST),
		GET_USERINFO(RequestPath.GET_USERINFO, RequestType.GET),
		GET_ALLUSERID(RequestPath.GET_ALLUSERID, RequestType.GET);
		
		private RequestPath path;
		private RequestType reqType;
		
		private ActionType(RequestPath path, RequestType reqType){
			this.path = path;
			this.reqType = reqType;
		}
		
		
		public RequestPath getPath(){
			return path;
		}
		
		public RequestType getReqType(){
			return reqType;
		}
	}
	
	/**
	 * before call other api, we need to get the access token first
	 * @return
	 */
	private String getAccessToken(){
		String url = baseUrl + "/token";
		Map<String, String> param = new HashMap<>();
		param.put("grant_type", "client_credential");
		param.put("appid", appId);
		param.put("secret", appSecret);
		String response = httpManager.get(url, param);
		WAccessToken accessToken = null;
		if ( (accessToken = JsonUtils.parseToObj(response, WAccessToken.class)) == null ){
			throw new WeiXinException();
		}

		return accessToken.getAccess_token();
	}
	
	
	/**
	 * all the api will call this api
	 * @param action
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <JSONPARAM, OUTPUT> OUTPUT internalSend(ActionType action, Map<String, String> param, JSONPARAM jsonParam, Class<JSONPARAM> jsonParamClazz, Class<OUTPUT> outClazz){
		String url = baseUrl + action.getPath();
		String strResponse = null;
		final Map<String, String> mapParam = param;
		mapParam.put("access_token", appToken);
		switch ( action.getReqType() ){
		case GET:
			strResponse = httpManager.get(url, mapParam);			
			break;
		case POST:
			// JSON utility to parse parameter to 
			strResponse = httpManager.post(url, mapParam, JsonUtils.parseToString(jsonParam, jsonParamClazz));
		default:	
		}
		// check error
		try {
			WErrorMsg errorMsg = JsonUtils.parseToObj(strResponse, WErrorMsg.class);
			if ( errorMsg != null ){
				if ( errorMsg.getErrcode() != 0 )
					throw new Exception(strResponse);
				else
					return (OUTPUT)errorMsg;
			}
		} catch ( Exception e){
			throw new WeiXinException(e.getMessage());
		}
		
		return JsonUtils.parseToObj(strResponse, outClazz);
	}
	
	/**
	 * create group
	 * @param name
	 * @return the created group
	 */
	public WGroup createGroup(String name){
		WCreateGroup createGroup = new WCreateGroup();
		createGroup.getGroup().setName(name);
		return internalSend(ActionType.CREATE_GROUP, new HashMap<String, String>(), createGroup, WCreateGroup.class, WGroup.class);
	}
	
	/**
	 * get all the groups by the access token
	 * @return
	 */
	public WGroups getAllGroups(){
		return internalSend(ActionType.GET_ALL_GROUP, new HashMap<String, String>(), null, null, WGroups.class);
	}

	/**
	 * update group
	 * @param updateGroup
	 * @return
	 */
	public boolean updateGroup(WGroup updateGroup){
		WErrorMsg msg = internalSend(ActionType.UPDATE_GROUP, new HashMap<String, String>(), updateGroup, WGroup.class, WErrorMsg.class);
		return msg.getErrcode() == 0 ;
	}
	
	/**
	 * move one user to another place
	 * @param toGroup
	 * @return
	 */
	public boolean moveUserToGroup(WMoveUserToGroup toGroup){
		WErrorMsg msg = internalSend(ActionType.MOVETO_GROUP, new HashMap<String, String>(), toGroup, WMoveUserToGroup.class, WErrorMsg.class);
		return msg.getErrcode() == 0 ;
	}
	
	/**
	 * get user by open id
	 * @param openId
	 * @return
	 */
	public WUser getUserInfo(String openId){
		Map<String, String> param = new HashMap<>();
		param.put("openid", openId);
		param.put("lang", "zh_CN");
		return internalSend(ActionType.GET_USERINFO, param, null, null, WUser.class);
	}
	
	/**
	 * @param count
	 * @return
	 */
	public List<WUser> getUserInfos(int count){
		int loopCount = count > 10000 ? count/10000 + 1 : 1;
		List<WUser> users = new ArrayList<>();
		String lastOpenId = null;
		for ( int i = 0; i < loopCount; loopCount ++ ) {
			WUserIds ids = null;
			if ( i == 0 ){
				ids =  getUserIdsByOpenId(null);
				lastOpenId = ids.getNext_openid();
			} else {
				ids =  getUserIdsByOpenId(lastOpenId);
				lastOpenId = ids.getNext_openid();
			}
			// loop to get user information
			for ( String openId : ids.getData().getOpenid() ){
				users.add(getUserInfo(openId));
			}
		}
		
		return users;
	}
	
	/**
	 * @param openId
	 * @return
	 */
	public WUserIds getUserIdsByOpenId(String openId){
		Map<String, String> param = new HashMap<>();
		param.put("next_openid", openId);
		return internalSend(ActionType.GET_ALLUSERID, param, null, null, WUserIds.class);
	}
}