package com.fujitsu.keystone.publics.event;

import com.fujitsu.base.exception.AccessTokenException;
import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileUploadException;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Barrie on 15/12/7.
 */
public class DefaultEvent extends Event {
    @Override
    public String execute(HttpServletRequest request, JSONObject requestJson) throws JMSException, ConnectionFailedException, AccessTokenException, WeChatException, IOException, FileUploadException {
        super.execute(request, requestJson);

        String respXml = null;

        return respXml;
    }
}
