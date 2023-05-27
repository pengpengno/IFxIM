package com.ifx.client.app.pane.message;

import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Account;
import javafx.scene.layout.Pane;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;


public class ChatMainPane extends Pane {

    private SessionInfoVo curSession;

    private Map<String, AccountInfo> accountInfoAttribute; // key account

    private Queue<MessagePane> messagePanes;



    private ChatMainPane(){
        messagePanes = new ArrayDeque<>();
    }


    public void addMessagePane(MessagePane messagePane){


    }


}
