package com.wu.netty;

import com.wu.handler.MessageDecoderHandler;
import com.wu.handler.MessageEncoderHanlder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyClientHandlerInitializer extends ChannelInitializer<SocketChannel> {
    private NettyClient nettyClient;

    public NettyClientHandlerInitializer(NettyClient NettyClient) {
        this.nettyClient = NettyClient;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("encoder",new MessageEncoderHanlder());
        pipeline.addLast("decoder",new MessageDecoderHandler());
        pipeline.addLast("rpcHandler",new RpcClientHandler(nettyClient));
    }
}
