package com.ifx.connect.handler.server;

import com.ifx.common.base.AccountInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengpeng
 * @description
 * @date 2023/3/8
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerInboundHandler extends ChannelInboundHandlerAdapter {

    public static AttributeKey<AccountInfo> AccAttr = AttributeKey.valueOf(AccountInfo.class.getName());


//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        log.info("channel  client is Registered");
//        Channel channel = ctx.channel();
//        ByteBuf buffer = channel.alloc().buffer();
//        log.info("receiving  data");
//        if (buffer.readableBytes() < 4){
//            log.debug(" readableBytes is less 4");
//            super.channelRegistered(ctx);
//            return;
//        }
//
//        int length = buffer.readInt();  // 获取长度
//        if (length < 0) {
//            ctx.close();
//            log.error("[IM msg decoder]message length less than 0, channel closed");
//            super.channelRegistered(ctx);
//        }
//
//        if (length > buffer.readableBytes() - 4) {
//            buffer.resetReaderIndex();
//            super.channelRegistered(ctx);
//
//        }
//        int type = buffer.readInt();
//
//        ProtocolType.ProtocolMessageEnum protocolMessageEnum = ProtocolType.ProtocolMessageEnum.forNumber(type);
//        MessageMapEnum byEnum = MessageMapEnum.getByEnum(protocolMessageEnum);
//        if (byEnum == MessageMapEnum.CHAT){
//            ByteBuf byteBuf = buffer.readBytes(length);
//            Auth.Authenticate authenticate = Auth.Authenticate.parseFrom(byteBuf.array());
//            String jwt = authenticate.getJwt();
//            log.info(" The   jwt token is {}",jwt);
//            AccountInfo accountInfo = AccountJwtUtil.verifyAndGetClaim(jwt);
//            channel.attr(AccAttr).set(accountInfo);
//        }
//        ctx.channel().writeAndFlush("have you login?");
//        super.channelRegistered(ctx);
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info("channel  client is reading");
//        Channel channel = ctx.channel();
//        ByteBuf buffer = channel.alloc().buffer();
//        log.info("receiving  data");
//        if (buffer.readableBytes() < 4){
//            log.debug(" readableBytes is less 4");
//            return;
//        }
//
//        int length = buffer.readInt();  // 获取长度
//        if (length < 0) {
//            ctx.close();
//            log.error("[IM msg decoder]message length less than 0, channel closed");
//            return;
//        }
//
//        if (length > buffer.readableBytes() - 4) {
//            buffer.resetReaderIndex();
//            return;
//        }
//        int type = buffer.readInt();
//
//        ProtocolType.ProtocolMessageEnum protocolMessageEnum = ProtocolType.ProtocolMessageEnum.forNumber(type);
//        MessageMapEnum byEnum = MessageMapEnum.getByEnum(protocolMessageEnum);
//        if (byEnum == MessageMapEnum.CHAT){
//            ByteBuf byteBuf = buffer.readBytes(length);
//            Auth.Authenticate authenticate = Auth.Authenticate.parseFrom(byteBuf.array());
//            String jwt = authenticate.getJwt();
//            log.info(" The   jwt token is {}",jwt);
//            AccountInfo accountInfo = AccountJwtUtil.verifyAndGetClaim(jwt);
//            channel.attr(AccAttr).set(accountInfo);
//        }
//        super.channelRead(ctx, msg);
//    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
