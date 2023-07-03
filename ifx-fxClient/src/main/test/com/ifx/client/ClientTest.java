package com.ifx.client;

import cn.hutool.core.io.FileUtil;
import javafx.application.Application;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/22
 */
public class ClientTest {
    @Test
    public  void sysin() throws IOException {
        Properties properties = new Properties();
        properties.load(FileUtil.getReader(new File("application.yaml"), Charset.defaultCharset()));


    }


    @Test
    public void startApp(){
        Application.launch(BoostrapApp.class);

    }
}
