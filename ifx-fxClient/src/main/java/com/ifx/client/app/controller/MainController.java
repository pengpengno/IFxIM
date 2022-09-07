package com.ifx.client.app.controller;


import com.ifx.client.service.helper.MainHelper;
import com.ifx.common.base.AccountInfo;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
//@FXWindow(mainStage = false, title = "RegisterController")
//@FXController(path = "com/ifx/client/app/fxml/main.fxml")
public class MainController   {

    @FXML
    private ScrollBar mainMsgFxml;

    @FXML
    private TextArea msgTextArea;

    @FXML
    private TextField searchField;

    @Resource
    private MainHelper mainHelper;

    private AccountInfo chatAcc; // 正在发起会话的用户

    @FXML
    void sendMsg(MouseEvent event) {
//        1. 发送信息
        String text = msgTextArea.getText();
        boolean empty = text.isEmpty();

    }
    @FXML
    void searchAcc(InputMethodEvent event) {
//        1. 查询 所有用户
//        2. 用户列表回传

        String text = searchField.getText();


    }


}
