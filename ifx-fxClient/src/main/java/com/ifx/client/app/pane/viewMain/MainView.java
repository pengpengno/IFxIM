package com.ifx.client.app.pane.viewMain;

import cn.hutool.core.util.ObjectUtil;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/8
 */
@Slf4j
public class MainView extends Pane implements Initializable {

    @Autowired
    private List<MainViewAction> mainViewActions;

    private Pane currentView;

    @Autowired
    private DefaultMainView defaultMainView;




    public void switchPane(APPEnum viewEnum){
        MainViewAction mainViewAction = mainViewActions.stream()
                .filter(e -> ObjectUtil.equal(viewEnum, e.viewType())).findFirst()
                .orElse(defaultMainView);
        if (mainViewAction instanceof Pane pane){
            switchPane(pane);
        }

        log.warn(" Illegal main no result");
    }

    public void switchPane( Pane view ){

        int componentIndex = this.getChildren().indexOf(currentView);

        this.getChildren().remove(componentIndex);

        currentView = view;

        this.getChildren().add(componentIndex,currentView);
    }


    public void initPane() {


        this.prefHeight(200);
        this.prefWidth(200);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(161,100,100),null,null)));

        defaultMainView.initialize(null,null);

        currentView = defaultMainView;

        this.getChildren().add(currentView);


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("init MainView Pane");
        initPane();
    }





    private MainView(){

    }
    private enum SingleInstance{
        INSTANCE;
        private final MainView instance;
        SingleInstance(){
            instance = new MainView();
        }
        private MainView getInstance(){
            return instance;
        }
    }
    public static MainView getInstance(){
        return MainView.SingleInstance.INSTANCE.getInstance();
    }


}
