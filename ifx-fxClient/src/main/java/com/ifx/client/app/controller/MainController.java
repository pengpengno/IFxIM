package com.ifx.client.app.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.client.app.pane.SearchPane;
import com.ifx.client.service.helper.AccountHelper;
import com.ifx.client.util.SpringFxmlLoader;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.res.Result;
import com.ifx.connect.connection.client.ClientToolkit;
import com.ifx.connect.proto.parse.ProtocolResultParser;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
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
    private Button createSession;

    @FXML
    private TextField searchField;



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
        scene = msgTextArea.getScene();
        searchPane.setVgap(8);
        searchPane.setHgap(4);
        msgTextArea.addEventHandler(KeyEvent.KEY_PRESSED ,(keyPress)-> {
            KeyCode code = keyPress.getCode();
            if (ObjectUtil.equal(code ,KeyCode.ENTER)){
//                TODO 发送消息
                msgTextArea.clear();
//                清除后光标移位到左上角
                msgTextArea.setPrefColumnCount(0);
                msgTextArea.setPrefRowCount(0);
                msgTextArea.setScrollTop(0);
                msgTextArea.setScrollLeft(0);
                log.debug("正在发送消息，");
            }
            String name = code.getName();
            log.debug("按下了 {} ",name);
        });
    }

    @FXML
    void sendMsg(MouseEvent event) {
        log.info("send button");
        boolean supported = Platform.isSupported(ConditionalFeature.INPUT_METHOD);
//        1. 发送信息
        String text = msgTextArea.getText();
        boolean empty = text.isEmpty();

    }
    @FXML
    void createSession(MouseEvent event) {
        log.info("create sesssion ");
        boolean supported = Platform.isSupported(ConditionalFeature.INPUT_METHOD);
//        1. 发送信息
        String text = msgTextArea.getText();
        boolean empty = text.isEmpty();

    }


    protected void initSearch(){
        searchField.textProperty().addListener((obs-> {
            searchPane.getChildren().clear();
            String text = searchField.getText();
            log.debug("输入的文本为 {} ",text);
            AccountSearchVo accountSearchVo = new AccountSearchVo();
            accountSearchVo.setAccount(text);
//             获取回调输出
            ClientToolkit.getDefaultClientAction().sendJsonMsg(AccountHelper.applySearchAccount(accountSearchVo),(protoCol -> {
                List<AccountInfo> accountInfos = ProtocolResultParser.getDataAsList(protoCol,AccountInfo.class);
                log.info(JSON.toJSONString(protoCol));
//                添加数据
                accountInfos.forEach(e-> searchPane.getChildren().add(new SearchPane.AccountMiniPane(e)));
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
        ClientToolkit.getDefaultClientAction().sendJsonMsg(AccountHelper.applySearchAccount(accountSearchVo),(protoCol -> {
            Result result = protoCol.getResult();


            List<AccountInfo> accountInfos = ProtocolResultParser.getDataAsList(protoCol,AccountInfo.class);
            if (listView!= null){
                accountInfos.stream().forEach(e-> {
                    listView.getItems().add(e.getAccount());
                });
            }
        }));
    }

    public static void show(){
        Stage stage = SpringFxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\main.fxml");
        log.info("prepare to show  register");
        stage.show();
        stage.setTitle("IFx");
    }


}
