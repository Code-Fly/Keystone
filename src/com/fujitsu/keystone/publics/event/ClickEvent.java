/**
 *
 */
package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.query.Query;
import com.fujitsu.keystone.publics.service.impl.MenuService;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import net.sf.json.JSONObject;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Barrie
 */
public class ClickEvent extends Event {
    public static final String EVENT_KEY = "EventKey";

    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, WeChatException, ConnectionFailedException, AccessTokenException {
        super.execute(request, requestJson);

        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(TO_USER_NAME);

        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageService.RESP_MESSAGE_TYPE_TEXT);
        // 事件KEY值，与创建菜单时的key值对应
        String eventKey = requestJson.getString(EVENT_KEY);
        // 根据key值判断用户点击的按钮
        // 充装存储
        if (eventKey.equals(MenuService.FW_ZXTS)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("咨询投诉").append(Const.LINE_SEPARATOR);
            buffer.append(Const.LINE_SEPARATOR);
            buffer.append("气瓶用户对任何环节有疑问均可通过微信方式咨询，并提出投诉和建议。").append(Const.LINE_SEPARATOR);
            buffer.append("输入格式:").append(Const.LINE_SEPARATOR);
            buffer.append(Query.SEPARATOR + Query.CUSTOMER_SERVICE).append(Const.LINE_SEPARATOR);
            textMessage.setContent(buffer.toString());
            respXml = MessageService.messageToXml(textMessage);
        }
        // 其他按钮
        else {
            textMessage.setContent("功能尚未开放，敬请期待！" + eventKey);
            respXml = MessageService.messageToXml(textMessage);
        }

        return respXml;
    }

}
