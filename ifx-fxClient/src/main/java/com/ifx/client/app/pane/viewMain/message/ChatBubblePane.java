package com.ifx.client.app.pane.viewMain.message;

import com.ifx.account.vo.ChatMsgVo;
import com.ifx.client.util.FontUtil;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.ValidatorUtil;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 气泡控件
 *
 */
@Slf4j
public class ChatBubblePane extends FlowPane implements Initializable {


    private Label accountNameLabel;

    private TextField messageField;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





    public ChatBubblePane(){

        this.setHgap(USE_PREF_SIZE);

        this.setVgap(1);

//        this.setPrefHeight(20);

        this.setOrientation(Orientation.VERTICAL);
    }


    public ChatBubblePane  wiredChatMsgVo(ChatMsgVo chatMsgVo){

        if (chatMsgVo == null){
            throw new IllegalArgumentException("Chat message is illegal!");
        }

        ValidatorUtil.validateThrows(chatMsgVo, ChatMsgVo.ChatPush.class);

        AccountInfo fromAccount = chatMsgVo.getFromAccount();

        accountNameLabel = FontUtil.defaultLabel(10, fromAccount.account());

        messageField  = new TextField(chatMsgVo.getContent());

        messageField.setFont(FontUtil.defaultFont(15));

        messageField.setEditable(false);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(136,150,160),null,null)));

        this.getChildren().addAll(accountNameLabel,messageField);

        return this;
    }



}
