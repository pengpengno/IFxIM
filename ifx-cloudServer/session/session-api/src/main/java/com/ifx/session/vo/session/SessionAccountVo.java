package com.ifx.session.vo.session;

import com.ifx.common.base.AccountInfo;
import com.ifx.session.valiator.SessionAccountAdd;
import lombok.Data;
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


//    private Set<AccountInfo>  addAccountSet;   // 待添加 的账户

    private Set<String> addAccSet;

    @NotNull(message = "创建会话的账户信息不可为空！", groups = {SessionAccountAdd.class})
    private AccountInfo createInfo;   //创建账户信息




}
