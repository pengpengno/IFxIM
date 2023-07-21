package com.ifx.client.app.pane.message;

import com.ifx.account.vo.session.SessionInfoVo;
import com.ifx.client.util.FontUtil;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/22
 */
public class DefaultMessagePane extends MessagePane{

    private Label label ;




    public DefaultMessagePane(SessionInfoVo vo) {
        super(vo);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        label = new Label("No message try to start new session ");
        label.setFont(FontUtil.ArialFont);
//        super.initialize(location, resources);
    }



}
