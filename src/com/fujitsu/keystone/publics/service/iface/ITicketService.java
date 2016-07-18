package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by 123 on 2016/7/14.
 */
public interface ITicketService {
    JSONObject createTicket(JSONObject json) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    String getTicket(String ticket, HttpServletResponse response) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;
}
