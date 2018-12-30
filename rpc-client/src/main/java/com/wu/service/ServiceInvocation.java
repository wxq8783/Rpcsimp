package com.wu.service;

import com.wu.netty.NettyClient;
import com.wu.registrybean.RegistryAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceInvocation {

    private String interfaceClassName ;

    private RpcRegisterService rpcRegisterService;

    public ServiceInvocation(String interfaceClassName , RpcRegisterService rpcRegisterService) {
        this.interfaceClassName = interfaceClassName;
        this.rpcRegisterService = rpcRegisterService;
    }

    //发现路径
//
//    public ConcurrentHashMap<String,List<NettyClient>> serverMap = new ConcurrentHashMap<>();
//
//    public List<RegistryAddress> listAddress(String interfaceClassName){
//        return rpcRegisterService.doDiscover(interfaceClassName);
//    }
//
//    public NettyClient getNettyClient(){
//        List<NettyClient> nettyClients = serverMap.get(interfaceClassName);
//        if(!CollectionUtils.isEmpty(nettyClients)){
//            return nettyClients.get(0);
//        }
//        synchronized (this){
//            List<String> addressList = listAddress(interfaceClassName);
//            if(CollectionUtils.isEmpty(addressList)){
//                return null;
//            }
//            List<NettyClient> nettyClientList = new ArrayList<>();
//            for(String address : addressList){
//                nettyClientList.add(new NettyClient("",0,interfaceClassName));
//            }
//            serverMap.put(interfaceClassName,nettyClientList);
//            return nettyClientList.get(0);
//        }
//    }


}
