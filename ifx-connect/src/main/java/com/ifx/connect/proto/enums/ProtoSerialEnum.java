package com.ifx.connect.proto.enums;

import com.ifx.connect.proto.IMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/2
 */
@Getter
@AllArgsConstructor
public enum ProtoSerialEnum {



    MESSAGE(1001 , IMessage.class),

    ;
    private Integer header;

    private Class<?>  tClass ;




}
