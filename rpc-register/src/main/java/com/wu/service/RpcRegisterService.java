package com.wu.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Map;

@Service("rpcRegisterService")
public class RpcRegisterService
{
    public void doRegister(Map<String, Object> annotationMap)
    {
        if (CollectionUtils.isEmpty(annotationMap)) {
            return;
        }
        Iterator<Map.Entry<String, Object>> it = annotationMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String, Object> entry = (Map.Entry)it.next();
            String name = (String)entry.getKey();
            Object object = entry.getValue();
            String str1 = object.getClass().getName();
        }
    }
}