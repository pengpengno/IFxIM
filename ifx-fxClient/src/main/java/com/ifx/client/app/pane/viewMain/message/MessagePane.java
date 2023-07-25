package com.ifx.client.app.pane.viewMain.message;

import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.util.FontUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
@Slf4j
public class MessagePane extends FlowPane implements Initializable {

    private static final Integer MAX_LINE_CHAR = 20 ;

    private Map<Long, ChatBubblePane>  chatBubblePaneMap;

    private SessionInfoVo sessionInfoVo ;



    private JFXTextArea messageArea;

    private JFXButton sendButton;

    private Label sessionNameLabel  ;

    public Mono<SessionInfoVo> sessionInfoVoMono(){
        return Mono.justOrEmpty(sessionInfoVo);
    }

    public SessionInfoVo sessionInfoVo(){
        return sessionInfoVo;
    }






    public MessagePane(){

    }

    public MessagePane wiredSessionInfo(SessionInfoVo vo){

        chatBubblePaneMap = new HashMap<>();

        initPane(vo);
        return this;
    }


    private void initPane(SessionInfoVo vo){
        sessionInfoVo = vo;

        if (sessionInfoVo == null){

            sessionNameLabel = FontUtil.defaultLabel(15,"empty");

            sessionNameLabel = new Label("empty");


        }else {

            sessionNameLabel = FontUtil.defaultLabel(15,sessionInfoVo.getSessionName());

        }
        this.setWidth(300);
        this.setHeight(200);

        this.setPrefWidth(700);
        this.setPrefHeight(400);

        this.getChildren().add(sessionNameLabel);
        this.setAlignment(Pos.TOP_LEFT);

    }


    public void addMessage(ChatMsgVo chatMsgVo){

        ChatBubblePane chatBubblePane = new ChatBubblePane(chatMsgVo);

        chatBubblePaneMap.putIfAbsent(chatMsgVo.getMsgId(),chatBubblePane);

        this.getChildren().add(chatBubblePane);

    }

    public void pullHistoryMsg(){
        if (sessionInfoVo !=null){

            Long sessionId = sessionInfoVo.getSessionId();

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init message ");
    }
}
