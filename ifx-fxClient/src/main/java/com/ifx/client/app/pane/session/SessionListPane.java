package com.ifx.client.app.pane.session;

import com.ifx.account.vo.session.SessionInfoVo;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component
public class SessionListPane extends FlowPane {


    private Map<Long,SessionMessageMinPane> sessionMessageMinPaneMap;


    private SessionListPane(){
        sessionMessageMinPaneMap = new HashMap<>();
    }


    public void addSession(SessionInfoVo sessionInfoVo){
        if (sessionInfoVo == null){
            throw new IllegalArgumentException("Session is illegal ,Could not create SessionMinPane");
        }
        Long sessionId = sessionInfoVo.getSessionId();

        this.setHgap(FlowPane.USE_PREF_SIZE);

        this.setVgap(1);

        SessionMessageMinPane sessionMessageMinPane = new SessionMessageMinPane(sessionInfoVo);
        SessionMessageMinPane sessionMessageMinPane2 = new SessionMessageMinPane(sessionInfoVo);

        sessionMessageMinPaneMap.putIfAbsent(sessionId,sessionMessageMinPane);


        this.setOrientation(Orientation.VERTICAL);
        this.getChildren().add(sessionMessageMinPane);
        this.getChildren().add(sessionMessageMinPane2);


        this.setAlignment(Pos.TOP_LEFT);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(136,10,160),null,null)));


        this.setLayoutY(20);

        this.setPrefHeight(590);
        this.setWidth(190);
        this.setHeight(590);



    }

    private enum SingleInstance{
        INSTANCE;
        private final SessionListPane instance;
        SingleInstance(){
            instance = new SessionListPane();
        }
        private SessionListPane getInstance(){
            return instance;
        }
    }
    public static SessionListPane getInstance(){
        return SingleInstance.INSTANCE.getInstance();
    }


}
