package com.ifx.connect.enums;

import cn.hutool.core.util.ObjectUtil;
import com.google.protobuf.Message;
import com.ifx.connect.proto.Account;
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
public enum ProtocolMessageMapEnum {

    CHAT (ProtocolType.ProtocolMessageEnum.CHAT, Chat.ChatMessage.class),
    AUTH (ProtocolType.ProtocolMessageEnum.AUTH, Account.Authenticate.class),
    ACCOUNT (ProtocolType.ProtocolMessageEnum.ACCOUNTINFO, Account.AccountInfo.class),

    ;



    private final ProtocolType.ProtocolMessageEnum typeEnum;  // 类型枚举

    private final Class<? extends  Message> messageClass;  //  对应的实体类



    public static ProtocolMessageMapEnum getByClass(Class<? extends Message> messageClass){
        return Arrays.stream(ProtocolMessageMapEnum.values()).filter(e-> ObjectUtil.equals(e.messageClass,messageClass)).findFirst().orElseThrow(()-> new IllegalArgumentException("The provided  message is not supported !"));
    }

    public static ProtocolMessageMapEnum getByEnum(ProtocolType.ProtocolMessageEnum typeEnum){
        return Arrays.stream(ProtocolMessageMapEnum.values()).filter(e-> ObjectUtil.equals(e.typeEnum,typeEnum)).findFirst().orElseThrow(()-> new IllegalArgumentException("The provided  enum is illegal!"));
    }


}
