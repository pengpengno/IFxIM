package com.ifx.session.vo.session;

import com.ifx.common.base.AccountInfo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "创建会话的账户信息不可为空！", groups = {SessionAccountAdd.class})
    private AccountInfo createInfo;   //创建账户信息




    /**
     * @author pengpeng
     * @description
     * @date 2023/1/17
     */
    public interface SessionAccountAdd {
    }
}
