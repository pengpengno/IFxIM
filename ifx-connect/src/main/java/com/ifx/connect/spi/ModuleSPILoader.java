package com.ifx.connect.spi;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * SPI 加载工具类
 * @author pengpeng
 * @description
 * @date 2023/2/3
 */
@Slf4j
public class ModuleSPILoader {

    /***
     * 获取 spi 第一个拓展点
     * @param loaderSPIClass
     * @return
     * @param <T>
     */
    public  synchronized static <T> T loadUniqueSPI(Class<T> loaderSPIClass){
//        synchronized (loaderSPIClass){
            ServiceLoader<T> load = ServiceLoader.load(loaderSPIClass);
            Iterator<T> iterator = load.iterator();
            if (iterator.hasNext()){
                return iterator.next();
            }else {
                log.warn("尚未发现业务处理拓展点");
                return null;
            }
//        }
    }


    /***
     * 获取 spi 第一个拓展点
     * @param loaderSPIClass
     * @param className 类全限定路径  例如 {@code com.mysql.cj.jdbc.Driver}
     * @return
     * @param <T>
     */
    public synchronized static <T> T loadSPI(Class<T> loaderSPIClass,String className){
        Objects.requireNonNull(className,"className not null");
        Objects.requireNonNull(loaderSPIClass,"loaderSpiClass not null");
        ServiceLoader<T> load = ServiceLoader.load(loaderSPIClass);
        for (T t : load) {
            String name = t.getClass().getName();
            if (className.equals(name)) {
                return t;
            }
            log.warn("尚未发现业务处理拓展点");
        }
        return null;
    }



}
