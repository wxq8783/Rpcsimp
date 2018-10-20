package com.wu.handler;

import com.wu.coder.kryo.KryoCoderService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageEncoderHanlder extends MessageToByteEncoder<Object> {
    @Autowired
    KryoCoderService kryoCoderService;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        System.out.println("---------------编码");
        kryoCoderService.encoder(out,msg);
    }
}
