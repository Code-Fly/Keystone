/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

/**
 * @author Barrie
 */
public interface IMenuService {
    JSONObject createDefault(String accessToken, JSONObject json) throws ConnectionFailedException, WeChatException;

    JSONObject createCondition(String accessToken, JSONObject json) throws ConnectionFailedException, WeChatException;

    JSONObject get(String accessToken) throws ConnectionFailedException, WeChatException;

    JSONObject deleteDefault(String accessToken) throws ConnectionFailedException, WeChatException;

    JSONObject deleteCondition(String accessToken, String id) throws ConnectionFailedException, WeChatException;

    JSONObject test(String accessToken, String userId) throws ConnectionFailedException, WeChatException;
}
