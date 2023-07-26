package com.ifx.client.app.pane.viewMain.session;

import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.util.FontUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionMessageMinPane extends Pane {

    private Label sessionNameLabel;

    private Label lastMessage;

    private Label unReadMessages;

    private SessionInfoVo sessionInfoVo;

    public static Long HEIGHT = 100l;
    public static Long WIDTH = 190l;



    public SessionInfoVo getSessionInfoVo(){
        return sessionInfoVo;
    }

    public SessionMessageMinPane (SessionInfoVo sessionInfoVo){

        if (sessionInfoVo == null){
            throw new IllegalArgumentException("Session is illegal!");
        }

        this.sessionInfoVo = sessionInfoVo;

        sessionNameLabel = FontUtil.defaultLabel(20,getSessionInfoVo().getSessionName());

        sessionNameLabel.setAlignment(Pos.CENTER);


        this.getChildren().add(sessionNameLabel);

//        this.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
//
//            log.info("click min pane");
//
//            SessionEvent sessionEvent = new SessionEvent(SessionEvent.SESSION_SWITCH, sessionInfoVo);
//
//            ChatMainPane.getInstance().switchSessionEvent(sessionEvent);
//
//        });

        this.setBackground(new Background(new BackgroundFill(Color.rgb(51,100,100),null,null)));



    }




}
