package com.ifx.fxstarter.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ifx.fxstarter.ann.FxApplication;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class FxScanUtil {


    /**
     * 查询所有 FXApplication 注解标识的类 的 路径
     * 1. 搜索注解
     *
     * @param annotation 注解
     */
    private static Set<String> scanFxAnn(Annotation annotation) {

        if (FxApplication.class != annotation.annotationType()) {
            return null;
        }
        String[] dirs = ((FxApplication) annotation).basePackage();
        Set<String> sets = new HashSet<>();
        CollectionUtil.addAll(sets, dirs);
        sets.stream().forEach(e->StrUtil.replace(e,".","/"));
        for (String dir : sets) {
            log.info("fxApplication: " + dir);
            List<String> temps = CollectionUtil.newArrayList();
            try {
//                temps = classUtil.scanAllClassName(dir);
                for (String className : temps) {
                    log.info("loading class: " + className);
                }
            } catch (Exception exception) {
                log.error("{}", exception);
            }
        }
        return null;
    }


    private static List<String> readFromJarDirectory(String path, String packageName) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<JarEntry> entrys = jarFile.entries();
        List<String> classNames = new ArrayList<>();
        while (entrys.hasMoreElements()) {
            JarEntry jarEntry = entrys.nextElement();
            if (!jarEntry.getName().endsWith(".class")) continue;
            int packageNameIndex = jarEntry.getName().indexOf("/");
            if ("".equals(packageName)) {
                classNames.add(jarEntry.getName());
            } else {
                if (packageNameIndex == -1) continue;
                String baseName = jarEntry.getName().substring(0, packageNameIndex);
                if (baseName.equals(packageName)) {
                    classNames.add(cn.hutool.core.io.FileUtil.mainName(jarEntry.getName()).replaceAll("/", "."));
                }
            }
        }
        return classNames;
    }
}
