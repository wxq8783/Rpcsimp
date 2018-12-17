package com.wu.netty;

import com.wu.RequestBean;
import com.wu.ResponseBean;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class NettyClient {
    //private RequestBean requestBean;

    public ConcurrentHashMap<String,CompletableFuture> requestRPCMap = new ConcurrentHashMap<>();

    private String host;
    private Integer port;
    private String interfaceName;

    public NettyClient(String host, Integer port, String interfaceName) {
        this.host = host;
        this.port = port;
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
                .handler(new NettyClientHandlerInitializer(this));
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
                        NettyChannelManager.getInstance().addChannel(channel.remoteAddress().toString(),channel);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Channel getChannel(String address){
        Channel channel = NettyChannelManager.getInstance().getChannel(address);
        if(channel == null){
            this.clientStart("",0);
            return NettyChannelManager.getInstance().getChannel(address);
        }
        return null;
    }


    public CompletableFuture sendRequest(RequestBean requestBean) throws InterruptedException {
        String requestId = requestBean.getRequestId();
        CompletableFuture<ResponseBean> future = new CompletableFuture<>();
        Channel channel = getChannel("");
        final CountDownLatch downLatch = new CountDownLatch(1);
        if(channel == null){
            ResponseBean responseBean = new ResponseBean();
            requestBean.setRequestId(requestId);
            responseBean.setErrorMsg("channel is null");
            future.complete(responseBean);
            future.isDone();
        }else{
            ChannelFuture channelFuture = channel.writeAndFlush(requestBean);
            requestRPCMap.put(requestId,future);
            channelFuture.sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if(future.isSuccess()){
                        downLatch.countDown();
                        return;
                    }
                }
            });
        }
        downLatch.await();
        return future;
    }
}
