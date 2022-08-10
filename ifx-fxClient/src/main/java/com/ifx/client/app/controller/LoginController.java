package com.ifx.client.app.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.enums.CommandEnum;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.Task;
//import de.felixroske.jfxsupport.FXMLController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
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

    public void init(){
//        SpringFxmlLoader
    }

    @FXML
    public void login(MouseEvent event) {
//        ClientAction bean = SpringUtil.getBean(ClientAction.class);
        String account = accountField.getText();
        String psd = passwordField.getText();
//        String  account = characters.toString();
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount(account);
        accountBaseInfo.setPassword(psd);
        Method[] methods = AccountService.class.getMethods();

        Protocol<AccountBaseInfo> logBase = new Protocol<>();
        logBase.setBody(JSON.toJSONString(accountBaseInfo));
        logBase.setCommand(CommandEnum.LOGIN.name());
        Task task = protocol -> {
//            int code = protocol.getRes().getCode();
            List data = protocol.getRes().getData();
            Boolean loginStatus = (Boolean)data.get(0);
            if (loginStatus){
                log.info("login success ");
            }
        };
        clientAction.sendJsonMsg(logBase,task);

    }


    @FXML
    void cancelLogin(MouseEvent event) {

    }

    @FXML
    void toRegister(MouseEvent event)   {
        Stage stage = springFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\register.fxml");
        log.info("prepare to show  register");
        stage.show();
        stage.setTitle("zhuce");

    }


}
