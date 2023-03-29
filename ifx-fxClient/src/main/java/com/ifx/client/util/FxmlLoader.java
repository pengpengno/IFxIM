package com.ifx.client.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ifx.client.ClientApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class FxmlLoader {

    private final static ConcurrentHashMap<String,Stage> stageMap = new ConcurrentHashMap<>();
    private final static ConcurrentHashMap<String,Scene> sceneMap = new ConcurrentHashMap<>();


    private static Scene applyScene(URL url) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
//            Injector injector = Guice.createInjector();
//            ProxyWiredBeanProcessor instance = injector.getInstance(ProxyWiredBeanProcessor.class);
//            fxmlLoader.setControllerFactory(instance.proxyBeanProcessor());
            fxmlLoader.setControllerFactory(SpringUtil::getBean);
            return new Scene(fxmlLoader.load());
        }
        catch (Exception e){
            log.error("create stage fail {}", ExceptionUtil.stacktraceToString(e));
        }
        return null;
    }


    /***
     * 根据给定路径加载fxml
     * @param path
     * @return
     */
    private static Scene applySceneSpring(String path) {
        try{
            Assert.notNull(path,"指定路径不可为空！");
            FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource(path));
//            Injector injector = Guice.createInjector();
//            ProxyWiredBeanProcessor instance = injector.getInstance(ProxyWiredBeanProcessor.class);
//            fxmlLoader.setControllerFactory(instance.proxyBeanProcessor());
            fxmlLoader.setControllerFactory(SpringUtil::getBean);
            return new Scene(fxmlLoader.load());
        }
        catch (Exception e){
            log.error("create stage fail {}", ExceptionUtil.stacktraceToString(e));
        }
        return null;
    }



    /***
     * url 加载 fxml 资源为 Scene
     * @param url
     * @return
     */
    public static Scene load(URL url) {
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
     * 获取单例的 Stage
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
            log.debug("{} frame  is doing {} event",evt.getSource(),evt.getEventType());
            stageMap.remove(classPath);
        });

        stageMap.put(classPath,resStage);
        return resStage;
    }

    /**
     * 获取单例的 Stage
     * @param classPath
     * @return
     */
    public static Stage applySinStageSpring(String classPath){
        Stage resStage = stageMap.computeIfAbsent(classPath, (path) -> {
            Stage stage = new Stage();
            Scene scene = applySceneSpring(classPath);
            stage.setScene(scene);
            return stage;
        });
//        if windows is do close request  handler will close
        resStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST,(evt)-> {
            log.debug("{} frame  is doing {} event",evt.getSource(),evt.getEventType());
            stageMap.remove(classPath);
        });

        stageMap.put(classPath,resStage);
        return resStage;
    }



}
