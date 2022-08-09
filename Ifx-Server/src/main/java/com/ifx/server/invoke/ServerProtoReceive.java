package com.ifx.server.invoke;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ifx.account.service.AccountService;
import com.ifx.account.vo.AccountBaseInfo;
import com.ifx.common.res.Result;
import com.ifx.connect.enums.CommandEnum;
import com.ifx.connect.proto.Protocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServerProtoReceive {
    @DubboReference(version = "1.0.0")
    private AccountService accountService;
    public void received(ChannelHandlerContext ctx, Protocol protocol){
        try {

            String command = protocol.getCommand();
            CommandEnum commandEnum = CommandEnum.getByName(command);
            switch (commandEnum) {
                case LOGIN:
                    log.info("正在处理 login 请求额");
                    Boolean login = accountService.login(JSONObject.parseObject(protocol.getBody(), AccountBaseInfo.class));
                    Result<Boolean> res = Result.ok(CollectionUtil.newArrayList(login));
                    protocol.setRes(res);
                    Channel channel = ctx.channel();
                    channel.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(protocol), CharsetUtil.UTF_8));
//                ctx.writeAndFlush(JSON.toJSONString(protocol));
                    log.info("服务端业务处理完毕  登录状态为{} ", login);
                default:
                    break;

            }
        }catch (Exception e){
            log.error(ExceptionUtil.stacktraceToString(e));
        }
    }
}
