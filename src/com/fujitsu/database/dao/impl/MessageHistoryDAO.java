package com.fujitsu.database.dao.impl;

import com.fujitsu.base.dao.BaseDAO;
import com.fujitsu.database.dao.iface.IMessageHistoryDAO;
import com.fujitsu.database.entity.MessageHistoryExample;
import com.fujitsu.database.entity.MessageHistoryWithBLOBs;
import com.fujitsu.database.mapper.MessageHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
@Repository
public class MessageHistoryDAO extends BaseDAO implements IMessageHistoryDAO {
    @Autowired
    private MessageHistoryMapper messageHistoryMapper;

    @Override
    public List<MessageHistoryWithBLOBs> getMessageHistoryList(MessageHistoryExample example) {
        return messageHistoryMapper.selectByExampleWithBLOBs(example);
    }

    @Override
    public int getMessageHistoryListCount(MessageHistoryExample example) {
        return messageHistoryMapper.countByExample(example);
    }

    @Override
    public int addMessageHistory(MessageHistoryWithBLOBs messageHistory) {
        return messageHistoryMapper.insertSelective(messageHistory);
    }

    @Override
    public int delMessageHistory(MessageHistoryExample example) {
        return messageHistoryMapper.deleteByExample(example);
    }

    @Override
    public int modifyMessageHistory(MessageHistoryWithBLOBs messageHistory, MessageHistoryExample example) {
        return messageHistoryMapper.updateByExampleWithBLOBs(messageHistory, example);
    }
}
