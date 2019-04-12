package com.wu.netty;

import com.wu.ResponseBean;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;

public class RpcClientHandler extends SimpleChannelInboundHandler<ResponseBean> {
    private NettyClient nettyClient;

    public RpcClientHandler(NettyClient nettyClient) {
        super();
        this.nettyClient = nettyClient;
    }


    private volatile Channel channel;

    private SocketAddress remotePeer;

    public Channel getChannel() {
        return channel;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        remotePeer = this.channel.remoteAddress();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseBean responseBean) throws Exception {
        String requestId = responseBean.getRequestId();
        CompletableFuture comFuture = nettyClient.requestRPCMap.get(requestId);
        if(comFuture != null ){
            nettyClient.requestRPCMap.remove(requestId);
            comFuture.complete(responseBean);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //logger.error("client caught exception", cause);
        SocketAddress address = ctx.channel().remoteAddress();

        NettyChannelManager.getInstance().removeChannel(address.toString());
        ctx.close();
    }

}
