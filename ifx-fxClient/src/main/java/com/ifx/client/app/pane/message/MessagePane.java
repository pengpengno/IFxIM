package com.ifx.client.app.pane.message;

import cn.hutool.core.util.StrUtil;
import com.ifx.account.mapstruct.AccProtoBufMapper;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.app.pane.session.SessionListPane;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.Chat;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
@Slf4j
public class MessagePane extends FlowPane implements Initializable {

    private static final Integer MAX_LINE_CHAR = 20 ;

    private Map<Long, ChatBubblePane>  chatBubblePaneMap;

    private SessionInfoVo sessionInfoVo ;

    private Label sessionNameLabel  ;

    public Mono<SessionInfoVo> sessionInfoVoMono(){
        return Mono.justOrEmpty(sessionInfoVo);
    }

    public SessionInfoVo sessionInfoVo(){
        return sessionInfoVo;
    }


    private MessagePane(){

        chatBubblePaneMap = new HashMap<>();

        this.setBackground(new Background(new BackgroundFill(Color.rgb(16,160,160),null,null)));

        this.setWidth(30);
        this.setHeight(70);
    }


    public MessagePane(SessionInfoVo vo){
        chatBubblePaneMap = new HashMap<>();
        initPane(vo);
    }


    private void initPane(SessionInfoVo vo){
        sessionInfoVo = vo;

        if (sessionInfoVo == null){
            sessionNameLabel = new Label("empty");
        }else {
            sessionNameLabel = new Label(sessionInfoVo.getSessionName());
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init message ");
    }
}
