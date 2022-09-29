package com.ifx.client.app.pane;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Slf4j
@Component
public class MessagePane extends Pane implements Initializable {

    private String message;

    private String fromAccount;

    private String maxRowString;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init message ");
    }
}
