package com.ifx.client.app.pane;

import com.ifx.connect.proto.Account;
import com.ifx.connect.proto.Chat;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;
@Slf4j
//@Component
public class MessagePane extends GridPane implements Initializable {


    private Chat.ChatMessage chatMessage;

    private Label fromAccountLabel ;

    private Label messageLabel;

    private Account.AccountInfo fromAccountInfo;



    private MessagePane(){

    }

    private JFXTextField textField;

    public MessagePane(Chat.ChatMessage message){
        chatMessage  = message ;
        fromAccountLabel = new Label();
        messageLabel = new Label();
        fromAccountInfo = message.getFromAccountInfo();

        fromAccountLabel.setText(fromAccountInfo.getAccount());

        fromAccountLabel.setLayoutY(100);
        fromAccountLabel.setLayoutX(20);
        messageLabel.setLayoutY(300);
        messageLabel.setLayoutX(20);

        messageLabel.setText(chatMessage.getContent());
        this.setCache(false);
        this.setMaxSize(300,300);
        
        this.getChildren().add(fromAccountLabel);
        this.getChildren().add(messageLabel);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init message ");
    }
}
