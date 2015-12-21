/**
 *
 */
package com.fujitsu.keystone.publics.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.FileUtil;
import com.fujitsu.base.helper.KeystoneUtil;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.impl.GreeterService;
import com.fujitsu.queue.service.impl.QueueService;
import net.sf.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barrie
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class CoreController extends BaseController {

    @Resource
    ICoreService coreService;

    @Resource
    GreeterService greeterService;

    @Resource
    QueueService mq;

    @RequestMapping(value = "/core")
    public void connect(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();
        logger.info(method);
        if ("GET".equals(method)) {
            try {
                coreService.doGet(request, response);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        } else if ("POST".equals(method)) {
            try {
                coreService.doPost(request, response);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    @RequestMapping(value = "/token/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, WeChatException, JMSException {
//
//        greeterService.post("111111");
//        greeterService.post("222");
//        greeterService.post("333");
//        greeterService.get();
//        greeterService.get();
//        greeterService.get();
//
//        String queue = "queue://omiuxs4TDeYabfsAXdUh6GFfIloU";
//        mq.connect();
//        System.out.println(mq.browse(queue));
//        mq.clear(queue);
//        mq.clear(queue, "JMSType <> '" + MessageService.REQ_MESSAGE_TYPE_TEXT + "'");
//        System.out.println(mq.receive(queue, null));
//        System.out.println(mq.receive(queue, "JMSType = '" + MessageService.REQ_MESSAGE_TYPE_TEXT + "'"));
//        System.out.println(mq.browse(queue));
//        mq.close();

        return KeystoneUtil.refreshLocalAccessToken().toString();
    }

    @RequestMapping(value = "/token/query", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String queryToken(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, WeChatException {
        return KeystoneUtil.getLocalAccessToken().toString();
    }

    @RequestMapping(value = "/jsapi/ticket/query", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String queryJsapiTicket(HttpServletRequest request, HttpServletResponse response) throws ConnectionFailedException, AccessTokenException, WeChatException {
        JSONObject resp = coreService.getJsapiTicket(KeystoneUtil.getAccessToken());
        return resp.toString();
    }

    @RequestMapping(value = "/file/image/product")
    @ResponseBody
    public String getImage(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getParameter("url");
        String pid = request.getParameter("pid");
        if (null != url || null == pid) {
            url = FileUtil.getWeChatImage(url, FileUtil.CATEGORY_PRODUCT, pid, false);
        }
        return url;

    }
}
