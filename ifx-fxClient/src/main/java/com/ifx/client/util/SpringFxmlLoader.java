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
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SpringFxmlLoader {
    
    private final ConcurrentHashMap<String,Stage> stageMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,Scene> sceneMap = new ConcurrentHashMap<>();

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

    public Scene applyScene(URL url) {
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
    
    public Scene applyScene(String classPath) {
        try{
            URL resource = FileUtil.file(classPath).toURI().toURL();
            Scene scene = applyScene(resource);
            sceneMap.put(classPath,scene);
            return scene;
        }
        catch (Exception e){
            log.warn(" fxml path wrong  can not wired {}",classPath);
        }
        return null;
    }

    /**
     * 获取单例的Stage
     * @param classPath
     * @return
     */
    public Stage applySinStage(String classPath){
        Stage resStage = stageMap.computeIfAbsent(classPath, (path) -> {
            Stage stage = new Stage();
            stage.setScene(sceneMap.computeIfAbsent(classPath, (v) -> applyScene(v)));
            return stage;
        });
        stageMap.put(classPath,resStage);
        return resStage;
    }

//    /**
//     * 获取单例的 Stage
//     * @param url
//     * @return
//     */
//    public Stage applySinStage(URL url){
//
//        Stage resStage = stageMap.computeIfAbsent(url.getPath(), (path) -> {
//            Stage stage = new Stage();
//            stage.setScene(sceneMap.computeIfAbsent(classPath, (v) -> applyScene(v)));
//            return stage;
//        });
//        stageMap.put(classPath,resStage);
//        return resStage;
//    }
}
