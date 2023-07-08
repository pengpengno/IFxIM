package com.ifx.client.app.controller;


import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.client.api.ChatApi;
import com.ifx.client.api.SessionApi;
import com.ifx.client.app.pane.message.ChatMainPane;
import com.ifx.client.app.pane.session.SessionListPane;
import com.ifx.client.app.pane.viewMain.MainView;
import com.ifx.client.util.FxmlLoader;
import com.ifx.connect.connection.client.ReactiveClientAction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Slf4j
public class MainController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private Pane  dashBoardPane;

    @FXML
    private Pane  viewMainPane;

    @Autowired
    ReactiveClientAction reactiveClientAction;

    @Autowired
    ChatApi chatApi;

    @Autowired
    SessionApi sessionApi;

    @Autowired
    MainView mainView;

    private final SessionListPane sessionListPane = SessionListPane.getInstance();

    private final ChatMainPane chatMainPane = ChatMainPane.getInstance();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.debug(" {} is loading ...", getClass().getName());
        log.debug("The receive handler had built");
        chatMainPane.init();

        viewMainPane.getChildren().add(mainView);

        initSearch();

    }




    protected void initSearch(){
        searchField.textProperty().addListener((obs-> {
            String text = searchField.getText();
            AccountSearchVo build =
                AccountSearchVo.builder()
                .likeAccount(searchField.getText())
                .build();
        }));
    }

    @FXML
    void searchAcc(InputMethodEvent event) {
        String text = searchField.getText();
        log.info("当前文本为 {} ",text);
        AccountSearchVo build =
                AccountSearchVo.builder()
                .likeAccount(searchField.getText())
                .build();
    }

    public static void show(){
        Stage stage = FxmlLoader.applySinStage("com\\ifx\\client\\app\\fxml\\main.fxml");
        log.info("prepare to show  register");
        stage.show();
        stage.setTitle("IFx");
    }


}
