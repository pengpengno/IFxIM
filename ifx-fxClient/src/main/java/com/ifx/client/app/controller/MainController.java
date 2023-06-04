package com.ifx.client.app.controller;


import cn.hutool.core.date.DateUtil;
import com.ifx.account.enums.ContentType;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.client.api.ChatApi;
import com.ifx.client.app.event.ChatEvent;
import com.ifx.client.app.event.handler.ReceiveChatMessageEventHandler;
import com.ifx.client.app.pane.message.MessagePane;
import com.ifx.client.util.FxmlLoader;
import com.ifx.common.context.AccountContext;
import com.ifx.connect.connection.client.ReactiveClientAction;
import com.ifx.connect.proto.Chat;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.event.EventUtil;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component()
@Slf4j
public class MainController implements Initializable , ReceiveChatMessageEventHandler {


    private JFXButton createSession;

    @FXML
    private Label receiveMessage;

    @FXML
    private TextField searchField;


    @FXML
    private ScrollPane msgScrollPane;

    @FXML
    private Pane chatPane;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea messageArea;

    private VBox vBox;

    @Autowired
    ReactiveClientAction reactiveClientAction;


    @Autowired
    ChatApi chatApi;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug(" {} is loading ...", getClass().getName());
        initSearch();
        vBox = new VBox(8);
        log.debug("The receive handler had built");
        msgScrollPane.setContent(vBox);
        initChatHandler();
    }

    private void initChatHandler (){
        receiveMessage.addEventHandler(ChatEvent.RECEIVE_CHAT , (chat)-> {
            log.info("client receive message");
            Chat.ChatMessage chatMessage = chat.getChatMessage();
            MessagePane msgPane = new MessagePane(chatMessage);
            vBox.getChildren().add(msgPane);
            receiveMessage.setText(chatMessage.getContent());
        });
    }


    /**
     * @Inherit
     * @param chatEvent
     */
    public void  receiveChat (ChatEvent chatEvent){
        log.info(" fire event ");
        Runnable fireEvent = () -> EventUtil.fireEvent( receiveMessage,chatEvent );
        if (!Platform.isFxApplicationThread()){
            Platform.runLater(fireEvent);
        }
        else {
            fireEvent.run();
        }
    }


    @FXML
    void sendMsg(MouseEvent event) {
        log.info("send button");
        boolean supported = Platform.isSupported(ConditionalFeature.INPUT_METHOD);

        boolean fxApplicationThread = Platform.isFxApplicationThread();

        String content = messageArea.getText();

//        TODO Pane package messageVo
//        ChatMsgVo chatMsgVo = new ChatMsgVo();
//        chatMsgVo.setMsgSendTime(DateUtil.now());
//        chatMsgVo.setContent(ContentType.TEXT.name());
//        chatMsgVo.setContent(content);
//        chatMsgVo.setFromAccount(AccountContext.getCurAccount());
//        chatMsgVo.setSessionId(sessionId);
        log.debug("try to send message");
//        chatApi.sendMsg(chatMsgVo).subscribe();
        log.debug("send message success");

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
