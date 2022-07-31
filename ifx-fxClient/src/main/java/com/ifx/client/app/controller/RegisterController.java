package com.ifx.client.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegisterController {

    @FXML
    private TextField regPsdField;

    @FXML
    private TextField regPsdField1;

    @FXML
    private Label registAccountField;

    @FXML
    private Button registBut;

    @FXML
    private Button registBut1;

    @FXML
    private VBox registerFrame;

    @FXML
    void register(ActionEvent event) {
//    1. 发送注册请求
//        2.  准备序列化数据进行通信
    }

    @FXML
    void toLoginFrame(ActionEvent event) {
//        1. 判断是否完成信息录入
    }

}
