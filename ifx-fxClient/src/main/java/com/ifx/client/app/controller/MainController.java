package com.ifx.client.app.controller;


import cn.hutool.core.io.FileUtil;
import com.ifx.account.vo.search.AccountSearchVo;
import com.ifx.client.app.pane.dashbord.DashBoardPane;
import com.ifx.client.app.pane.viewMain.MainView;
import com.ifx.client.util.FxmlLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private FlowPane dashBoardFlowPane;

    @FXML
    private Pane  viewMainPane;

    @Autowired
    MainView mainView;

    @Autowired
    DashBoardPane dashBoard;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("Main Controller is init");
        mainView.initialize(null,null);
        dashBoard.initialize(null,null);

        mainView.prefWidthProperty().bind(viewMainPane.widthProperty());
        dashBoard.prefHeightProperty().bind(dashBoardFlowPane.heightProperty());
        viewMainPane.getChildren().add(mainView);
        dashBoardFlowPane.getChildren().add(dashBoard);


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
        log.info("prepare to show  main");
        Image icon = new Image(FileUtil.getInputStream("icon/title/conversation.png"));
        stage.getIcons().add(icon);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("IFx");
        stage.show();

    }


}
