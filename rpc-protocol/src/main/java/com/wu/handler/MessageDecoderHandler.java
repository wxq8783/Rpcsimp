package com.wu.handler;



import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoderHandler extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        System.out.println("--------------------------解码");
//        KryoCoderService kryoCoderService = new KryoCoderService();
//        int readableBytes = in.readableBytes();
//        int messageLength = in.readInt();
//        if(messageLength < 1){
//            ctx.close();
//        }
//        try {
//            byte[] messageByte = new byte[messageLength];
//            in.readBytes(messageLength);
//            Object object = kryoCoderService.decoder(messageByte);
//            out.add(object);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
