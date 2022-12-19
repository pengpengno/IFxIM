package com.ifx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    SUC(1),
    FAIL(0),
    ;
    private final Integer code;

}
