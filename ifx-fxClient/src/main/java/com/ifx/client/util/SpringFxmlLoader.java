package com.ifx.client.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ifx.client.ClientApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
@Slf4j
@Component
public class SpringFxmlLoader {
    

    public Object load(String url, String resources) {

        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(SpringUtil::getBean);

        loader.setLocation(getClass().getResource(url));

        loader.setResources(ResourceBundle.getBundle(resources));

        try {

            return loader.load();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;

    }
    public Object load(URL url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(SpringUtil::getBean);
        loader.setLocation(url);
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public FXMLLoader applyLoad(URL url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(SpringUtil::getBean);
        loader.setLocation(url);
//        loader.setResources(ResourceBundle.getBundle(resources));
        return loader;
    }

    public Scene applyStage(URL url) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setControllerFactory(SpringUtil::getBean);
            Scene scene = new Scene(fxmlLoader.load());

//        loader.setResources(ResourceBundle.getBundle(resources));
            return scene;
        }
        catch (Exception e){
            log.error("create stage fail {}", ExceptionUtil.stacktraceToString(e));
//            throw e;
        }
        return null;

    }
    
    public Scene applyStage(String classPath) {
        try{
            URL resource = FileUtil.file(classPath).toURI().toURL();
            return applyStage(resource);
        }
        catch (Exception e){
            log.warn(" fxml path wrong  can not wired {}",classPath);
        }
        return null;
    }
    

}
