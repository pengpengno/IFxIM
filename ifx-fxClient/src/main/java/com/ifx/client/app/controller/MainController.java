package com.ifx.client.app.controller;


import cn.hutool.core.util.ObjectUtil;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.client.app.event.ChatEvent;
import com.ifx.client.util.FxmlLoader;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.proto.Chat;
import com.jfoenix.controls.JFXButton;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Slf4j
public class MainController implements Initializable {

    @FXML
    private ScrollBar mainMsgFxml;

    @FXML
    private TextArea msgTextArea;

    @FXML
    private FlowPane searchPane;

//    @FXML
    private JFXButton createSession;



    private Label receiveMessage;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> listView;

    private AccountInfo chatAcc; // 正在发起会话的用户

    private volatile Scene scene;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("{} is loading ...",getClass().getName());
        initSearch();
        scene = msgTextArea.getScene();
        searchPane.setVgap(8);
        searchPane.setHgap(4);
        msgTextArea.addEventHandler(KeyEvent.KEY_PRESSED ,(keyPress)-> {
            KeyCode code = keyPress.getCode();
            if (ObjectUtil.equal(code ,KeyCode.ENTER)){
                msgTextArea.clear();
                msgTextArea.setPrefColumnCount(0);
                msgTextArea.setPrefRowCount(0);
                msgTextArea.setScrollTop(0);
                msgTextArea.setScrollLeft(0);
                log.debug("正在发送消息，");
            }
            String name = code.getName();

        });

        receiveMessage.addEventHandler(ChatEvent.RECEIVE_CHAT , (chat)-> {
            log.info("receive message");
            Chat.ChatMessage chatMessage = chat.getChatMessage();
            receiveMessage.setText(chatMessage.getContent());
        });
    }



    @FXML
    void sendMsg(MouseEvent event) {
        log.info("send button");
        boolean supported = Platform.isSupported(ConditionalFeature.INPUT_METHOD);
//        1. 发送信息
        String text = msgTextArea.getText();
        boolean empty = text.isEmpty();

    }
    @FXML
    void createSession(MouseEvent event) {
        log.info("create session ");
        boolean supported = Platform.isSupported(ConditionalFeature.INPUT_METHOD);
//        1. 发送信息
        String text = msgTextArea.getText();
        boolean empty = text.isEmpty();

    }


    protected void initSearch(){
        searchField.textProperty().addListener((obs-> {
            searchPane.getChildren().clear();
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
