package com.fujitsu.database.service.impl;

import com.fujitsu.base.service.BaseService;
import com.fujitsu.database.dao.iface.IWeChatUserInfoDAO;
import com.fujitsu.database.entity.WeChatUserInfo;
import com.fujitsu.database.entity.WeChatUserInfoExample;
import com.fujitsu.database.service.iface.IWeChatUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
@Service
public class WeChatUserInfoService extends BaseService implements IWeChatUserInfoService {
    @Autowired
    private IWeChatUserInfoDAO weChatUserInfoDAO;

    @Override
    public List<WeChatUserInfo> getWeChatUserInfoList(WeChatUserInfo weChatUserInfo) {
        WeChatUserInfoExample condition = new WeChatUserInfoExample();
        condition.setOrderByClause("subscribe_time ASC");
        if (weChatUserInfo.getRows() > 0 && weChatUserInfo.getPage() > 0) {
            condition.setLimitStart((weChatUserInfo.getPage() - 1) * weChatUserInfo.getRows());
            condition.setLimitEnd(weChatUserInfo.getRows());
        }
        return weChatUserInfoDAO.getWeChatUserList(condition);
    }

    @Override
    public int getWeChatUserInfoListCount(WeChatUserInfo weChatUserInfo) {
        WeChatUserInfoExample condition = new WeChatUserInfoExample();
        return weChatUserInfoDAO.getWeChatUserListCount(condition);
    }

    @Override
    public List<WeChatUserInfo> getWeChatUserInfoDetail(WeChatUserInfo weChatUserInfo) {
        WeChatUserInfoExample condition = new WeChatUserInfoExample();
        condition.createCriteria().andOpenIdEqualTo(weChatUserInfo.getOpenId());
        return weChatUserInfoDAO.getWeChatUserList(condition);
    }

    @Override
    public String addWeChatUserInfo(WeChatUserInfo weChatUserInfo) {
        weChatUserInfoDAO.addWeChatUser(weChatUserInfo);
        return weChatUserInfo.getOpenId();
    }

    @Override
    public int delWeChatUserInfo(String openId) {
        WeChatUserInfoExample condition = new WeChatUserInfoExample();
        condition.createCriteria().andOpenIdEqualTo(openId);
        return weChatUserInfoDAO.delWeChatUser(condition);
    }

    @Override
    public int modifyWeChatUserInfo(WeChatUserInfo weChatUserInfo) {
        WeChatUserInfoExample condition = new WeChatUserInfoExample();
        condition.createCriteria().andOpenIdEqualTo(weChatUserInfo.getOpenId());
        return weChatUserInfoDAO.modifyWeChatUser(weChatUserInfo, condition);
    }
}
