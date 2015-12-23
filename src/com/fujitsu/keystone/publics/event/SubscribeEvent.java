/**
 *
 */
package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import com.fujitsu.keystone.publics.service.impl.UserService;
import net.sf.json.JSONObject;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Barrie
 */
public class SubscribeEvent extends Event {

    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws AccessTokenException, WeChatException, ConnectionFailedException, JMSException {
        super.execute(request, requestJson);

        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(TO_USER_NAME);

        UserService userService = new UserService();

        JSONObject user = userService.getWeChatUserInfo(fromUserName);

        TextMessage message = new TextMessage();

        message.setToUserName(fromUserName);
        message.setFromUserName(toUserName);
        message.setCreateTime(new Date().getTime());
        message.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);


        StringBuffer buffer = new StringBuffer();
        buffer.append("您好，欢迎关注");
        buffer.append(Const.WECHART_NAME);
        buffer.append(Const.LINE_SEPARATOR);
        buffer.append("我们来聊天吧~").append(Const.LINE_SEPARATOR);
        message.setContent(buffer.toString());

        // 将消息对象转换成xml
        respXml = MessageService.messageToXml(message);

        return respXml;
    }

}
