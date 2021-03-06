package com.fujitsu.database.controller;

import com.fujitsu.base.controller.BaseController;
import com.fujitsu.base.entity.ErrorMsg;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.database.entity.MessageHistoryWithBLOBs;
import com.fujitsu.database.service.iface.IMessageHistoryService;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
@Controller
@RequestMapping(value = "/api/keystone")
public class MessageHistoryController extends BaseController {
    @Autowired
    IMessageHistoryService messageHistoryService;

    @RequestMapping(value = "/message/get/{fromUser}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8)
    @ResponseBody
    public ErrorMsg getMessage(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable String fromUser
    ) throws ConnectionFailedException, AccessTokenException, WeChatException, UnsupportedEncodingException {

        MessageHistoryWithBLOBs messageHistory = new MessageHistoryWithBLOBs();
        messageHistory.setFromUser(fromUser);
        List<MessageHistoryWithBLOBs> list = messageHistoryService.getMessageHistoryList(messageHistory);
        ErrorMsg msg = new ErrorMsg("0", "success", list);
        return msg;
    }
}
