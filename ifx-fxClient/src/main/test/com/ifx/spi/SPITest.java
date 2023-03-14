package com.ifx.spi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/5
 */
@Slf4j

public class SPITest {

    @Test
    public void testSPi(){
        ServiceLoader<ClientBusinessSPI> load = ServiceLoader.load(ClientBusinessSPI.class);
        for (ClientBusinessSPI  spi : load){
            spi.doBusiness();
        }
    }
}
