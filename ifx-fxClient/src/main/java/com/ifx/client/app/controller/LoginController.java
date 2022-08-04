package com.ifx.client.app.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.netty.NettyClientAction;
import com.ifx.connect.netty.client.ClientAction;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
    public void login(MouseEvent event) {
        ClientAction bean = SpringUtil.getBean(ClientAction.class);
        CharSequence characters = accountField.getCharacters();
        String psd = passwordField.getCharacters().toString();
        String  account = characters.toString();
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount(account);
        accountBaseInfo.setPassword(psd);
        ChannelFuture sent = clientAction.sent(JSON.toJSONString(accountBaseInfo));
//        sent.addListener((ChannelFutureListener) future -> {
//
//        })
//        clientAction.sent(bean);

//        bean.sent(account);

    }


    @FXML
    void cancelLogin(MouseEvent event) {

    }

    @FXML
    void toRegister(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        URL resource = FileUtil.file("com\\ifx\\client\\app\\fxml\\register.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        Scene scene = new Scene(fxmlLoader.load());
//        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();
    }


}
