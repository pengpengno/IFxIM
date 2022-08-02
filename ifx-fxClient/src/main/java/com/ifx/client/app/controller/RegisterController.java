package com.ifx.client.app.controller;

import com.ifx.client.netty.NettyClient;
import com.ifx.client.netty.NettyClientAction;
import com.ifx.connect.netty.client.ClientAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.swing.*;

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


    private ClientAction action = NettyClientAction.getInstance();
    @FXML
    void register(ActionEvent event) {
//      1. 获取组件信息
//        2. 发送注册请求
//        3. 判断注册状态 成功？or失败

//        action.sent();
    }

    @FXML
    void toLoginFrame(ActionEvent event) {
//        1. 判断是否完成信息录入
//        跳转到loginFrame
    }

}
