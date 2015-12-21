/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.entity.customer.account.KfInfo;
import com.fujitsu.keystone.publics.entity.customer.message.CouponMessage;
import com.fujitsu.keystone.publics.entity.customer.message.TextMessage;
import net.sf.json.JSONObject;

/**
 * @author Barrie
 */
public interface ICustomerService {
    JSONObject getAccountList() throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getAccountListOnline() throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject accountAdd(KfInfo account) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject accountDelete(KfInfo account) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject accountUpdate(KfInfo account) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject sendTextMessage(TextMessage message) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject sendCouponMessage(CouponMessage message) throws ConnectionFailedException, WeChatException, AccessTokenException;
}
