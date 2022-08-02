package com.ifx.client.app.controller;

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

    private ClientAction clientAction = new NettyClientAction();

    @FXML
    void login(MouseEvent event) {
        CharSequence characters = accountField.getCharacters();
        String s = characters.toString();
        clientAction.sent(s);
    }


    @FXML
    void cancelLogin(MouseEvent event) {

    }


}
