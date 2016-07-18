package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.UrlUtil;
import com.fujitsu.keystone.publics.service.iface.ITicketService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by 123 on 2016/7/14.
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class TicketController extends BaseController {
    @Autowired
    ITicketService ticketService;

    @RequestMapping(value = "/ticket/create/temporary", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject createTemporary(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "expire", required = true) String expire,
                                      @RequestParam(value = "sceneId", required = true) String sceneId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {

        JSONObject param = new JSONObject();
        param.put("expire_seconds", expire);
        param.put("action_name", "QR_SCENE");
        param.put("action_info", JSONObject.fromObject(" {\"scene\": {\"scene_str\": \"" + sceneId + "\"}}"));
        return ticketService.createTicket(param);
    }

    @RequestMapping(value = "/ticket/create/permanent", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public JSONObject createPermanent(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "sceneId", required = true) String sceneId
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {

        JSONObject param = new JSONObject();
        param.put("action_name", "QR_LIMIT_STR_SCENE");
        param.put("action_info", JSONObject.fromObject(" {\"scene\": {\"scene_str\": \"" + sceneId + "\"}}"));
        return ticketService.createTicket(param);
    }

    @RequestMapping(value = "/ticket/query/{ticket}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public String createTemporary(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable String ticket
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {
        return ticketService.getTicket(UrlUtil.encode(ticket, CharEncoding.UTF_8), response);
    }


}
