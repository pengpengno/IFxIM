package com.ifx.client.app.pane.message;

import com.ifx.account.vo.ChatMsgVo;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.ValidatorUtil;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 气泡控件
 *
 */
@Slf4j
public class ChatBubblePane extends FlowPane implements Initializable {

    private String message;

    private String session;

    private String account;

    private Label accountNameLabel;

    private Label messageLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public ChatBubblePane(ChatMsgVo chatMsgVo){
        if (chatMsgVo == null){
            throw new IllegalArgumentException("Chat message is illegal!");
        }

        ValidatorUtil.validateThrows(chatMsgVo, ChatMsgVo.ChatPush.class);
        AccountInfo fromAccount = chatMsgVo.getFromAccount();

        accountNameLabel = new Label(fromAccount.account());

        messageLabel = new Label(chatMsgVo.getContent());


        this.getChildren().add(accountNameLabel);

        this.getChildren().add(messageLabel);
    }



}
