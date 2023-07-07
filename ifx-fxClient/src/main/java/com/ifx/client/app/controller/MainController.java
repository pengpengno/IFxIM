package com.ifx.client.app.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.account.enums.ContentType;
import com.ifx.account.mapstruct.AccProtoBufMapper;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.api.ChatApi;
import com.ifx.client.api.SessionApi;
import com.ifx.client.app.event.ChatEvent;
import com.ifx.client.app.event.SessionEvent;
import com.ifx.client.app.event.handler.ReceiveChatMessageEventHandler;
import com.ifx.client.app.event.handler.SwitchMainChatPaneHandler;
import com.ifx.client.app.pane.message.ChatMainPane;
import com.ifx.client.app.pane.session.SessionListPane;
import com.ifx.client.util.FxApplicationThreadUtil;
import com.ifx.client.util.FxmlLoader;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.context.AccountContext;
import com.ifx.connect.connection.client.ReactiveClientAction;
import com.ifx.connect.proto.Chat;
import com.sun.javafx.event.EventUtil;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@Slf4j
public class MainController implements Initializable , ReceiveChatMessageEventHandler {

    @FXML
    private TextField searchField;

    @FXML
    private Pane chatPane;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea messageArea;


    @FXML
    private Pane sessionInfoPane;

    @FXML
    private Button refreshSessionButton;

    private Label accountNameLabel;
    @Autowired
    ReactiveClientAction reactiveClientAction;

    @Autowired
    ChatApi chatApi;

    @Autowired
    SessionApi sessionApi;


    private final SessionListPane sessionListPane = SessionListPane.getInstance();
    private final ChatMainPane chatMainPane = ChatMainPane.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug(" {} is loading ...", getClass().getName());
        log.debug("The receive handler had built");
        chatMainPane.init();

        sessionInfoPane.setBackground(new Background(new BackgroundFill(Color.rgb(16,160,160),null,null)));

        sessionInfoPane.getChildren().add(sessionListPane);

        chatPane.getChildren().add(chatMainPane);

        initSearch();

        initChatHandler();
        initSessionPane();

    }

    public void initAccount(){
        accountNameLabel = new Label(AccountContext.getCurAccount().accountName());

    }

    private void initChatHandler (){
        chatPane.addEventHandler(ChatEvent.RECEIVE_CHAT , (chat)-> {
            log.info("client receive message");
            Chat.ChatMessage chatMessage = chat.getChatMessage();

            ChatMsgVo chatMsgVo = AccProtoBufMapper.INSTANCE.tran2ProtoChat(chatMessage);

            Mono.justOrEmpty(Optional.ofNullable(chatMainPane.currentSessionInfo()))
                .filter(e-> ObjectUtil.equal(chatMsgVo.getSessionId(),e.getSessionId()))
                .hasElement()
                .flatMap(isCurrentSessionMessage-> {
                    if (isCurrentSessionMessage) {
                        return chatMainPane.getMessagePane().doOnNext(e->e.addMessage(chatMsgVo));
                    }
                    return Mono.empty();
                })
                .subscribe();
        });
    }


    /**
     * @Inherit
     * @param chatEvent
     */
    public void  receiveChat (ChatEvent chatEvent){
        log.info(" fire chat event ");
        FxApplicationThreadUtil.invoke( () -> EventUtil.fireEvent( chatEvent ,chatPane,sessionInfoPane));
    }


    public void initSessionPane(){
        AccountInfo curAccount = AccountContext.getCurAccount();
        log.debug("accountInfo {} ", JSON.toJSONString(curAccount));
        if(curAccount == null){
            throw new IllegalArgumentException("When init Session occur error,pls login before operate!");
        }
        sessionApi.sessionInfo(curAccount.userId())
                .subscribe(e-> FxApplicationThreadUtil.invoke(()->sessionListPane.addSession(e)));
    }

    @FXML
    void refreshSession(MouseEvent event){
        initSessionPane();
    }

    @FXML
    void sendMsg(MouseEvent event) {
        log.info("send button");

        String content = messageArea.getText();

        Mono.justOrEmpty(Optional.ofNullable(chatMainPane.currentSessionInfo()))
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

        log.info("send message success");

    }



    @FXML
    void createSession(MouseEvent event) {
        log.info("create session ");
        boolean supported = Platform.isSupported(ConditionalFeature.INPUT_METHOD);

    }


    protected void initSearch(){
        searchField.textProperty().addListener((obs-> {
            String text = searchField.getText();
            AccountSearchVo build =
                AccountSearchVo.builder()
                .likeAccount(searchField.getText())
                .build();
        }));
    }

    @FXML
    void searchAcc(InputMethodEvent event) {
        String text = searchField.getText();
        log.info("当前文本为 {} ",text);
        AccountSearchVo build =
                AccountSearchVo.builder()
                .likeAccount(searchField.getText())
                .build();
    }

    public static void show(){
        Stage stage = FxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\main.fxml");
        log.info("prepare to show  register");
        stage.show();
        stage.setTitle("IFx");
    }


}
