package com.wu.netty;

import com.wu.RequestBean;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyClientService {
    //private RequestBean requestBean;

    private String interfaceName;

    public NettyClientService(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    private EventLoopGroup workGroup;

    public void clientStart(String host , Integer port){
        workGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new NettyClientHandlerInitializer());
        doConnect(b , host, port);

    }

    private void doConnect(Bootstrap bootstrap , String host , Integer port) {
        try {
            bootstrap.connect(host,port).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if(future.isSuccess()){
                        ChannelFuture channelFuture = (ChannelFuture) future;
                        Channel channel = channelFuture.channel();
                        NettyChannelManager channelManager = NettyChannelManager.getInstance();
                        channelManager.addChannel(interfaceName,channel);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
