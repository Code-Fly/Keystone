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
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

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
    @RequestMapping(value = "/user/sns/query/{openId}/{accessToken}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject getSNSUserInfo(HttpServletRequest request, HttpServletResponse response,
                                     @PathVariable String openId,
                                     @PathVariable String accessToken
    ) throws ConnectionFailedException, WeChatException, AccessTokenException {
        return userService.getSNSUserInfo(accessToken, openId);
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
    @RequestMapping(value = "/user/sns/oauth", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject SNSUserOAuth(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, WeChatException, OAuthException, AccessTokenException {

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        // 用户同意授权
        if (!"authdeny".equals(code) && null != code) {
            // 获取网页授权access_token
            JSONObject sat = userService.getOauth2AccessToken(Const.WECHART_APP_ID, Const.WECHART_APP_SECRET, code);
            // 用户标识
            String openId = sat.getString("openid");
            if ("snsapi_userinfo".equals(sat.getString("scope"))) {
                return userService.getSNSUserInfo(sat.getString("access_token"), openId);
            } else {
                // 调用接口获取access_token
                JSONObject at = coreService.getAccessToken(Const.WECHART_APP_ID, Const.WECHART_APP_SECRET);
                return userService.getWeChatUserInfo(request, openId);
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
    @RequestMapping(value = "/user/query/{openId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject getWeChatUserInfo(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable String openId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {
        return userService.getWeChatUserInfo(request, openId);

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
    @RequestMapping(value = "/user/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject getWeChatUserList(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "nextOpenId", required = false, defaultValue = "0") String nextOpenId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException {

        if ("0".equals(nextOpenId))
            nextOpenId = null;

        return userService.getWeChatUserList(nextOpenId);
    }

    /**
     * 更新用户备注
     *
     * @param request
     * @param response
     * @param openId
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/user/remark/update/{openId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject updateWeChatUserRemark(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @PathVariable String openId,
                                             @RequestParam(value = "remark", required = true) String remark
//                                             @RequestBody String remark
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {
        return userService.updateWeChatUserRemark(openId, remark);
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
    @RequestMapping(value = "/user/group/list", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject getWeChatUserGroupList(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {
        String at = KeystoneUtil.getAccessToken();
        return userService.getWeChatUserGroupList();
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
    @RequestMapping(value = "/user/group/query/{openId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject getWeChatUserGroupByOpenId(HttpServletRequest request, HttpServletResponse response,
                                                 @PathVariable String openId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {
        String at = KeystoneUtil.getAccessToken();
        return userService.getWeChatUserGroupByOpenId(openId);
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
    @RequestMapping(value = "/user/group/rename/{groupId}/{name}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject renameWeChatUserGroup(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable String groupId,
                                            @PathVariable String name
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {
        String at = KeystoneUtil.getAccessToken();

        return userService.renameWeChatUserGroup(groupId, name);
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
    @RequestMapping(value = "/user/group/update/{openId}/{toGroupId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject updateWeChatUserGroup(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable String openId,
                                            @PathVariable String toGroupId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {
        String at = KeystoneUtil.getAccessToken();

        return userService.updateWeChatUserGroup(openId, toGroupId);
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
    @RequestMapping(value = "/user/group/batchUpdate", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject batchUpdateWeChatUserGroup(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(value = "openIds", required = true) String openIds,
                                                 @RequestParam(value = "toGroupId", required = true) String toGroupId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {
        String at = KeystoneUtil.getAccessToken();

        return userService.batchUpdateWeChatUserGroup(JSONArray.fromObject(openIds), toGroupId);
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
    @RequestMapping(value = "/user/group/delete/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject deleteWeChatUserGroup(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable String groupId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {
        String at = KeystoneUtil.getAccessToken();

        return userService.deleteWeChatUserGroup(groupId);
    }

}
