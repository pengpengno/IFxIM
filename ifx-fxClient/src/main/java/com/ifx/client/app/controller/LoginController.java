package com.ifx.client.app.controller;


import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.client.service.LoginService;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.Task;
import com.sun.javafx.tk.quantum.QuantumToolkit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;


@Component
@Slf4j
public class LoginController  {


    private LoginController(){
      log.info(LoginController.log.getName());
    }
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
//    private JFXButton loginBut;
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

    @Resource
    private SpringFxmlLoader springFxmlLoader;

    @Resource
    private LoginService loginService;

    public void init(){
//        SpringFxmlLoader
    }

    @FXML
    public void login(MouseEvent event) {
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount( accountField.getText());
        accountBaseInfo.setPassword(passwordField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("登录状态");
        alert.contentTextProperty().addListener((a1,a2,a3)-> {
            alert.show();
        });
        Task task = protocol -> {
            List data = protocol.getRes().getData();
            Boolean loginStatus = (Boolean)data.get(0);
            log.info("login status {}",loginStatus);
            if (loginStatus){
                alert.setContentText("登录成功");
                log.info("login success ");
            }else{
                alert.setContentText("登录失败");
                log.info("登录失败！");
            }
        };
        log.info("启动登录");
        Protocol listAllProtocol = DubboGenericParse.applyProtocol(AccountService.class, "listAllAccoutInfo", null);
        clientAction.sendJsonMsg(listAllProtocol);
        Protocol login = loginService.applyLogins(accountBaseInfo);
        clientAction.sendJsonMsg(login,task);
    }


    @FXML
    void cancelLogin(MouseEvent event) {
        log.info("正在关闭客户端程序-----");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void toRegister(MouseEvent event)   {
        Stage stage = springFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\register.fxml");
        log.info("prepare to show  register");
        stage.show();
        stage.setTitle("注册");

    }


}
