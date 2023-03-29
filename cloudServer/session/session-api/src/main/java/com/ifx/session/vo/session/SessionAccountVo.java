package com.ifx.session.vo.session;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * 会话创建实体
 * @author pengpeng
 * @description
 * @date 2023/1/17
 */
@Data
public class SessionAccountVo extends SessionInfoVo {


    @NotEmpty(message = "The specify account could not be empty!" , groups = {SessionAccountAdd.class})
    private Set<Long> addUseIdSet;



    /**
     * @author pengpeng
     * @description
     * @date 2023/1/17
     */
    public interface SessionAccountAdd {
    }
}
