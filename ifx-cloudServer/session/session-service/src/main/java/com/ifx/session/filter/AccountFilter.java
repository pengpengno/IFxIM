package com.ifx.session.filter;

import com.ifx.session.consts.IFxCommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * dubbo服务Filter
 */
@Activate(group = {org.apache.dubbo.common.constants.CommonConstants.PROVIDER, org.apache.dubbo.common.constants.CommonConstants.CONSUMER})
@Slf4j
public class AccountFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext clientAttachment = RpcContext.getClientAttachment();
        log.info("正在 经过 AccountFilter");
        Result invoke = invoker.invoke(invocation);
        return invoke;
    }
}
