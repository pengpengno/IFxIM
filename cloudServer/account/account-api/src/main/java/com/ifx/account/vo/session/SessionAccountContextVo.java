package com.ifx.account.vo.session;

import com.ifx.common.base.AccountInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SessionAccountContextVo extends SessionInfoVo implements Serializable {


    private Map<Long,AccountInfo> sessionAccountContext;


}
