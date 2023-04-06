package com.ifx.client.app.frame;

import javafx.stage.Stage;

import java.util.concurrent.ConcurrentHashMap;

public class FrameViewer {

    public ConcurrentHashMap<String, Stage> frameCache ;
    private static FrameViewer instance = null;

    public FrameViewer getInstance(){
        if (instance == null){
            frameCache = new ConcurrentHashMap<>();
            instance = new FrameViewer();
        }
        return instance;
    }

    private FrameViewer(){}


}
