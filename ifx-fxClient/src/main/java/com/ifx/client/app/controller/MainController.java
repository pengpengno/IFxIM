package com.ifx.client.app.controller;


import com.alibaba.fastjson2.JSON;
import com.ifx.account.vo.AccountSearchVo;
import com.ifx.client.app.pane.SearchPane;
import com.ifx.client.service.AccountService;
import com.ifx.client.service.ClientService;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.res.Result;
import com.ifx.connect.proto.Protocol;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Slf4j
public class MainController implements Initializable {

    @FXML
    private ScrollBar mainMsgFxml;

    @FXML
    private TextArea msgTextArea;

    @FXML
    private FlowPane searchPane;

    @FXML
    private TextField searchField;

    @Resource
    private AccountService accountService;

    @Resource
    private ClientService clientService;

    @Resource
    private SpringFxmlLoader springFxmlLoader;

    @FXML
    private ListView<String> listView;

    private AccountInfo chatAcc; // 正在发起会话的用户

    private volatile Scene scene;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("{} is loading ...",getClass().getName());
        initSearch();
        Group group = new Group();
        scene = msgTextArea.getScene();
        searchPane.setVgap(8);
        searchPane.setHgap(4);
    }

    @FXML
    void sendMsg(MouseEvent event) {
        log.info("send button");
        boolean supported = Platform.isSupported(ConditionalFeature.INPUT_METHOD);
//        1. 发送信息
        String text = msgTextArea.getText();
        boolean empty = text.isEmpty();

    }


    protected void initSearch(){
        searchField.textProperty().addListener((obs-> {
            String text = searchField.getText();
            log.info("当前文本为 {} ",text);
            AccountSearchVo accountSearchVo = new AccountSearchVo();
            accountSearchVo.setAccount(text);
            Protocol query = accountService.query(accountSearchVo);
            clientService.send(query,(protoCol -> {
                String content = protoCol.getContent();
                Result result = JSON.parseObject(content, Result.class);
                List<AccountInfo> accountInfos = result.getData(AccountInfo.class);
                log.info(JSON.toJSONString(protoCol));
//                添加数据
                accountInfos.stream().forEach(e-> {
                    searchPane.getChildren().add(new SearchPane.AccountMiniPane(e));
                });
            }));
        }));
    }

    @FXML
    void searchAcc(InputMethodEvent event) {
//        1. 查询 所有用户  (缓存没有则通过 请求 查询)
//        2. 用户列表回传
        String text = searchField.getText();
        log.info("当前文本为 {} ",text);
        AccountSearchVo accountSearchVo = new AccountSearchVo();
        accountSearchVo.setAccount(text);
        Protocol query = accountService.query(accountSearchVo);
        clientService.send(query,(protoCol -> {
            String content = protoCol.getContent();
            Result result = JSON.parseObject(content, Result.class);
            List<AccountInfo> accountInfos = result.getData(AccountInfo.class);
            accountInfos.stream().forEach(e-> {
                listView.getItems().add(e.getAccount());
            });
        }));
    }


}
