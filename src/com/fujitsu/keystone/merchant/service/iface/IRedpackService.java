package com.fujitsu.keystone.merchant.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
public interface IRedpackService {
    Map<String, String> sendRedpack(Map<String, Object> data) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;
}
