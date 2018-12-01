package com.wu.service;

import com.wu.annotation.RPCService;
import com.wu.util.CommonConstant;
import com.wu.zookeeper.ZookeeperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Map;

@Service("rpcRegisterService")
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
        }
    }


    public void discover(String interfaceName){

    }
}