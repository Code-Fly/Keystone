/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.ConfigUtil;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMenuService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class MenuController extends BaseController {
    @Resource
    IMenuService menuService;
    @Resource
    ICoreService coreService;

    /**
     * 创建菜单
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/create", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String create(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {
        // 调用接口获取access_token
        String at = KeystoneUtil.getAccessToken();

        String menuStr = ConfigUtil.getJson("menu.json");

        // 调用接口创建菜单
        JSONObject resp = JSONObject.fromObject(menuService.create(at, JSONObject.fromObject(menuStr)));
        return resp.toString();
    }

    /**
     * 获取菜单
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/get", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {
        // 调用接口获取access_token
        String at = KeystoneUtil.getAccessToken();

        // 调用接口创建菜单
        JSONObject resp = JSONObject.fromObject(menuService.get(at));
        return resp.toString();
    }

    /**
     * 删除菜单
     *
     * @param request  request
     * @param response response
     * @return
     * @throws ConnectionFailedException
     * @throws AccessTokenException
     * @throws WeChatException
     */
    @RequestMapping(value = "/menu/delete", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {
        // 调用接口获取access_token
        String at = KeystoneUtil.getAccessToken();

        // 调用接口创建菜单
        JSONObject resp = JSONObject.fromObject(menuService.delete(at));
        return resp.toString();
    }
}
