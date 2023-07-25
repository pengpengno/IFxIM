package com.ifx.client.app.pane.viewMain;

import cn.hutool.core.util.ObjectUtil;
import com.ifx.client.app.enums.APPEnum;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
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
    private List<ViewAction> viewActions;

    private Pane currentView;

    @Autowired
    private DefaultView defaultMainView;



    public void switchPane(APPEnum viewEnum){

        ViewAction viewAction = viewActions.stream()
                .filter(e -> ObjectUtil.equal(viewEnum, e.viewType())).findFirst()
                .orElse(defaultMainView);

        if (viewAction instanceof Pane pane){

            switchPane(pane);

        }else {
            log.warn(" Illegal main no result");
        }

    }

    private void switchPane( Pane view ){

        int componentIndex = this.getChildren().indexOf(currentView);

        this.getChildren().remove(componentIndex);

        currentView = view;

        currentView.prefHeightProperty().bind(this.heightProperty());

        currentView.prefWidthProperty().bind(this.widthProperty());

        this.getChildren().clear();

        this.getChildren().add(componentIndex,currentView);

    }


    public void initPane() {



        defaultMainView.initialize(null,null);

        viewActions.stream().filter(e-> e!=null).forEach(e-> {
            e.initialize(null,null);
            if (e instanceof Pane pane){

                pane.prefHeightProperty().bind(this.heightProperty());
                pane.prefWidthProperty().bind(this.widthProperty());

            }
        });

        currentView = defaultMainView;

        currentView.prefHeightProperty().bind(this.heightProperty());
        currentView.prefWidthProperty().bind(this.widthProperty());

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
