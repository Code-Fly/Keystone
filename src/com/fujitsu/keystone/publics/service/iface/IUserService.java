/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author Barrie
 */
public interface IUserService {
    JSONObject getOauth2AccessToken(String appId, String appSecret, String code) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject refreshOauth2AccessToken(String appId, String refreshToken) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getSNSUserInfo(String snsAccessToken, String openId) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getWeChatUserInfo(String openId) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getWeChatUserInfo(HttpServletRequest request, String openId) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getWeChatUserList(String nextOpenId) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject updateWeChatUserRemark(String openId, String remark) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    JSONObject getWeChatUserGroupList() throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getWeChatUserGroupByOpenId(String openId) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    JSONObject renameWeChatUserGroup(String groupId, String name) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    JSONObject updateWeChatUserGroup(String openId, String toGroupId) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    JSONObject batchUpdateWeChatUserGroup(JSONArray openIds, String toGroupId) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    JSONObject deleteWeChatUserGroup(String groupId) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;
}
