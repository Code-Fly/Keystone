package com.fujitsu.base.helper;

import com.fujitsu.base.exception.ConnectionFailedException;
import com.fujitsu.base.exception.WeChatException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Barrie on 15/12/5.
 */
public class WeChatClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(WeChatClientUtil.class);

    private static int RETRY = 3;

    public static String get(String url, String charset) throws ConnectionFailedException, WeChatException {
        String resp = null;
        for (int i = 0; i < (RETRY - 1); i++) {
            resp = HttpClientUtil.get(url, charset);
            if (isValid(resp)) {
                break;
            }
        }
        return resp;

    }

    public static String get(String url, Map<String, String> params, String charset) throws ConnectionFailedException, WeChatException {
        String resp = null;
        for (int i = 0; i < (RETRY - 1); i++) {
            resp = HttpClientUtil.get(url, params, charset);
            if (isValid(resp)) {
                break;
            }
        }
        return resp;
    }

    public static String get(String url, String params, String charset) throws ConnectionFailedException, WeChatException {
        String resp = null;
        for (int i = 0; i < (RETRY - 1); i++) {
            resp = HttpClientUtil.get(url, params, charset);
            if (isValid(resp)) {
                break;
            }
        }
        return resp;

    }

    public static String post(String url, String charset) throws ConnectionFailedException, WeChatException {
        String resp = null;
        for (int i = 0; i < (RETRY - 1); i++) {
            resp = HttpClientUtil.post(url, charset);
            if (isValid(resp)) {
                break;
            }
        }
        return resp;
    }

    public static String post(String url, Map<String, String> params, String charset) throws ConnectionFailedException, WeChatException {
        String resp = null;
        for (int i = 0; i < (RETRY - 1); i++) {
            resp = HttpClientUtil.post(url, params, charset);
            if (isValid(resp)) {
                break;
            }
        }
        return resp;
    }

    public static String post(String url, Map<String, String> params, String charset, String ContentType) throws ConnectionFailedException, WeChatException {
        String resp = null;
        for (int i = 0; i < (RETRY - 1); i++) {
            resp = HttpClientUtil.post(url, params, charset, ContentType);
            if (isValid(resp)) {
                break;
            }
        }
        return resp;
    }

    public static String post(String url, String params, String charset) throws ConnectionFailedException, WeChatException {
        String resp = null;
        for (int i = 0; i < (RETRY - 1); i++) {
            resp = HttpClientUtil.post(url, params, charset);
            if (isValid(resp)) {
                break;
            }
        }
        return resp;

    }

    public static String post(String url, String params, String charset, String ContentType) throws ConnectionFailedException, WeChatException {
        String resp = null;
        for (int i = 0; i < (RETRY - 1); i++) {
            resp = HttpClientUtil.post(url, params, charset, ContentType);
            if (isValid(resp)) {
                break;
            }
        }
        return resp;

    }

    private static boolean isValid(String respStr) throws ConnectionFailedException, WeChatException {
        JSONObject jsonObject = JSONObject.fromObject(respStr);
        if (jsonObject.containsKey("errcode") && !jsonObject.getString("errcode").equals("0")) {
            if (jsonObject.getString("errcode").equals("40001") || jsonObject.getString("errcode").equals("42001")) {
                KeystoneUtil.refreshRemoteAccessToken();
                return false;
            } else {
                throw new WeChatException(respStr);
            }
        }
        return true;
    }
}
