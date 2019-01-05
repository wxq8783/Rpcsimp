package com.wu.service;


import com.wu.ProviderClassManager;
import com.wu.annotation.RPCService;
import com.wu.registrybean.RegistryAddress;
import com.wu.util.CommonConstant;
import com.wu.zookeeper.ZookeeperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class RpcRegisterService {

    @Autowired
    ZookeeperClient zookeeperClient;


    public void doRegister(Map<String, Object> annotationMap , String host , int serverPort) {
        if (CollectionUtils.isEmpty(annotationMap)) {
            return;
        }
        Iterator<Map.Entry<String, Object>> it = annotationMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Object> entry = (Map.Entry)it.next();
            Object object = entry.getValue();
            String interfaceName = object.getClass().getAnnotation(RPCService.class).value().getName();
            String registerPath = interfaceName+CommonConstant.PATH_JOIN+host+":"+serverPort;
            zookeeperClient.createPath(registerPath);
            ProviderClassManager.getInstance().setProviderMap(interfaceName,object);
        }
    }



    public RegistryAddress doDiscover(String interfaceName){
        List<String> addressList =  zookeeperClient.getPathDetail(interfaceName);
        if(CollectionUtils.isEmpty(addressList)){
            return null;
        }
        for(String allAddress : addressList){
            RegistryAddress registryAddress= new RegistryAddress();
            String[] addressArr = allAddress.split("\\:");
            registryAddress.setHost(addressArr[0]);
            registryAddress.setPort(Integer.valueOf(addressArr[1]));
            return registryAddress;
        }
        return null;
    }
}