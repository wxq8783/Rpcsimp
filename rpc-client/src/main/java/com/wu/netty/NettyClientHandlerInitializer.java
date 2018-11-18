package com.wu.netty;

import com.wu.handler.MessageDecoderHandler;
import com.wu.handler.MessageEncoderHanlder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyClientHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("coder",new LengthFieldBasedFrameDecoder(65535,0,4,0,0));
        pipeline.addLast("encoder",new MessageEncoderHanlder());
        pipeline.addLast("decoder",new MessageDecoderHandler());
        pipeline.addLast("handler",new RpcClientHandler());
    }
}
