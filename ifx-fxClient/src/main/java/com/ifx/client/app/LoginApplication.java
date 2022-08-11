package com.ifx.client.app;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import com.ifx.client.connect.netty.NettyClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.net.URL;

public class LoginApplication extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @SneakyThrows
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            NettyClient nettyClient = new NettyClient(8976);
//            init.channel().writeAndFlush("standardised");
            nettyClient.write("ewqeqweq");
        }
        catch (Throwable e ){
            System.out.println(ExceptionUtil.stacktraceToString(e));
            System.out.println("服务器链接失败！");
        }


//        URL resource = getClass().getResource("fxml\\login.fxml");
//        new URL()
//        URL resource = FileUtil.file("classpath*:**\\fxml\\login.xml").toURI().toURL();
        URL resource = FileUtil.file("com\\ifx\\client\\app\\fxml\\login.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        Scene scene = new Scene(fxmlLoader.load());
//        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
