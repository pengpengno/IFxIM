package com.ifx.client.app.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.ifx.client.netty.NettyClientAction;
import com.ifx.connect.netty.client.ClientAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LoginController {

    @FXML
    private Label account;

    @FXML
    private TextField accountField;

    @FXML
    private CheckBox autoLoginCheckBox;

    @FXML
    private Button canel;

    @FXML
    private ImageView iconView;

    @FXML
    private Button loginBut;

    @FXML
    private VBox loginFrame;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label password;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label registerAccount;

    @FXML
    private CheckBox remberPsdCheckBox;

    @Resource(name = "netty")
    private ClientAction clientAction;
    //    private ClientAction action = NettyClientAction.getInstance();

    @FXML
    void login(MouseEvent event) {
        ClientAction bean = SpringUtil.getBean(ClientAction.class);
        CharSequence characters = accountField.getCharacters();
        String psd = passwordField.getCharacters().toString();
        String  account = characters.toString();
        bean.sent(account);

    }


    @FXML
    void cancelLogin(MouseEvent event) {

    }


}
