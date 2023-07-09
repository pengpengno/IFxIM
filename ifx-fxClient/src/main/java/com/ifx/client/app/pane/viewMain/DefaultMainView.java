package com.ifx.client.app.pane.viewMain;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/8
 */
@Component
public class DefaultMainView extends GridPane implements Initializable,MainViewAction {

    private Label defaultLabel;


    public void initPane(){

        defaultLabel = new Label(" There is no view , try to click dashboard");

        defaultLabel.setAlignment(Pos.CENTER);


        this.setPrefSize(200,400);
        this.getChildren().add(defaultLabel);
    }

    @Override
    public APPEnum viewType() {
        return APPEnum.DEFAULT;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPane();
    }
}
