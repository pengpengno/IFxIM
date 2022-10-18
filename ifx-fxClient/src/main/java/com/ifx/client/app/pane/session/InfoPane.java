package com.ifx.client.app.pane.session;

import com.ifx.common.base.AccountInfo;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoPane extends Pane implements Initializable {

    private AccountInfo accountInfo;

    private String message;


    private String messageCreateDateTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }





    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageCreateDateTime() {
        return messageCreateDateTime;
    }

    public void setMessageCreateDateTime(String messageCreateDateTime) {
        this.messageCreateDateTime = messageCreateDateTime;
    }
}
