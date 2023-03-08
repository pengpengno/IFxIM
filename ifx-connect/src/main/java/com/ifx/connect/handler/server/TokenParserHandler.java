package com.ifx.connect.handler.server;

import com.ifx.common.base.AccountInfo;
import com.ifx.common.utils.AccountJwtUtil;
import com.ifx.connect.enums.MessageMapEnum;
import com.ifx.connect.proto.Auth;
import com.ifx.connect.proto.ProtocolType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengpeng
 * @description
 * @date 2023/2/28
 */
@Slf4j
public class TokenParserHandler extends ChannelDuplexHandler {



    public static AttributeKey<AccountInfo> AccAttr = AttributeKey.valueOf(AccountInfo.class.getName());


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        if (msg instanceof ByteBuf) {
//            ByteBuf buf = (ByteBuf) msg;
//            ByteBuf newBuf = ctx.alloc().buffer(buf.readableBytes() + token.length() + 1);
//            newBuf.writeByte(token.length());
//            newBuf.writeBytes(token.getBytes());
//            newBuf.writeByte('|');
//            newBuf.writeBytes(buf);
//            msg = newBuf;
//        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        ctx.channel().
//        if (msg instanceof ByteBuf) {
//            ByteBuf buf = (ByteBuf) msg;
//            int tokenLength = buf.readByte();
//            byte[] tokenBytes = new byte[tokenLength];
//            buf.readBytes(tokenBytes);
//            String receivedToken = new String(tokenBytes);
//            if (receivedToken.equals(token)) {
//                // Token is valid, strip it off and pass the message up the pipeline
//                ByteBuf newBuf = ctx.alloc().buffer(buf.readableBytes() - tokenLength - 1);
//                buf.skipBytes(1);
//                newBuf.writeBytes(buf);
//                msg = newBuf;
//            } else {
//                // Token is not valid, close the connection
//                ctx.close();
//                return;
//            }
//        }
        super.channelRead(ctx, msg);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channel  client is register");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel  client is active");
        Channel channel = ctx.channel();
        ByteBuf buffer = channel.alloc().buffer();

        if (buffer.readableBytes() < 4){
            log.debug(" readableBytes is less 4");
            return;
        }

        int length = buffer.readInt();  // 获取长度
        if (length < 0) {
            ctx.close();
            log.error("[IM msg decoder]message length less than 0, channel closed");
            return;
        }

        if (length > buffer.readableBytes() - 4) {
            buffer.resetReaderIndex();
            return;
        }
        int type = buffer.readInt();

        ProtocolType.ProtocolMessageEnum protocolMessageEnum = ProtocolType.ProtocolMessageEnum.forNumber(type);
        MessageMapEnum byEnum = MessageMapEnum.getByEnum(protocolMessageEnum);
        if (byEnum == MessageMapEnum.CHAT){
            ByteBuf byteBuf = buffer.readBytes(length);
            Auth.Authenticate authenticate = Auth.Authenticate.parseFrom(byteBuf.array());
            String jwt = authenticate.getJwt();
            log.info(" The   jwt token is {}",jwt);
            AccountInfo accountInfo = AccountJwtUtil.verifyAndGetClaim(jwt);
            channel.attr(AccAttr).set(accountInfo);
        }
    }
}
