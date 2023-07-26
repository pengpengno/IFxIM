package com.ifx.client.app.pane.viewMain.message;

import com.ifx.client.util.FontUtil;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/22
 */
@Component
public class DefaultMessagePane extends MessagePane{

    private Label label ;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        label = FontUtil.defaultLabel(20,"No message try to start new session ");


        this.getChildren().add(label);
    }



}
