package com.wu.handler;


import com.wu.coder.kryo.KryoCoderService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class MessageDecoderHandler extends ByteToMessageDecoder {
    @Autowired
    KryoCoderService kryoCoderService;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("--------------------------解码");
        int messageLength = in.readInt();
        if(messageLength < 1){
            ctx.close();
        }
        try {
            byte[] messageByte = new byte[messageLength];
            in.readBytes(messageLength);
            Object object = kryoCoderService.decoder(messageByte);
            out.add(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
