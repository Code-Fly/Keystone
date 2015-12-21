/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.FileUtil;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.entity.account.WeChatUserInfo;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Barrie
 */
@Service
public class UserService extends BaseService implements IUserService {
    @Resource
    ICoreService coreService;

    /**
     * 获取网页授权凭证
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject getOauth2AccessToken(String appId, String appSecret, String code) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_SNS_OAUTH2_TOKEN_GET.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
        // 获取网页授权凭证
        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 刷新网页授权凭证
     *
     * @param appId
     * @param refreshToken
     * @return
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject refreshOauth2AccessToken(String appId, String refreshToken) throws ConnectionFailedException, WeChatException, AccessTokenException {

        String url = Const.PublicPlatform.URL_SNS_OAUTH2_TOKEN_REFRESH.replace("APPID", appId).replace("REFRESH_TOKEN", refreshToken);
        // 刷新网页授权凭证
        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 通过网页授权获取用户信息
     *
     * @param openId 用户标识
     * @return SNSUserInfo
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject getSNSUserInfo(String snsAccessToken, String openId) throws ConnectionFailedException, WeChatException, AccessTokenException {
        // SNSUserInfo snsUserInfo = null;

        String url = Const.PublicPlatform.URL_USER_GET_SNS_INFO.replace("SNS_ACCESS_TOKEN", snsAccessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户信息
     *
     * @param openId 用户标识
     * @return WeixinUserInfo
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject getWeChatUserInfo(String openId) throws ConnectionFailedException, WeChatException, AccessTokenException {
        // WeChatUserInfo wechatUserInfo = null;

        String url = Const.PublicPlatform.URL_USER_GET_INFO.replace("OPENID", openId);
        // 获取用户信息
        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @param openId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject getWeChatUserInfo(HttpServletRequest request, String openId) throws ConnectionFailedException, WeChatException, AccessTokenException {

        JSONObject resp = getWeChatUserInfo(openId);
        if (resp.containsKey("errcode")) {
            logger.error(resp.toString());
            return resp;
        }
        WeChatUserInfo weUserInfo = (WeChatUserInfo) JSONObject.toBean(resp, WeChatUserInfo.class);
        String headimgurl = Const.getServerUrl(request) + FileUtil.getWeChatImage(weUserInfo.getHeadimgurl() + "?wx_fmt=jpeg", FileUtil.CATEGORY_USER, weUserInfo.getOpenid(), false);
        weUserInfo.setHeadimgurl(headimgurl);
        return JSONObject.fromObject(weUserInfo);
    }

    /**
     * 获取关注者列表
     *
     * @param nextOpenId
     * @return
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject getWeChatUserList(String nextOpenId) throws ConnectionFailedException, WeChatException, AccessTokenException {
        // WeChatUserList wechatUserList = null;
        if (null == nextOpenId)
            nextOpenId = "";

        String url = Const.PublicPlatform.URL_USER_GET_LIST.replace("NEXT_OPENID", nextOpenId);

        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户列表
     *
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject getWeChatUserGroupList() throws ConnectionFailedException, WeChatException, AccessTokenException {

        String url = Const.PublicPlatform.URL_USER_GROUP_GET_LIST;

        String response = WeChatClientUtil.get(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户列表
     *
     * @param openId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject getWeChatUserGroupByOpenId(String openId) throws ConnectionFailedException, WeChatException, AccessTokenException {

        String url = Const.PublicPlatform.URL_USER_GROUP_GET_BY_OPENID;
        JSONObject request = new JSONObject();
        request.put("openid", openId);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 修改分组名
     *
     * @param groupId
     * @param name
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject renameWeChatUserGroup(String groupId, String name) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_USER_GROUP_RENAME;
        JSONObject request = new JSONObject();
        JSONObject group = new JSONObject();
        group.put("id", groupId);
        group.put("name", name);
        request.put("group", group);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 移动用户分组
     *
     * @param openId
     * @param toGroupId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject updateWeChatUserGroup(String openId, String toGroupId) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_USER_GROUP_UPDATE;
        JSONObject request = new JSONObject();
        request.put("openid", openId);
        request.put("to_groupid", toGroupId);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 批量移动用户分组
     *
     * @param openIds
     * @param toGroupId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject batchUpdateWeChatUserGroup(JSONArray openIds, String toGroupId) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_USER_GROUP_BATCH_UPDATE;
        JSONObject request = new JSONObject();
        request.put("openid_list", openIds);
        request.put("to_groupid", toGroupId);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 删除分组
     *
     * @param groupId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject deleteWeChatUserGroup(String groupId) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.PublicPlatform.URL_USER_GROUP_DELETE;
        JSONObject request = new JSONObject();
        JSONObject group = new JSONObject();
        group.put("id", groupId);
        request.put("group", group);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }
}
