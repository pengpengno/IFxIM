package com.ifx.client.app.controller;

import cn.edu.scau.biubiusuisui.annotation.FXController;
import cn.edu.scau.biubiusuisui.annotation.FXWindow;
import cn.edu.scau.biubiusuisui.entity.FXBaseController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//@FXWindow(mainStage = false, title = "RegisterController")
//@FXController(path = "com/ifx/client/app/fxml/main.fxml")
public class MainController  extends FXBaseController {

    @FXML
    private ScrollBar mainMsgFxml;

    @FXML
    private TextArea msgTextArea;

    @FXML
    private TextField searchField;

    @FXML
    void sendMsg(MouseEvent event) {
//        1. 发送信息
    }

}
