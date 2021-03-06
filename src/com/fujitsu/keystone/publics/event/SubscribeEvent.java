/**
 *
 */
package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.constants.Const;
import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import com.fujitsu.database.entity.WeChatUserInfo;
import com.fujitsu.database.service.impl.WeChatUserInfoService;
import com.fujitsu.keystone.publics.entity.push.response.TextMessage;
import com.fujitsu.keystone.publics.service.impl.MessageService;
import com.fujitsu.keystone.publics.service.impl.UserService;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.txt.Icu4jEncodingDetector;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author Barrie
 */
public class SubscribeEvent extends Event {
    public static final String EVENT_KEY = "EventKey";

    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws AccessTokenException, WeChatException, ConnectionFailedException, JMSException, IOException, FileUploadException {
        super.execute(request, requestJson);

        String respXml = null;
        // 发送方帐号
        String fromUserName = requestJson.getString(FROM_USER_NAME);
        // 开发者微信号
        String toUserName = requestJson.getString(TO_USER_NAME);

        // 事件KEY值，与创建菜单时的key值对应
        String eventKey = requestJson.getString(EVENT_KEY);

        if (null != eventKey && !"".equals(eventKey.trim()) && eventKey.split("qrscene_").length == 2) {

            String scene = eventKey.split("qrscene_")[1];

            logger.info(scene);

            UserService userService = new UserService();

            JSONObject user = userService.getWeChatUserInfo(fromUserName);
            WeChatUserInfo target = new Gson().fromJson(user.toString(), WeChatUserInfo.class);

            JSONObject groupList = userService.getWeChatUserGroupList();
            for (int i = 0; i < groupList.getJSONArray("groups").size(); i++) {
                if (groupList.getJSONArray("groups").getJSONObject(i).getString("id").equals(scene)) {
                    userService.updateWeChatUserGroup(target.getOpenId(), scene);
                    userService.updateWeChatUserRemark(target.getOpenId(), "组：" + groupList.getJSONArray("groups").getJSONObject(i).getString("name"));
                }
            }

            WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            WeChatUserInfoService weChatUserInfoService = ((WeChatUserInfoService) wac.getBean("weChatUserInfoService"));
            if (weChatUserInfoService.getWeChatUserInfoDetail(target).size() == 0) {
                weChatUserInfoService.addWeChatUserInfo(target);
            }
        }

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

    public String detect(InputStream inputStream) throws FileUploadException, IOException {
        //HtmlEncodingDetector  UniversalEncodingDetector  Icu4jEncodingDetector
        EncodingDetector encodingDetector = new Icu4jEncodingDetector();
        Charset encode = encodingDetector.detect(new BufferedInputStream(inputStream), new Metadata());
        logger.info("文件编码 : " + encode.name());
        return encode.name();
    }
}
