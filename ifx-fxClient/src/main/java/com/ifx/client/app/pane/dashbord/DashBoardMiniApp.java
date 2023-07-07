package com.ifx.client.app.pane.dashbord;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * @author pengpeng
 * @description
 * @date 2023/6/29
 */
public class DashBoardMiniApp extends Pane {



    private Label applicationName;

    private Image applicationIcon ;

    public String getAppName(){
        return "DashBoard";
    }


    public void setOnMouseClickAction(MouseEvent mouseClickAction){

    }




}
