package com.ifx.account.enums;

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.function.Function;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/13
 */
@Slf4j
public enum LogEnum {
    JDK (){
        @Override
        public void outLog(String log) {
            System.out.println(log);
        }
    },

@NotNull SLf4j(){
        @Override
        public void outLog(String logs) {
            log.info(logs);
        }

        @Override
        public <T> Function<T, String> outLogFunction() {
            return (logs)->{
                log.info(logs.toString());
                return SLf4j.name();
            };
        }
    }
    ;

    public void outLog(String log){
    }

    public void outLog(Function<String,String> function, String logs){
        function.apply(logs);
    }

    public <T> Function<T,String> outLogFunction(){
    return  (logs)-> {
            System.out.println(logs);
            return JDK.name();
        };
    }
}
