/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Barrie
 */
public interface IOrderService {
    JSONObject getOrderList(String status, String beginTime, String endTime) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getOrderList(HttpServletRequest request, String status, String beginTime, String endTime) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getOrder(String orderId) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getOrder(HttpServletRequest request, String orderId) throws ConnectionFailedException, WeChatException, AccessTokenException;

    int getOrderCount(JSONObject oList, String productId);
}
