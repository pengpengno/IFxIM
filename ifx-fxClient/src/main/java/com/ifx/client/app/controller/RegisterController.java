package com.ifx.client.app.controller;

import cn.edu.scau.biubiusuisui.annotation.FXController;
import cn.edu.scau.biubiusuisui.annotation.FXWindow;
import cn.edu.scau.biubiusuisui.entity.FXBaseController;
import cn.edu.scau.biubiusuisui.entity.FXPlusLocale;
import cn.hutool.core.util.StrUtil;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.service.RegisterService;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.netty.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.TaskHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
//@FXWindow(mainStage = false, title = "RegisterController")
//@FXController(path = "com/ifx/client/app/fxml/register.fxml")
public class RegisterController  extends FXBaseController {

    @FXML
    private TextField accountField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField psdField;

    @FXML
    private Button cancel;

    @FXML
    private Button register;

    @FXML
    private VBox registerFrame;


    @Resource
    private ClientAction clientAction;
    @Resource
    private SpringFxmlLoader springFxmlLoader;

    @Resource
    private RegisterService registerService;
    @FXML
    void register(MouseEvent event) {
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount(accountField.getText());
        accountBaseInfo.setPassword(psdField.getText());
        accountBaseInfo.setEmail(mailField.getText());

        TaskHandler taskHandler = resProtocol -> {
            String account = (String)resProtocol.getRes().getData().get(0);
            if (StrUtil.isNotBlank(account)){
                log.info("注册成功！");
            }

        };
        Protocol registerProtocol = registerService.applyRegister(accountBaseInfo);
        clientAction.sendJsonMsg(registerProtocol, taskHandler);
    }

    @FXML
    void cancel(MouseEvent event)   {

        Stage stage = springFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\register.fxml");
        Stage loginStage = springFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\login.fxml");
        stage.hide();
        loginStage.toFront();
    }



}
