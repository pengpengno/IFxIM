package com.ifx.fxstarter.loader;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SpringFxmlLoader {

    private static final ConcurrentHashMap<String,Stage> stageMap = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String,Scene> sceneMap = new ConcurrentHashMap<>();


    private static Scene applyScene(URL url) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setControllerFactory(SpringUtil::getBean);
            Scene scene = new Scene(fxmlLoader.load());
            return scene;
        }
        catch (Exception e){
            log.error("create stage fail {}", ExceptionUtil.stacktraceToString(e));
        }
        return null;
    }


    public static Scene applySinScene(String classPath) {
        try{
            Scene scene = sceneMap.computeIfAbsent(classPath, (k) -> {
                URL resource = null;
                try {
                    resource = FileUtil.file(classPath).toURI().toURL();
                } catch (Exception e) {
                    log.info(ExceptionUtil.stacktraceToString(e));
                }
                return applyScene(resource);
            });
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
    public static Stage applySinStage(String classPath){
        Stage resStage = stageMap.computeIfAbsent(classPath, (path) -> {
            Stage stage = new Stage();
            Scene scene = applySinScene(classPath);
            stage.setScene(scene);
            return stage;
        });
//        if windows is do close request  handler will close
        resStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST,(evt)-> {
            log.info("{} frame  is doing {} event",evt.getSource(),evt.getEventType());
            stageMap.remove(classPath);
        });

        stageMap.put(classPath,resStage);
        return resStage;
    }


}
