package com.ifx.client.app.pane.viewMain.message;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.account.enums.ContentType;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.chat.PullChatMsgVo;
import com.ifx.account.vo.page.PageVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.api.ChatApi;
import com.ifx.client.api.SessionApi;
import com.ifx.client.app.event.SessionEvent;
import com.ifx.client.app.event.handler.ReceiveChatMessageEventHandler;
import com.ifx.client.app.event.handler.SwitchSessionPaneHandler;
import com.ifx.client.util.FontUtil;
import com.ifx.client.util.FxApplicationThreadUtil;
import com.ifx.common.context.AccountContext;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextArea;
import com.sun.javafx.event.EventUtil;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import java.util.stream.Collectors;


@Slf4j
public class ChatMainPane extends FlowPane implements SwitchSessionPaneHandler, ReceiveChatMessageEventHandler , Initializable {

    private MessagePane currentMessagePane ;

    private JFXTextArea messageArea;

    private JFXButton sendButton;

    private JFXScrollPane jfxScrollPane;

    @Autowired
    private SessionApi sessionApi;

    @Autowired
    private DefaultMessagePane defaultMessagePane;

    @Autowired
    private ChatApi chatApi;

    private final Map<Long,MessagePane> messagePanes = new HashMap<>(); // key sessionId

    @Override
    public void switchSessionEvent(SessionEvent sessionEvent) {
        FxApplicationThreadUtil.invoke(()-> EventUtil.fireEvent(sessionEvent,this));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPane();
        initEvent();
    }

    private void initEvent(){
        switchPaneHandler();
        sendMessageEvent();
    }



    /***
     * sessionInfoPane switch EventHandler
     */
    private  void switchPaneHandler (){
       this.addEventHandler(SessionEvent.SESSION_SWITCH, event-> {
           log.info("session switch event");
           event.getSessionInfoVo()
                   .flatMap(e -> Mono.justOrEmpty(Optional.ofNullable(messagePanes.get(e.getSessionId())))
                           .switchIfEmpty(
                                   sessionApi.sessionInfoBySessionId(e.getSessionId())
                                           .map(k-> {
                                               MessagePane messagePane = new MessagePane();
                                               messagePane.wiredSessionInfo(k);
                                               return messagePane;
                                           })))
                               .doOnNext(messagePane -> {
                                   FxApplicationThreadUtil.invoke(()-> {
                                       log.info("set e {}  as currentMessagePane ", JSON.toJSONString(messagePane.sessionInfoVo()));
                                       addMessagePane(messagePane);
                                       switchPane(messagePane);
                                   });
                               })
                               .doOnNext(messagePane -> {
                                   PullChatMsgVo pullChatMsgVo = new PullChatMsgVo();
                                   pullChatMsgVo.setPageVo(PageVo.defaultPageVo());
                                   pullChatMsgVo.setSessionId(messagePane.sessionInfoVo().getSessionId());
                                   chatApi.pullMsg(pullChatMsgVo)
                                           .doOnNext(e-> messagePane.clear())
                                           .collect(Collectors.toList())
                                           .doOnNext(messagePane::chatBubble)
                                           .subscribe();
                               })
                               .subscribe();
       });

    }

    /***
     * switch messagePane  facus on  specify pane
     * @param e message pane
     */
    public void switchPane(MessagePane e){
        e.prefWidthProperty().bind(this.widthProperty());
        int componentIndex = this.getChildren().indexOf(currentMessagePane);
        this.getChildren().remove(componentIndex);
        currentMessagePane = e;
        this.getChildren().add(componentIndex, currentMessagePane);
    }




    void sendMessageEvent() {
        log.info("send button");
        sendButton.setOnMouseClicked(event -> {
            String content = messageArea.getText();
            if (StrUtil.isNotBlank(content)){
                Mono.justOrEmpty(Optional.ofNullable(currentSessionInfo()))
                        .map(e-> {
                            ChatMsgVo chatMsgVo = new ChatMsgVo();
                            chatMsgVo.setMsgSendTime(DateUtil.now());
                            chatMsgVo.setContent(ContentType.TEXT.name());
                            chatMsgVo.setContent(content);
                            chatMsgVo.setFromAccount(AccountContext.getCurAccount());
                            chatMsgVo.setSessionId(e.getSessionId());
                            return chatMsgVo;
                        })
                        .doOnNext(e->chatApi.sendMsg(e).subscribe())
                        .doOnNext(e-> messageArea.clear())
                        .subscribe();
            }
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


    public SessionInfoVo currentSessionInfo(){
        return currentMessagePane.sessionInfoVo();
    }


    private ChatMainPane(){


    }




    private void initPane() {

        defaultMessagePane.prefWidthProperty().bind(this.widthProperty());
        defaultMessagePane.prefHeightProperty().bind(this.heightProperty());

        currentMessagePane = defaultMessagePane;

        currentMessagePane.prefWidthProperty().bind(this.widthProperty());

        messageArea = new JFXTextArea();

        sendButton = new JFXButton("send");


        sendButton.setButtonType(JFXButton.ButtonType.RAISED);
        sendButton.setFont(FontUtil.ArialFont);
        sendButton.setAlignment(Pos.BOTTOM_RIGHT);

        messageArea.prefWidthProperty().bind(this.widthProperty());

        messageArea.setLayoutX(400);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(161,100,100),null,null)));

        this.getChildren().addAll(currentMessagePane,sendButton,messageArea);




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
