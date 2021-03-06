package com.fujitsu.database.service.iface;

import com.fujitsu.database.entity.MessageHistoryWithBLOBs;

import java.util.List;

/**
 * Created by 123 on 2016/7/14.
 */
public interface IMessageHistoryService {
    List<MessageHistoryWithBLOBs> getMessageHistoryList(MessageHistoryWithBLOBs messageHistory);

    int getMessageHistoryListCount(MessageHistoryWithBLOBs messageHistory);

    List<MessageHistoryWithBLOBs> getMessageHistoryDetail(MessageHistoryWithBLOBs messageHistory);

    String addMessageHistory(MessageHistoryWithBLOBs messageHistory);

    int delMessageHistory(String uuid);

    int modifyMessageHistory(MessageHistoryWithBLOBs messageHistory);
}
