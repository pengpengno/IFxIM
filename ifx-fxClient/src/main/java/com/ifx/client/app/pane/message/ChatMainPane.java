package com.ifx.client.app.pane.message;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.account.enums.ContentType;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.api.ChatApi;
import com.ifx.client.api.SessionApi;
import com.ifx.client.app.event.SessionEvent;
import com.ifx.client.app.event.handler.ReceiveChatMessageEventHandler;
import com.ifx.client.app.event.handler.SwitchMainChatPaneHandler;
import com.ifx.client.util.FxApplicationThreadUtil;
import com.ifx.common.context.AccountContext;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextArea;
import com.sun.javafx.event.EventUtil;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;


@Slf4j
public class ChatMainPane extends FlowPane implements SwitchMainChatPaneHandler, ReceiveChatMessageEventHandler , Initializable {

    private MessagePane currentMessagePane ;

    private JFXTextArea messageArea;


    private JFXButton sendButton;

    private JFXScrollPane jfxScrollPane;

    @Autowired
    private SessionApi sessionApi;

    @Autowired
    private ChatApi chatApi;


    private final Map<Long,MessagePane> messagePanes = new HashMap<>(); // key sessionId

    @Override
    public void switchSessionEvent(SessionEvent sessionEvent) {
        FxApplicationThreadUtil.invoke(()-> EventUtil.fireEvent(sessionEvent,this));
    }

    private void initEvent(){
        switchPaneHandler()
        .doOnNext(e->sendMessageEvent())
        .subscribe();
    }


    /***
     * sessionInfoPane switch EventHandler
     */
    private  Mono<Void> switchPaneHandler (){
       return Mono.just(this)
               .doOnNext(obj -> {
                   obj.addEventHandler(SessionEvent.SESSION_SWITCH, event-> {
                       event.getSessionInfoVo()
                               .flatMap(e-> Mono.justOrEmpty(Optional.ofNullable(messagePanes.get(e.getSessionId())))
                                       .switchIfEmpty(
                                               Mono.just(new MessagePane(Mono.from(sessionApi.sessionInfoBySessionId(e.getSessionId())).block()))
                                                       .doOnNext(k-> {
                                                           log.info("add MessagePane");
                                                           addMessagePane(k);
                                                       }))
                               ).doOnNext(e-> {
                                   log.info("set e {}  as currentMessagePane ", JSON.toJSONString(e.sessionInfoVo()));
                                   int componentIndex = this.getChildren().indexOf(currentMessagePane);
                                   this.getChildren().remove(componentIndex);
                                   currentMessagePane = e;
                                   this.getChildren().add(componentIndex,currentMessagePane);
                               })
                               .doOnNext(l-> this.requestFocus())
                       ;
                   });
               }).then();
    }


    void sendMessageEvent() {
        log.info("send button");
        sendButton.setOnMouseClicked(event -> {
            String content = messageArea.getText();
            Mono.justOrEmpty(Optional.ofNullable(currentSessionInfo()))
                    .map(e-> {
                        ChatMsgVo chatMsgVo = new ChatMsgVo();
                        chatMsgVo.setMsgSendTime(DateUtil.now());
                        chatMsgVo.setContent(ContentType.TEXT.name());
                        chatMsgVo.setContent(content);
                        chatMsgVo.setFromAccount(AccountContext.getCurAccount());
                        chatMsgVo.setSessionId(e.getSessionId());
                        log.info("send message {} ",JSON.toJSONString(chatMsgVo));
                        return chatMsgVo;
                    })
                    .doOnNext(e->chatApi.sendMsg(e).subscribe())
                    .subscribe();
        });

    }


    private void addMessagePane(MessagePane messagePane){
        if (messagePane == null) {
            return;
        }
        SessionInfoVo sessionInfoVo = messagePane.sessionInfoVo();

        if (sessionInfoVo == null){
            log.warn("SessionInfoVo is invalid!");
            return;
        }
        Long sessionId = sessionInfoVo.getSessionId();

        if (messagePanes.containsKey(sessionId)){
            log.info("MessagePane {}  has exists , extra operation is unnecessary!",sessionId);
            return;
        }
        messagePanes.putIfAbsent(sessionId,messagePane);
        log.info("MessagePane {} add into context",sessionId);

    }

    public Mono<MessagePane> getMessagePane(){
        return Mono.justOrEmpty(currentMessagePane);
    }

    public Mono<SessionInfoVo> getCurrentSessionInfo(){
        return currentMessagePane.sessionInfoVoMono();
    }

    public SessionInfoVo currentSessionInfo(){
        return currentMessagePane.sessionInfoVo();
    }


    private ChatMainPane(){


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPane();
        initEvent();
    }



    private void initPane() {



        currentMessagePane = new MessagePane(null);

        jfxScrollPane = new JFXScrollPane();

        jfxScrollPane.setContent(currentMessagePane);

        this.getChildren().add(jfxScrollPane);

        this.addMessagePane(currentMessagePane);

        this.setWidth(300);
        this.setHeight(300);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(161,100,100),null,null)));


        this.getChildren().add(currentMessagePane);

    }


    public Mono<Void> switchMessagePane(SessionInfoVo sessionInfoVo){

        if (sessionInfoVo == null){
            throw new IllegalArgumentException("SessionInfo is valid.");
        }

        return Mono.justOrEmpty(Optional.ofNullable(messagePanes.get(sessionInfoVo.getSessionId())))
                .doOnNext(messagePane -> currentMessagePane = messagePane)
                .then();
    }




    private enum SingleInstance{
        INSTANCE;
        private final ChatMainPane instance;
        SingleInstance(){
            instance = new ChatMainPane();
        }
        private ChatMainPane getInstance(){
            return instance;
        }
    }
    public static ChatMainPane getInstance(){
        return ChatMainPane.SingleInstance.INSTANCE.getInstance();
    }


}
