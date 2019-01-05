package com.wu.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.stereotype.Component;

@Component
public class MessageEncoderHandler extends MessageToByteEncoder<Object> {
//    @Autowired
//    KryoCoderService kryoCoderService;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        System.out.println("---------------编码");
//        KryoCoderService kryoCoderService = new KryoCoderService();
//        kryoCoderService.encoder(out,msg);
    }
}
