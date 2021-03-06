package com.fujitsu.database.dao.iface;

import com.fujitsu.database.entity.MessageHistoryExample;
import com.fujitsu.database.entity.MessageHistoryWithBLOBs;

import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
public interface IMessageHistoryDAO {
    List<MessageHistoryWithBLOBs> getMessageHistoryList(MessageHistoryExample example);

    int getMessageHistoryListCount(MessageHistoryExample example);

    int addMessageHistory(MessageHistoryWithBLOBs messageHistory);

    int delMessageHistory(MessageHistoryExample example);

    int modifyMessageHistory(MessageHistoryWithBLOBs messageHistory, MessageHistoryExample example);
}
