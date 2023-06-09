package com.ifx.account.vo.session;

import lombok.Data;

import java.io.Serializable;
@Data
public class SessionSearchVo implements Serializable {

    private Long sessionId;

    private String sessionName;


}
