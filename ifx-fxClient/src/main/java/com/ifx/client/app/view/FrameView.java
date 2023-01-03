package com.ifx.client.app.view;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.function.Supplier;

/**
 * 界面视图
 */
public interface FrameView  {
    /**
     * 初始化界面
     * @return
     * @throws IOException
     */
    public Stage init() throws IOException;

    public void show(Supplier<? extends FrameView> supplier);

    public void show(String filePath);

    public Supplier<? extends  FrameView> applyFrameView(String filePath);

}
