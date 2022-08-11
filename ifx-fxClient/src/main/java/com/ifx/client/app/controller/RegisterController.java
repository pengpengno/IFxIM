package com.ifx.client.app.controller;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.netty.client.ClientAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RegisterController {

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
    @FXML
    void register(MouseEvent event) {
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount(accountField.getText());
        accountBaseInfo.setPassword(psdField.getText());
        accountBaseInfo.setEmail(mailField.getText());
        clientAction.sent(JSONObject.toJSONString(accountBaseInfo, JSONWriter.Feature.FieldBased));

    }

    @FXML
    void cancel(MouseEvent event)   {
//        Scene scene = springFxmlLoader.applySinScene("com\\ifx\\client\\app\\fxml\\login.fxml");
        Stage stage = springFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\register.fxml");
        Stage loginStage = springFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\login.fxml");
//        Window window = registerFrame.getScene().getWindow();
//        Stage  stage = (Stage) window;
//        stage.setScene(scene);
//        stage.close();
        stage.close();
//        stage.addEventHandler();
//        stage1.setScene(scene);
        loginStage.show();
//        new
    }

//    @FXML
//    public void cancel (){
//
//    }


}
