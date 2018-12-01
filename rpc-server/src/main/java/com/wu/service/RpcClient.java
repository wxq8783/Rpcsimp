package com.wu.service;

import com.wu.zookeeper.ZookeeperClient;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RpcClient {

    @Autowired
    ZookeeperClient zookeeperClient;

    public void initNettyClient(String interfaceName){
        zookeeperClient.getPathDetail(interfaceName);
    }

}
