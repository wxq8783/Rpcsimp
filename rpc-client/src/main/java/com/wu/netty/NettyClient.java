package com.wu.netty;

import com.wu.RequestBean;
import io.netty.bootstrap.Bootstrap;

public class NettyClient {
    private RequestBean requestBean;

    public NettyClient(RequestBean requestBean) {
        this.requestBean = requestBean;
    }


    public Object getResult(){
        Bootstrap b = new Bootstrap();

    }
}
