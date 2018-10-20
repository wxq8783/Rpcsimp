package com.wu.netty;

import com.wu.handler.MessageDecoderHandler;
import com.wu.handler.MessageEncoderHanlder;
import com.wu.service.RpcServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.stereotype.Component;

@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65536,0,4,0,0));
        pipeline.addLast("encoder",new MessageEncoderHanlder());
        pipeline.addLast("decoder",new MessageDecoderHandler());
        pipeline.addLast("rpcHandler",new RpcServerHandler());
    }
}
