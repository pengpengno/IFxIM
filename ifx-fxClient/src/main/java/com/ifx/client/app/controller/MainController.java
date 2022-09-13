package com.ifx.client.app.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.account.vo.AccountSearchVo;
import com.ifx.client.service.AccountService;
import com.ifx.client.service.ClientService;
import com.ifx.client.service.helper.MainHelper;
import com.ifx.common.base.AccountInfo;
import com.ifx.connect.proto.Protocol;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

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

    @Resource
    private AccountService accountService;

    @Resource
    private ClientService clientService;

    @FXML
    private ListView<String> listView;

    private AccountInfo chatAcc; // 正在发起会话的用户

    @FXML
    void sendMsg(MouseEvent event) {
        log.info("button");
        boolean supported = Platform.isSupported(ConditionalFeature.INPUT_METHOD);
        searchField.textProperty().addListener((obs-> {
            String text = searchField.getText();
            log.info("当前文本为 {} ",text);
            AccountSearchVo accountSearchVo = new AccountSearchVo();
            accountSearchVo.setAccount(text);
            Protocol query = accountService.query(accountSearchVo);
            clientService.send(query,(protoCol -> {
                List data = protoCol.getRes().getData();
                log.info(JSON.toJSONString(protoCol));
                List<AccountInfo> accountInfos = JSON.parseArray(JSON.toJSONString(data), AccountInfo.class);
                accountInfos.stream().forEach(e-> {
                    listView.getItems().add(e.getAccount());
                });
            }));
        }));
//        1. 发送信息
        String text = msgTextArea.getText();
        boolean empty = text.isEmpty();
    }

    @FXML
    void searchAcc(InputMethodEvent event) {
//        1. 查询 所有用户  (缓存没有则通过 请求 查询)
//        2. 用户列表回传
        String text = searchField.getText();
        log.info("当前文本为 {} ",text);
//        searchField.
        AccountSearchVo accountSearchVo = new AccountSearchVo();
        accountSearchVo.setAccount(text);
        Protocol query = accountService.query(accountSearchVo);
        clientService.send(query,(protoCol -> {
            List data = protoCol.getRes().getData();
            List<AccountInfo> accountInfos = JSON.parseArray(JSON.toJSONString(data), AccountInfo.class);
//            AccountInfo accountInfo = JSONObject.parseObject(data.toString(), AccountInfo.class);
            accountInfos.stream().forEach(e-> {
                listView.getItems().add(e.getAccount());
            });
        }));
    }


}
