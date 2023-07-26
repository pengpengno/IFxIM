package com.ifx.client.app.pane.viewMain.message;

import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.util.FontUtil;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

@Slf4j
public class MessagePane extends FlowPane implements Initializable {

    private static final Integer MAX_LINE_CHAR = 20 ;

    private SessionInfoVo sessionInfoVo ;

    private TextArea messageArea;

    private JFXButton sendButton;


    private ChatBubbleShowPane chatBubbleShowPane;

    private ScrollPane scrollPane;

    private Label sessionNameLabel  ;

    private Pane sessionInfoPane ;


    public SessionInfoVo sessionInfoVo(){
        return sessionInfoVo;
    }



    public MessagePane(){

    }

    public MessagePane wiredSessionInfo(SessionInfoVo vo){

        initPane(vo);

        return this;
    }


    public void chatBubble(Collection<ChatMsgVo> chatMsgVos){
        chatBubbleShowPane.chatBubble(chatMsgVos);
    }


    public void initPane(){

    }

    private void initPane(SessionInfoVo vo){

//        this.setHgap(USE_PREF_SIZE);

        this.setVgap(1);

        this.setOrientation(Orientation.VERTICAL);

        sessionInfoVo = vo;

        chatBubbleShowPane = new ChatBubbleShowPane();

        chatBubbleShowPane.initialize(null,null);

//        chatBubbleShowPane.prefWidthProperty().bind(this.prefWidthProperty());
//
//        chatBubbleShowPane.prefHeight(400);
//        scrollPane = new ScrollPane(chatBubbleShowPane);
//        scrollPane.setFitToWidth(true); // Adjust the width to fit the content
//        scrollPane.setFitToHeight(true); // Adjust the height to fit the content
        sessionInfoPane = new Pane();

        if (sessionInfoVo == null){
            sessionNameLabel = FontUtil.defaultLabel(15,"empty");

            sessionNameLabel = new Label("empty");

        }else {

            sessionNameLabel = FontUtil.defaultLabel(15,sessionInfoVo.getSessionName());

        }

        sessionInfoPane.getChildren().add(sessionNameLabel);

        this.setPrefHeight(400);

        this.getChildren().addAll(sessionInfoPane,chatBubbleShowPane);

    }


    public void clear(){
        chatBubbleShowPane.clear();
    }

    public void addChatBubblePane(ChatMsgVo chatMsgVo){
        chatBubbleShowPane.addChatBubblePane(chatMsgVo);
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init message ");
    }
}
