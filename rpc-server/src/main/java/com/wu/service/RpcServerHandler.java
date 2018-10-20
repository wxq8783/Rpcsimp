package com.wu.service;

import com.wu.RequestBean;
import com.wu.ResponseBean;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.beanutils.MethodUtils;

public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------------netty registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-------------netty unregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-------------------netty active");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("---------------netty inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("----------------netty read");
        RequestBean requestBean = (RequestBean) msg;
        ResponseBean responseBean = new ResponseBean();
        if(requestBean == null){
            responseBean.setErrorMsg("请求过来的requestBean为空");
            ctx.writeAndFlush(responseBean);
            return;
        }
        Object object = MethodUtils.invokeMethod(requestBean.getClassName(),
                requestBean.getMethodName(),
                requestBean.getParameters(),
                requestBean.getParametersType());
        responseBean.setRequestId(requestBean.getRequestId());
        responseBean.setResult(object);
        ctx.writeAndFlush(responseBean);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------------------netty readComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("----------------netty eventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
