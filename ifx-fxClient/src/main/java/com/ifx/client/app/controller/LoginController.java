package com.ifx.client.app.controller;


import com.ifx.account.vo.AccountVo;
import com.ifx.client.service.helper.AccountHelper;
import com.ifx.client.util.FxmlLoader;
import com.ifx.connect.proto.Protocol;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
    private CheckBox remberPsdCheckBox;

    @Autowired
    private WebClient webClient;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        passwordField.setText("wangpeng");
        accountField.setText("wangpeng");
        log.info ("initialing login controller ");
    }

    @FXML
    public void login(MouseEvent event) {
        AccountVo accountVo = new AccountVo();
        accountVo.setAccount( accountField.getText());
        accountVo.setPassword(passwordField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("登录状态");
        webClient
            .post()
            .uri("/api/account/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(accountVo)
            .retrieve()
            .onStatus(HttpStatusCode::isError,
                (clientResponse ) ->
                    clientResponse
                    .bodyToMono(ProblemDetail.class)
                    .flatMap(problemDetail ->
                        Mono.error(()->
                            new RuntimeException(problemDetail.getDetail()))))
            .bodyToMono(AccountVo.class)
                .doOnError((throwable)-> {
                    log.error( throwable.getMessage());
                    log.info( throwable.getMessage());
                })
            .subscribe(acc -> {
                Platform.runLater(()->  {
                    hide();
                    MainController.show();
                });
            });

        alert.contentTextProperty().addListener((a1,a2,a3)-> {
            alert.show();
        });

        log.info("启动登录");
        Protocol login = AccountHelper.applyLogins(accountVo);
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
        log.debug("隐藏数据");
        stage.hide();
    }
}
