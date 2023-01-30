package com.ifx.session.vo.session;

import com.ifx.session.valiator.SessionAccountAdd;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/***
 * 会话基础信息
 */
@Data
public class SessionInfoVo implements Serializable {


    @NotNull(message = "尚未传入选中的会话！", groups = {SessionAccountAdd.class})
    private Long sessionId; // 会话标识

    private String sessionName ; // 会话名称

    private String sessionType;
    private String group;

    private String groupName;

}
