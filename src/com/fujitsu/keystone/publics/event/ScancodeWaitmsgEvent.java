package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class ScancodeWaitmsgEvent extends Event {

    public static String SCAN_RESULT = "ScanResult";

    public static String SCAN_CODE_INFO = "ScanCodeInfo";

    public static String EVENT_KEY = "EventKey";

    /**
     * @throws AccessTokenException
     * @throws ConnectionFailedException
     * @throws UnsupportedEncodingException
     */
    public String execute(HttpServletRequest request, JSONObject requestJson) throws ConnectionFailedException,
            AccessTokenException, WeChatException, JMSException {
        super.execute(request, requestJson);

        String respXml = null;

        String fromUserName = requestJson.getString(FROM_USER_NAME);
        String toUserName = requestJson.getString(TO_USER_NAME);
        String eventKey = requestJson.getString(EVENT_KEY);
        JSONObject scanCodeInfo = JSONObject.fromObject(requestJson.get(SCAN_CODE_INFO));
        String scanResult = scanCodeInfo.getString(SCAN_RESULT);

        TextMessage message = new TextMessage();
        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);

        message.setContent("{eventKey:\"" + eventKey + "\" , scanResult: \"" + scanResult + "\"}");
        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);

        return respXml;
    }
}
