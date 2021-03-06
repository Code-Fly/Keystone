/**
 *
 */
package com.fujitsu.keystone.publics.service.iface;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * @author Barrie
 */
public interface IMenuService {
    JSONObject createDefault(JSONObject json) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    JSONObject createCondition(JSONObject json) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    JSONObject get() throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject deleteDefault() throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject deleteCondition(String id) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;

    JSONObject test(String userId) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException;
}
