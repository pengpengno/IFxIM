package com.ifx.client.app.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.connect.netty.client.ClientAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

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

    @FXML
    void register(MouseEvent event) {
        ClientAction bean = SpringUtil.getBean(ClientAction.class);
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setAccount(accountField.getText());
        accountBaseInfo.setPassword(psdField.getText());
        accountBaseInfo.setEmail(mailField.getText());
        bean.sent(JSONObject.toJSONString(accountBaseInfo, JSONWriter.Feature.FieldBased));
    }

    @FXML
    void toLoginFrame(ActionEvent event) throws IOException {

    }


}
