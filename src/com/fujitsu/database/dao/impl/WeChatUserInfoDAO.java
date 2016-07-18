package com.fujitsu.database.dao.impl;

import com.fujitsu.base.dao.BaseDAO;
import com.fujitsu.database.dao.iface.IWeChatUserInfoDAO;
import com.fujitsu.database.entity.WeChatUserInfo;
import com.fujitsu.database.entity.WeChatUserInfoExample;
import com.fujitsu.database.mapper.WeChatUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
@Repository
public class WeChatUserInfoDAO extends BaseDAO implements IWeChatUserInfoDAO {
    @Autowired
    private WeChatUserInfoMapper weChatUserInfoMapper;

    @Override
    public List<WeChatUserInfo> getWeChatUserList(WeChatUserInfoExample example) {
        return weChatUserInfoMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public int getWeChatUserListCount(WeChatUserInfoExample example) {
        return weChatUserInfoMapper.countByExample(example);
    }

    @Override
    public int addWeChatUser(WeChatUserInfo weChatUserInfo) {
        return weChatUserInfoMapper.insertSelective(weChatUserInfo);
    }

    @Override
    public int delWeChatUser(WeChatUserInfoExample example) {
        return weChatUserInfoMapper.deleteByExample(example);
    }

    @Override
    public int modifyWeChatUser(WeChatUserInfo weChatUserInfo, WeChatUserInfoExample example) {
        return weChatUserInfoMapper.updateByExampleWithBLOBs(weChatUserInfo, example);
    }
}
