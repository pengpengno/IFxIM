package com.ifx.client.app.pane;

import com.ifx.common.base.AccountInfo;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;


@Slf4j
/**
 * 查询面板
 */
public class AccountMiniPane extends Pane implements Initializable {

    public AccountInfo accountInfo;

    private Label name;

    private ImageView iconView;

    public AccountMiniPane(){

    }
    public AccountMiniPane (AccountInfo accountInfo)
    {
        this.accountInfo = accountInfo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(){
//        1.初始化标签文本
        name.setText(accountInfo.getUserName());
//        2.初始化容器大小
        name.setLayoutX(20d);

        name.setLayoutY(10d);


    }



}
