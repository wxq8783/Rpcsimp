package com.wu.service;

import com.wu.netty.NettyClient;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceInvocation {

    private String interfaceClassName ;

    public ServiceInvocation(String interfaceClassName) {
        this.interfaceClassName = interfaceClassName;
    }

    //发现路径

    public ConcurrentHashMap<String,List<NettyClient>> serverMap = new ConcurrentHashMap<>();

    public List<String> listAddress(String interfaceClassName){
        return null;
    }

    public NettyClient getNettyClient(){
        List<NettyClient> nettyClients = serverMap.get(interfaceClassName);
        if(!CollectionUtils.isEmpty(nettyClients)){
            return nettyClients.get(0);
        }
        synchronized (this){
            List<String> addressList = listAddress(interfaceClassName);
            if(CollectionUtils.isEmpty(addressList)){
                return null;
            }
            List<NettyClient> nettyClientList = new ArrayList<>();
            for(String address : addressList){
                nettyClientList.add(new NettyClient("",0,interfaceClassName));
            }
            serverMap.put(interfaceClassName,nettyClientList);
            return nettyClientList.get(0);
        }
    }


}
