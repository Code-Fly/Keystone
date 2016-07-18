package com.fujitsu.database.service.iface;

import com.fujitsu.database.entity.WeChatUserInfo;

import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
public interface IWeChatUserInfoService {
    List<WeChatUserInfo> getWeChatUserInfoList(WeChatUserInfo weChatUserInfo);

    int getWeChatUserInfoListCount(WeChatUserInfo weChatUserInfo);

    List<WeChatUserInfo> getWeChatUserInfoDetail(WeChatUserInfo weChatUserInfo);

    String addWeChatUserInfo(WeChatUserInfo weChatUserInfo);

    int delWeChatUserInfo(String openId);

    int modifyWeChatUserInfo(WeChatUserInfo weChatUserInfo);
}
