package com.wu.service;

import com.wu.netty.NettyClientService;
import com.wu.zookeeper.ZookeeperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RpcClient {

    @Autowired
    ZookeeperClient zookeeperClient;


    public void initNettyClient(String interfaceName){
        String addressInfo = zookeeperClient.getPathDetail(interfaceName);
        if(StringUtils.isEmpty(addressInfo)){
            return;
        }
        String[] addressArr = addressInfo.split(":");
        String host = addressArr[0];
        String port = addressArr[1];
        NettyClientService nettyClientService = new NettyClientService(interfaceName);
        nettyClientService.clientStart(host,Integer.valueOf(port));
    }

}
