package com.ifx.common.properties;

import lombok.Data;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/3
 */

@Component
@ConfigurationProperties(prefix = "ifx.connect.client")
@Data
public class R2DBCProperties {



    private String  driver ;

    private String host ;

    private String user ;

    private Integer port ;

    private String  password ;

    private String dataBase;
}
