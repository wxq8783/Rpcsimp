package com.wu.test;

import com.wu.service.RpcClientService;

public class ProxyTest {
    public static void main(String[] args) {
        RpcClientService<SampleService> rpcClientService = new RpcClientService<>();
        SampleService sampleService = rpcClientService.createProxy(SampleService.class);
        sampleService.setName("wxq");
    }
}
