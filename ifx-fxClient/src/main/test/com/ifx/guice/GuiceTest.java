package com.ifx.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ifx.client.spi.ModuleAbstract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/5
 */
public class GuiceTest {
    @Test
    public void assertEquals(){
        Injector injector = Guice.createInjector();
        ModuleAbstract instance = injector.getInstance(ModuleAbstract.class);
        ModuleAbstract instance2 = injector.getInstance(ModuleAbstract.class);
        Assertions.assertEquals(instance2,instance);
    }

    @Test
    public void assertProxyBean(){

    }
}
