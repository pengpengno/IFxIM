package com.ifx.client.app.controller;

import cn.hutool.core.util.StrUtil;
import com.ifx.account.vo.AccountVo;
import com.ifx.client.service.helper.RegisterHelper;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.common.res.Result;
import com.ifx.connect.connection.client.ClientAction;
import com.ifx.connect.proto.Protocol;
import com.ifx.connect.task.handler.TaskHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/***
 * 注册界面
 */
@Component
@Slf4j
public class RegisterController  {

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
    private RegisterHelper registerHelper;
    @FXML
    void register(MouseEvent event) throws NoSuchMethodException {
        AccountVo accountVo = new AccountVo();
        accountVo.setAccount(accountField.getText());
        accountVo.setPassword(psdField.getText());
        accountVo.setEmail(mailField.getText());

        TaskHandler taskHandler = resProtocol -> {
//            String account = (String) resProtocol.getContent().getDataAsTClass().get(0);
            Result result = resProtocol.getResult();
            Object res = result.getRes();
//            Object o = result.getDataAsTClass().get(0);
            String account = result.getDataAsTClass(String.class);
//            String account = JSONObject.parseObject(res.toString(), String.class);
            if (StrUtil.isNotBlank(account)){
                log.info("注册成功！ {} ",account);
            }
            log.info("注册成功！");
        };
        Protocol registerProtocol = registerHelper.applyRegister(accountVo);
        clientAction.sendJsonMsg(registerProtocol, taskHandler);
    }

    @FXML
    void cancel(MouseEvent event)   {

        Stage stage = SpringFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\register.fxml");
        Stage loginStage = SpringFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\login.fxml");
        stage.hide();
        loginStage.toFront();
    }

    public static void show(){
        Stage stage = SpringFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\register.fxml");
        log.info("prepare to show  register");
        stage.show();
        stage.setTitle("注册");
    }



}
