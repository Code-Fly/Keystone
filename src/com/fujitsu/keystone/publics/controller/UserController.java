/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.OAuthException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class UserController extends BaseController {
    @Resource
    ICoreService coreService;
    @Resource
    IUserService userService;

    /**
     * 获取SNS用户信息
     *
     * @param request     request
     * @param response    response
     * @param openId      openId
     * @param accessToken accessToken
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/sns/query/{openId}/{accessToken}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getSNSUserInfo(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable String openId,
                                 @PathVariable String accessToken
    ) throws ConnectionFailedException, WeChatException {

        JSONObject resp = userService.getSNSUserInfo(accessToken, openId);

        return resp.toString();
    }

    /**
     * SNS用户授权
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws WeChatException
     * @throws OAuthException
     */
    @RequestMapping(value = "/user/sns/oauth", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String SNSUserOAuth(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, WeChatException, OAuthException {

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        // 用户同意授权
        if (!"authdeny".equals(code) && null != code) {
            // 获取网页授权access_token
            JSONObject sat = userService.getOauth2AccessToken(Const.WECHART_APP_ID, Const.WECHART_APP_SECRET, code);
            // 用户标识
            String openId = sat.getString("openid");
            if ("snsapi_userinfo".equals(sat.getString("scope"))) {

                JSONObject resp = userService.getSNSUserInfo(sat.getString("access_token"), openId);

                logger.info(resp.toString());
                return resp.toString();
            } else {
                // 调用接口获取access_token
                JSONObject at = coreService.getAccessToken(Const.WECHART_APP_ID, Const.WECHART_APP_SECRET);

                JSONObject resp = userService.getWeChatUserInfo(request, at.getString("access_token"), openId);

                logger.info(resp.toString());
                return resp.toString();
            }

        }
        throw new OAuthException();
    }

    /**
     * 获取关注用户信息
     *
     * @param request  request
     * @param response response
     * @param openId   openId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/query/{openId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getWeChatUserInfo(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable String openId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        // 调用接口获取access_token
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.getWeChatUserInfo(request, at, openId);

        return resp.toString();

    }

    /**
     * 获取关注用户列表
     *
     * @param request    request
     * @param response   response
     * @param nextOpenId nextOpenId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getWeChatUserList(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "nextOpenId", required = false, defaultValue = "0") String nextOpenId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        if ("0".equals(nextOpenId))
            nextOpenId = null;

        JSONObject resp = userService.getWeChatUserList(at, nextOpenId);

        return resp.toString();
    }

    /**
     * 获取用户组列表
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/group/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getWeChatUserGroupList(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.getWeChatUserGroupList(at);

        return resp.toString();
    }

    /**
     * 查找用户所在用户组ID
     *
     * @param request  request
     * @param response response
     * @param openId   openId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/group/query/{openId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getWeChatUserGroupByOpenId(HttpServletRequest request, HttpServletResponse response,
                                             @PathVariable String openId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.getWeChatUserGroupByOpenId(at, openId);

        return resp.toString();
    }

    /**
     * @param request
     * @param response
     * @param groupId
     * @param name
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/group/rename/{groupId}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String renameWeChatUserGroup(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable String groupId,
                                        @PathVariable String name
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.renameWeChatUserGroup(at, groupId, name);

        return resp.toString();
    }

    /**
     * @param request
     * @param response
     * @param openId
     * @param toGroupId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/group/update/{openId}/{toGroupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateWeChatUserGroup(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable String openId,
                                        @PathVariable String toGroupId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.updateWeChatUserGroup(at, openId, toGroupId);

        return resp.toString();
    }

    /**
     * @param request
     * @param response
     * @param openIds
     * @param toGroupId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/group/batchUpdate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String batchUpdateWeChatUserGroup(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "openIds", required = true) String openIds,
                                             @RequestParam(value = "toGroupId", required = true) String toGroupId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.batchUpdateWeChatUserGroup(at, JSONArray.fromObject(openIds), toGroupId);

        return resp.toString();
    }

    /**
     * @param request
     * @param response
     * @param groupId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/user/group/delete/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteWeChatUserGroup(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable String groupId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();

        JSONObject resp = userService.deleteWeChatUserGroup(at, groupId);

        return resp.toString();
    }

}
