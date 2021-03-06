package com.fujitsu.database.service.impl;

import com.fujitsu.base.service.BaseService;
import com.fujitsu.database.dao.impl.MessageHistoryDAO;
import com.fujitsu.database.entity.MessageHistoryExample;
import com.fujitsu.database.entity.MessageHistoryWithBLOBs;
import com.fujitsu.database.service.iface.IMessageHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
@Service
public class MessageHistoryService extends BaseService implements IMessageHistoryService {
    @Autowired
    private MessageHistoryDAO messageHistoryDAO;

    @Override
    public List<MessageHistoryWithBLOBs> getMessageHistoryList(MessageHistoryWithBLOBs messageHistory) {
        MessageHistoryExample condition = new MessageHistoryExample();
        condition.createCriteria().andFromUserEqualTo(messageHistory.getFromUser());
        condition.setOrderByClause("create_time ASC");
        if (messageHistory.getRows() > 0 && messageHistory.getPage() > 0) {
            condition.setLimitStart((messageHistory.getPage() - 1) * messageHistory.getRows());
            condition.setLimitEnd(messageHistory.getRows());
        }
        return messageHistoryDAO.getMessageHistoryList(condition);
    }

    @Override
    public int getMessageHistoryListCount(MessageHistoryWithBLOBs messageHistory) {
        MessageHistoryExample condition = new MessageHistoryExample();
        condition.createCriteria().andFromUserEqualTo(messageHistory.getFromUser());
        return messageHistoryDAO.getMessageHistoryListCount(condition);
    }

    @Override
    public List<MessageHistoryWithBLOBs> getMessageHistoryDetail(MessageHistoryWithBLOBs messageHistory) {
        MessageHistoryExample condition = new MessageHistoryExample();
        condition.createCriteria().andUuidEqualTo(messageHistory.getUuid());
        return messageHistoryDAO.getMessageHistoryList(condition);
    }

    @Override
    public String addMessageHistory(MessageHistoryWithBLOBs messageHistory) {
        messageHistoryDAO.addMessageHistory(messageHistory);
        return messageHistory.getUuid();
    }

    @Override
    public int delMessageHistory(String uuid) {
        MessageHistoryExample condition = new MessageHistoryExample();
        condition.createCriteria().andUuidEqualTo(uuid);
        return messageHistoryDAO.delMessageHistory(condition);
    }

    @Override
    public int modifyMessageHistory(MessageHistoryWithBLOBs messageHistory) {
        MessageHistoryExample condition = new MessageHistoryExample();
        condition.createCriteria().andUuidEqualTo(messageHistory.getUuid());
        return messageHistoryDAO.modifyMessageHistory(messageHistory, condition);
    }
}
