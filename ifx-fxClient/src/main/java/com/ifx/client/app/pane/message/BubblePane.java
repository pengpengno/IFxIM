package com.ifx.client.app.pane.message;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 气泡控件
 *
 */
@Slf4j
public class BubblePane extends Pane implements Initializable {

    private String message;

    private String session;

    private String account;

    private Label accountName;

    private Label messageLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public BubblePane(){
    }



}
