package com.ifx.client.app.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.connect.netty.client.ClientAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class RegisterController {

    @FXML
    private TextField accountField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField psdField;

    @FXML
    private Button cannel;

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
    void toLoginFrame(ActionEvent event) throws IOException {
        Scene scene = springFxmlLoader.applyScene("com\\ifx\\client\\app\\fxml\\register.fxml");
        Window window = registerFrame.getScene().getWindow();
        Stage  stage = (Stage) window;
        stage.setScene(scene);

        stage.close();
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.show();
//        new
    }


}
