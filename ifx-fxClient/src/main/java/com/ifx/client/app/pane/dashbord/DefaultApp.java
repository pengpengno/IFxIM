package com.ifx.client.app.pane.dashbord;

import cn.hutool.core.lang.Assert;
import com.ifx.client.app.pane.viewMain.APPEnum;
import com.ifx.client.app.pane.viewMain.MainView;
import com.ifx.client.app.pane.viewMain.SessionMainView;
import com.ifx.common.base.AccountInfo;
import com.ifx.common.context.AccountContext;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/2
 */

@Component
@Slf4j
public class DefaultApp extends Pane implements MiniApplication, Initializable {


    private Label applicationName;

    private JFXButton applicationButton;

    private Image applicationIcon ;

    @Autowired
    private SessionMainView sessionMainView;



    @Autowired
    MainView mainView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPane();
    }

    @Override
    public APPEnum appName() {
        return APPEnum.DEFAULT;
    }



    public Mono<Void> initEvent(){
       return initSessionInfoEvent();
    }


    public Mono<Void> initSessionInfoEvent(){

        log.info("init SessionInfo");
        applicationButton.setOnMouseClicked(mouse-> {
            log.info("click {}" ,appName());
            mainView.switchPane(appName());
        });

        AccountInfo curAccount = AccountContext.getCurAccount();

        Assert.notNull(curAccount,"AccountInfo  is invalid , pls try login again!");
        return Mono.empty();
    }



    public void initPane(){

        applicationName = new Label(appName().name());

        applicationButton = new JFXButton(appName().name());

        Font customFontBoldItalic = Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 30);

        applicationButton.setStyle("-fx-text-fill: white;");

        applicationButton.setFont(customFontBoldItalic);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(61,100,100),null,null)));

        this.getChildren().add(applicationButton);

        initEvent().subscribe();


    }


}
