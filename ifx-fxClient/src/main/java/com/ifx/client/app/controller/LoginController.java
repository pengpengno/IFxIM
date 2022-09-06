package com.ifx.client.app.controller;


import cn.edu.scau.biubiusuisui.annotation.FXController;
import cn.edu.scau.biubiusuisui.annotation.FXWindow;
import cn.edu.scau.biubiusuisui.entity.FXBaseController;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.parse.DubboGenericParse;
import com.ifx.client.service.LoginService;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.context.AccountContext;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.client.service.ClientService;
import com.ifx.connect.task.TaskHandler;
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
@FXWindow(mainStage = true, title = "login")
@FXController(path = "com/ifx/client/app/fxml/login.fxml")
public class LoginController   extends FXBaseController {


//    private LoginController(){
//      log.info(LoginController.log.getName());
//    }
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

    @Resource
    private ClientService clientService;

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
        TaskHandler taskHandler = protocol -> {
            List data = protocol.getRes().getData();
            Object o = data.get(0);
            if (o == null){
                log.warn("登录失败！");
                return;
            }
            AccountInfo accountInfo = JSONObject.parseObject(o.toString(), AccountInfo.class);
            log.info("login status {}",accountInfo);
            if (accountInfo!=null){
                AccountContext.setCurAccount(accountInfo);
//                alert.setContentText("登录成功");
                log.info("login success ");
                Stage stage = springFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\main.fxml");
                Stage login = springFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\login.fxml");
                login.hide();
                log.info("prepare to show  main");
                stage.show();
                stage.setTitle("IFX");
            }else{
                alert.setContentText("登录失败");
                log.info("登录失败！");
            }
        };
        log.info("启动登录");
        Protocol login = loginService.applyLogins(accountBaseInfo);
        clientService.send(login,taskHandler);
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
