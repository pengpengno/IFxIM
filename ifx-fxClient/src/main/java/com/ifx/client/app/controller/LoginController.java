package com.ifx.client.app.controller;


import com.ifx.account.vo.AccountVo;
import com.ifx.client.api.AccountApi;
import com.ifx.client.util.FxmlLoader;
import com.ifx.common.context.AccountContext;
import com.ifx.connect.connection.client.ReactiveClientAction;
import com.ifx.connect.mapstruct.ProtoBufMapper;
import com.ifx.connect.proto.Account;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;
import java.util.ResourceBundle;


@Slf4j
@Component
public class LoginController  implements Initializable {

    @FXML
    private Label account;

    @FXML
    private TextField accountField;

    @FXML
    private CheckBox autoLoginCheckBox;

    @FXML
    private Button cancel;

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
    private CheckBox rememberPsdCheckBox;

    @Autowired
    private WebClient webClient;

    @Autowired
    private AccountApi accountApi;

    @Autowired
    ReactiveClientAction reactiveClientAction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordField.setText("wangpeng");
        accountField.setText("wangpeng");
        log.debug ("initialing login controller ");
    }

    @FXML
    public void login(MouseEvent event) {
        AccountVo accountVo = AccountVo.builder().account(accountField.getText())
                .password(accountField.getText()).build();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("登录状态");
        accountApi.login(accountVo)
            .map(acc -> {
                AccountContext.setCurAccount(acc);
                Account.AccountInfo accountInfo = ProtoBufMapper.INSTANCE.protocolAccMap(acc);
                return Account.Authenticate
                        .newBuilder()
                        .setAccountInfo(accountInfo)
                        .build();
            })
                .subscribe(auth-> {
                    reactiveClientAction.sendMessage(auth).subscribe();

                    Platform.runLater(()->  {
                        log.debug("start main frame");
                        hide();
                        MainController.show();
                    });
                });

        alert.contentTextProperty().addListener((a1,a2,a3)-> alert.show());

    }


    @FXML
    void cancelLogin(MouseEvent event) {
        log.info("正在关闭客户端程序-----");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void toRegister(MouseEvent event)   {
        RegisterController.show();
    }


    public static void show(){
        Stage stage = FxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\login.fxml");
        log.debug("prepare to show  register");
        stage.show();
        stage.setTitle("注册");
    }

    public static  void hide(){
        Stage stage = FxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\login.fxml");
        log.debug("Hide LoginFrame ");
        stage.hide();
    }
}
