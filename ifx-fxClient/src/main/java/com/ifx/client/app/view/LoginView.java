package com.ifx.client.app.view;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Supplier;
@Slf4j
@Component("login.fxml")
public class LoginView implements FrameView{

    private Stage stage = null;
    @Override
    public Stage init() throws IOException {
        Stage stage = new Stage();
        URL resource = FileUtil.file("com\\ifx\\client\\app\\fxml\\login.fxml").toURI().toURL();
//        Object load = springFxmlLoader.load(resource);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        fxmlLoader.setControllerFactory(bean -> SpringUtil.getBean(bean));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
        return stage;
    }

    @Override
    public void show(Supplier<? extends FrameView> supplier) {

    }

    @Override
    public void show(String filePath) {
        stage.show();
    }

    @Override
    public Supplier<? extends FrameView> applyFrameView(String filePath) {
        try{
            Stage init = init();
        }
        catch (Exception e ){
//            log.error();
        }
        return ()-> {
            return this;
        };
//        return null;
    }
}
