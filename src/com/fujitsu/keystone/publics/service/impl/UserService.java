/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
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
    public JSONObject getOauth2AccessToken(String appId, String appSecret, String code) throws ConnectionFailedException, WeChatException {
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
    public JSONObject refreshOauth2AccessToken(String appId, String refreshToken) throws ConnectionFailedException, WeChatException {

        String url = Const.PublicPlatform.URL_SNS_OAUTH2_TOKEN_REFRESH.replace("APPID", appId).replace("REFRESH_TOKEN", refreshToken);
        // 刷新网页授权凭证
        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     * @return SNSUserInfo
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject getSNSUserInfo(String accessToken, String openId) throws ConnectionFailedException, WeChatException {
        // SNSUserInfo snsUserInfo = null;

        String url = Const.PublicPlatform.URL_USER_GET_SNS_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户信息
     *
     * @param accessToken 接口访问凭证
     * @param openId      用户标识
     * @return WeixinUserInfo
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject getWeChatUserInfo(String accessToken, String openId) throws ConnectionFailedException, WeChatException {
        // WeChatUserInfo wechatUserInfo = null;

        String url = Const.PublicPlatform.URL_USER_GET_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @param accessToken
     * @param openId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject getWeChatUserInfo(HttpServletRequest request, String accessToken, String openId) throws ConnectionFailedException, WeChatException {

        JSONObject resp = getWeChatUserInfo(accessToken, openId);
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
     * @param accessToken
     * @param nextOpenId
     * @return
     * @throws ConnectionFailedException
     */
    @Override
    public JSONObject getWeChatUserList(String accessToken, String nextOpenId) throws ConnectionFailedException, WeChatException {
        // WeChatUserList wechatUserList = null;
        if (null == nextOpenId)
            nextOpenId = "";

        String url = Const.PublicPlatform.URL_USER_GET_LIST.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);

        String response = WeChatClientUtil.post(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户列表
     *
     * @param accessToken
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject getWeChatUserGroupList(String accessToken) throws ConnectionFailedException, WeChatException {

        String url = Const.PublicPlatform.URL_USER_GROUP_GET_LIST.replace("ACCESS_TOKEN", accessToken);

        String response = WeChatClientUtil.get(url, CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 获取用户列表
     *
     * @param accessToken
     * @param openId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject getWeChatUserGroupByOpenId(String accessToken, String openId) throws ConnectionFailedException, WeChatException {

        String url = Const.PublicPlatform.URL_USER_GROUP_GET_BY_OPENID.replace("ACCESS_TOKEN", accessToken);
        JSONObject request = new JSONObject();
        request.put("openid", openId);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 修改分组名
     *
     * @param accessToken
     * @param groupId
     * @param name
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject renameWeChatUserGroup(String accessToken, String groupId, String name) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_USER_GROUP_RENAME.replace("ACCESS_TOKEN", accessToken);
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
     * @param accessToken
     * @param openId
     * @param toGroupId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject updateWeChatUserGroup(String accessToken, String openId, String toGroupId) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_USER_GROUP_UPDATE.replace("ACCESS_TOKEN", accessToken);
        JSONObject request = new JSONObject();
        request.put("openid", openId);
        request.put("to_groupid", toGroupId);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 批量移动用户分组
     *
     * @param accessToken
     * @param openIds
     * @param toGroupId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject batchUpdateWeChatUserGroup(String accessToken, JSONArray openIds, String toGroupId) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_USER_GROUP_BATCH_UPDATE.replace("ACCESS_TOKEN", accessToken);
        JSONObject request = new JSONObject();
        request.put("openid_list", openIds);
        request.put("to_groupid", toGroupId);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * 删除分组
     *
     * @param accessToken
     * @param groupId
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @Override
    public JSONObject deleteWeChatUserGroup(String accessToken, String groupId) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_USER_GROUP_DELETE.replace("ACCESS_TOKEN", accessToken);
        JSONObject request = new JSONObject();
        JSONObject group = new JSONObject();
        group.put("id", groupId);
        request.put("group", group);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }
}
