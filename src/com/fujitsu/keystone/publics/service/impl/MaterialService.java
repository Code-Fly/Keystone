/**
 *
 */
package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import com.fujitsu.keystone.publics.service.iface.IMaterialService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author Barrie
 */
@Service
public class MaterialService extends BaseService implements IMaterialService {
    public static String MATERIAL_TYPE_IMAGE = "image";
    public static String MATERIAL_TYPE_VIDEO = "video";
    public static String MATERIAL_TYPE_VOICE = "voice";
    public static String MATERIAL_TYPE_NEWS = "news";
    @Resource
    ICoreService coreService;

    /**
     * @throws ConnectionFailedException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public JSONObject getMaterialList(String type, int offset, int count) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException {

        String url = Const.PublicPlatform.URL_MATERIAL_GET_LIST;

        JSONObject request = new JSONObject();
        request.put("type", type);
        request.put("offset", offset);
        request.put("count", count);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }

    /**
     * @param mediaId
     * @return
     * @throws ConnectionFailedException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public JSONObject getMaterial(String mediaId) throws ConnectionFailedException, WeChatException, AccessTokenException, UnsupportedEncodingException {

        String url = Const.PublicPlatform.URL_MATERIAL_GET_DETAIL;

        JSONObject request = new JSONObject();
        request.put("media_id", mediaId);

        String response = WeChatClientUtil.post(url, request.toString(), CharEncoding.UTF_8);

        return JSONObject.fromObject(response);
    }
}
