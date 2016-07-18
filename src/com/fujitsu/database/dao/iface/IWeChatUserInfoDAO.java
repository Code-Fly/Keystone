package com.fujitsu.database.dao.iface;

import com.fujitsu.database.entity.WeChatUserInfo;
import com.fujitsu.database.entity.WeChatUserInfoExample;

import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
public interface IWeChatUserInfoDAO {
    List<WeChatUserInfo> getWeChatUserList(WeChatUserInfoExample example);

    int getWeChatUserListCount(WeChatUserInfoExample example);

    int addWeChatUser(WeChatUserInfo weChatUserInfo);

    int delWeChatUser(WeChatUserInfoExample example);

    int modifyWeChatUser(WeChatUserInfo weChatUserInfo, WeChatUserInfoExample example);
}
