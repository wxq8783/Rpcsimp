package com.wu.netty;

import com.wu.RequestBean;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {
    private RequestBean requestBean;

    public NettyClient(RequestBean requestBean) {
        this.requestBean = requestBean;
    }

    private Bootstrap bootstrap ;

    private EventLoopGroup workGroup;

    private NettyClientHandlerInitializer nettyClientHandlerInitializer;

    public Object getResult(){
        bootstrap = new Bootstrap();
        bootstrap = createGroupNio(bootstrap);
        ChannelFuture future = doConnect(bootstrap);
        future.
    }

    public Bootstrap createGroupNio(Bootstrap bootstrap){
        workGroup = new NioEventLoopGroup();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,2014)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,2000)
                .option(ChannelOption.ALLOCATOR,PooledByteBufAllocator.DEFAULT)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(nettyClientHandlerInitializer);
        return bootstrap;
    }
}
