package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.service.iface.ITicketService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by 123 on 2016/7/14.
 */
@Service
public class TicketService extends BaseService implements ITicketService {
    @Override
    public JSONObject createTicket(JSONObject json) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException {
        String url = Const.PublicPlatform.URL_TICKET_CREATE;

        String response = WeChatClientUtil.post(url, json.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    @Override
    public String getTicket(String ticket, HttpServletResponse response) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException {
        String url = Const.PublicPlatform.URL_TICKET_GET;

        return WeChatClientUtil.forward(url.replace("TICKET", ticket), CharEncoding.UTF_8, response);
    }
}
