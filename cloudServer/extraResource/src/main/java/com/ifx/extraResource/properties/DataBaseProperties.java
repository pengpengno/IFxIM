package com.ifx.extraResource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/3
 */

@Component
@ConfigurationProperties(prefix = "ifx.resource.dataBase")
@Data
public class DataBaseProperties {



    private String  driver ;

    private String host ;

    private String user ;

    private Integer port ;

    private String  password ;

    private String dataBase;
}
