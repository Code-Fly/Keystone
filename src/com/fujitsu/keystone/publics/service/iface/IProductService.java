/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

/**
 * @author Barrie
 */
public interface IProductService {
    JSONObject getProductList(int status) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getProduct(String productId) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getProductGroupList() throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getProductGroupDetail(String groupId) throws ConnectionFailedException, WeChatException, AccessTokenException;
}
