package com.ifx.client.app.pane;

import com.ifx.connect.proto.Chat;
<<<<<<< HEAD
import com.jfoenix.controls.JFXTextField;
=======
>>>>>>> 152785b35fc71276fa5e0ebbb840d6c45f8a256e
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Slf4j
@Component
public class MessagePane extends Pane implements Initializable {

//    private String message;
//
//    private String fromAccount;
//
//    private String maxRowString;

    private Chat.ChatMessage message;

    private Label fromAccount ;


    private JFXTextField textField;

    MessagePane(Chat.ChatMessage message){
        this.message  = message ;
        fromAccount.setText(message.getContent());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init message ");
    }
}
