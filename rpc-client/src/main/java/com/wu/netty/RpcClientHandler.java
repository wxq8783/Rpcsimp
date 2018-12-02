package com.wu.netty;

import com.wu.ResponseBean;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<ResponseBean> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseBean responseBean) throws Exception {

    }
}
