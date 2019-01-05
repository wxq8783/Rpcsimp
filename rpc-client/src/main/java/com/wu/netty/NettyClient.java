package com.wu.netty;

import com.wu.RequestBean;
import com.wu.ResponseBean;
import com.wu.registrybean.RegistryAddress;
import com.wu.service.RpcRegisterService;
import com.wu.util.SpringBeanUtil;
import io.netty.bootstrap.Bootstrap;
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


    public ConcurrentHashMap<String,CompletableFuture> requestRPCMap = new ConcurrentHashMap<>();

    private RegistryAddress registryAddress;
    private String interfaceName;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public NettyClient( String interfaceName) {
        this.interfaceName = interfaceName;
        getRegistryAddress();
    }

    public void getRegistryAddress(){
        RpcRegisterService rpcRegisterService = SpringBeanUtil.INSTANCE.getBean("rpcRegisterService");
        registryAddress = rpcRegisterService.doDiscover(interfaceName);
    }

    private EventLoopGroup workGroup;

    public void clientStart(){
        workGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new NettyClientHandlerInitializer(this));
        doConnect(b ,registryAddress);

    }

    private void doConnect(Bootstrap bootstrap , RegistryAddress registryAddress) {
        try {
            bootstrap.connect(registryAddress.getHost(), registryAddress.getPort()).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if(future.isSuccess()){
                        ChannelFuture channelFuture = (ChannelFuture) future;
                        Channel channel = channelFuture.channel();
                        NettyChannelManager.getInstance().addChannel(registryAddress.toString(),channel);
                        countDownLatch.countDown();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Channel getChannel(String address) throws InterruptedException {
        Channel channel = NettyChannelManager.getInstance().getChannel(address);
        if(channel == null){
            this.clientStart();
            countDownLatch.await();
            return NettyChannelManager.getInstance().getChannel(address);
        }
        return channel;
    }


    public CompletableFuture sendRequest(RequestBean requestBean) throws InterruptedException {
        String requestId = requestBean.getRequestId();
        final CompletableFuture<ResponseBean> comFuture = new CompletableFuture<>();
        Channel channel = getChannel(registryAddress.toString());
        final CountDownLatch downLatch = new CountDownLatch(1);
        if(channel == null){
            ResponseBean responseBean = new ResponseBean();
            requestBean.setRequestId(requestId);
            responseBean.setErrorMsg("channel is null");
            comFuture.complete(responseBean);
            comFuture.isDone();
        }else{
            ChannelFuture channelFuture = channel.writeAndFlush(requestBean);
            requestRPCMap.put(requestBean.getRequestId(),comFuture);
            channelFuture.sync().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if(future.isSuccess()){
                        downLatch.countDown();
                    }
                }
            });
        }
        downLatch.await();
        return comFuture;
    }
}
