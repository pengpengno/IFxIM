package com.ifx.connect.enums;

import cn.hutool.core.util.ObjectUtil;
import com.ifx.connect.proto.Auth;
import com.ifx.connect.proto.Chat;
import com.ifx.connect.proto.ProtocolType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/8
 */
@Getter
@AllArgsConstructor
public enum MessageMapEnum {

    CHAT (ProtocolType.ProtocolMessageEnum.CHAT, Chat.ChatMessage.class),
    AUTH (ProtocolType.ProtocolMessageEnum.AUTH, Auth.Authenticate.class),
    ;



    private final ProtocolType.ProtocolMessageEnum typeEnum;  // 类型枚举


    private final Class<?> messageClass;  //  对应的实体类



    public static MessageMapEnum  getByEnum(ProtocolType.ProtocolMessageEnum typeEnum){
        return Arrays.stream(MessageMapEnum.values()).filter(e-> ObjectUtil.equals(e.typeEnum,typeEnum)).findFirst().orElseThrow(()-> new IllegalArgumentException("the enum provided is illegal!"));
    }

    public static MessageMapEnum  getByMessageClass(Class<?> messageClass){
        return Arrays.stream(MessageMapEnum.values()).filter(e-> ObjectUtil.equals(e.messageClass,messageClass)).findFirst().orElseThrow(()-> new IllegalArgumentException("the enum provided is illegal!"));
    }
}
