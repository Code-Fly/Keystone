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
public interface IMaterialService {
    JSONObject getMaterialList(String type, int offset, int count) throws ConnectionFailedException, WeChatException, AccessTokenException;

    JSONObject getMaterial(String mediaId) throws ConnectionFailedException, WeChatException, AccessTokenException;
}
