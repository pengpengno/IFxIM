package com.ifx.client.app.controller;


import cn.hutool.core.date.DateUtil;
import com.ifx.account.enums.ContentType;
import com.ifx.account.vo.ChatMsgVo;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.client.api.ChatApi;
import com.ifx.client.app.event.ChatEvent;
import com.ifx.client.app.pane.MessagePane;
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
public class MainController implements Initializable {


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

    private Long sessionId ;

    @Autowired
    ChatApi chatApi;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info(" {} is loading ...", getClass().getName());
        initSearch();
//
//        searchPane.setVgap(8);
//        searchPane.setHgap(4);
//        msgTextArea.addEventHandler(KeyEvent.KEY_PRESSED ,(keyPress)-> {
//            KeyCode code = keyPress.getCode();
//            if (ObjectUtil.equal(code ,KeyCode.ENTER)){
//                msgTextArea.clear();
//                msgTextArea.setPrefColumnCount(0);
//                msgTextArea.setPrefRowCount(0);
//                msgTextArea.setScrollTop(0);
//                msgTextArea.setScrollLeft(0);
////                log.debug("正在发送消息，");
//            }
//
//        });
        vBox = new VBox(8);
        log.debug("The receive handler had built");
        msgScrollPane.setContent(vBox);

        receiveMessage.addEventHandler(ChatEvent.RECEIVE_CHAT , (chat)-> {
            log.info("client receive message");
            Chat.ChatMessage chatMessage = chat.getChatMessage();
            MessagePane msgPane = new MessagePane(chatMessage);
            vBox.getChildren().add(msgPane);
//            messagePane.getChildren().add(msgPane);
            receiveMessage.setText(chatMessage.getContent());
        });

    }

    public void  receiveEvent (ChatEvent chatEvent){
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


        ChatMsgVo chatMsgVo = new ChatMsgVo();
        chatMsgVo.setMsgSendTime(DateUtil.now());
        chatMsgVo.setContent(ContentType.TEXT.name());
        chatMsgVo.setContent(content);
        chatMsgVo.setFromAccount(AccountContext.getCurAccount());
        chatMsgVo.setSessionId(sessionId);
        chatApi.sendMsg(chatMsgVo).subscribe();
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
