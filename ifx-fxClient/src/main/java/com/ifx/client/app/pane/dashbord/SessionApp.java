package com.ifx.client.app.pane.dashbord;

import com.google.inject.Singleton;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/2
 */

@Singleton
public class SessionApp extends DashBoardMiniApp{


    private Label applicationName;

    private String appName ;

    private Image applicationIcon ;


    private SessionApp(){
        appName = "Session";
    }

    public String getAppName(){
        return appName;
    }



}
