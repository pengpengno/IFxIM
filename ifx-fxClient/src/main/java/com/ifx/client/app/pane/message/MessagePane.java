package com.ifx.client.app.pane.message;

import cn.hutool.core.util.StrUtil;
import com.ifx.account.mapstruct.AccProtoBufMapper;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.Chat;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;
@Slf4j
//@Component
public class MessagePane extends Pane implements Initializable {

    private static final Integer MAX_LINE_CHAR = 20 ;

    private ChatMsgVo chatMsg;

    private Label fromAccountLabel ;

    private Label messageLabel;

    private AccountInfo fromAccountInfo;




    private MessagePane(){

    }

    public AccountInfo getAccount(){
        return fromAccountInfo;
    }

    public MessagePane(Chat.ChatMessage message){
        chatMsg  = AccProtoBufMapper.INSTANCE.tran2ProtoChat(message) ;
        fromAccountLabel = new Label();
        messageLabel = new Label();
        fromAccountInfo = ProtoBufMapper.INSTANCE.proto2Acc(message.getFromAccountInfo());

        fromAccountLabel.setText(fromAccountInfo.getAccount());

        fromAccountLabel.setLayoutY(100);
        fromAccountLabel.setLayoutX(20);
        messageLabel.setLayoutY(300);
        messageLabel.setLayoutX(20);

        messageLabel.setText(chatMsg.getContent());

        this.setMaxSize(300,300);
        
        this.getChildren().add(fromAccountLabel);
        this.getChildren().add(messageLabel);

    }

    public  Boolean isCurAccount(String account){
        return StrUtil.equals(account,getAccount().getAccount());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init message ");
    }
}
