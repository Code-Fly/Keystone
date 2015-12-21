package com.fujitsu.keystone.merchant.service.impl;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.base.helper.WeChatClientUtil;
import com.fujitsu.base.helper.XmlUtil;
import com.fujitsu.base.service.BaseService;
import com.fujitsu.keystone.merchant.service.iface.IPayService;
import com.fujitsu.keystone.publics.service.iface.ICoreService;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Barrie on 15/11/21.
 */
@Service
public class PayService extends BaseService implements IPayService {
    @Resource
    ICoreService coreService;

    public Map<String, String> payRefund(Map<String, Object> data) throws ConnectionFailedException, WeChatException, AccessTokenException {
        String url = Const.MerchantPlatform.URL_MERCHANT_PAY_REFUND;

        String response = WeChatClientUtil.post(url, XmlUtil.toXML(data), CharEncoding.UTF_8);

        return XmlUtil.parseXml(response);
    }

}
