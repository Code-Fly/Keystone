/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Barrie
 */
public interface IUserService {
    JSONObject getOauth2AccessToken(String appId, String appSecret, String code) throws ConnectionFailedException, WeChatException;

    JSONObject refreshOauth2AccessToken(String appId, String refreshToken) throws ConnectionFailedException, WeChatException;

    JSONObject getSNSUserInfo(String accessToken, String openId) throws ConnectionFailedException, WeChatException;

    JSONObject getWeChatUserInfo(String accessToken, String openId) throws ConnectionFailedException, WeChatException;

    JSONObject getWeChatUserInfo(HttpServletRequest request, String accessToken, String openId) throws ConnectionFailedException, WeChatException;

    JSONObject getWeChatUserList(String accessToken, String nextOpenId) throws ConnectionFailedException, WeChatException;

    JSONObject getWeChatUserGroupList(String accessToken) throws ConnectionFailedException, WeChatException;

    JSONObject getWeChatUserGroupByOpenId(String accessToken, String openId) throws ConnectionFailedException, WeChatException;

    JSONObject renameWeChatUserGroup(String accessToken, String groupId, String name) throws ConnectionFailedException, WeChatException;

    JSONObject updateWeChatUserGroup(String accessToken, String openId, String toGroupId) throws ConnectionFailedException, WeChatException;

    JSONObject batchUpdateWeChatUserGroup(String accessToken, JSONArray openIds, String toGroupId) throws ConnectionFailedException, WeChatException;

    JSONObject deleteWeChatUserGroup(String accessToken, String groupId) throws ConnectionFailedException, WeChatException;
}
