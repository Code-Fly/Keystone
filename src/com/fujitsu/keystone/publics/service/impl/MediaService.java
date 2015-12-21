package com.fujitsu.keystone.publics.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.HttpClientUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.publics.service.iface.IMediaService;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Barrie on 15/12/21.
 */
@Service
public class MediaService extends BaseService implements IMediaService {

    @Override
    public String get(String accessToken, String mediaId, HttpServletResponse httpServletResponse) throws ConnectionFailedException, WeChatException {
        String url = Const.PublicPlatform.URL_MEDIA_GET.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
        // 获取网页授权凭证
        String response = HttpClientUtil.getFile(url, CharEncoding.UTF_8, httpServletResponse);

        return response;
    }
}
