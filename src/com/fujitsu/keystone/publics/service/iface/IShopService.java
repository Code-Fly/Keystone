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
public interface IShopService {

    JSONObject getShop(String poi_id) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getShopList(String begin, String limit) throws ConnectionFailedException, WeChatException, AccessTokenException;
}
