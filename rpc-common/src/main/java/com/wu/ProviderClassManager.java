package com.wu;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProviderClassManager {

    private Map<String,Object> providerMap = new ConcurrentHashMap<>();

    private static final ProviderClassManager INSTANCE = new ProviderClassManager();

    private ProviderClassManager(){

    }

    public static ProviderClassManager getInstance(){
        return INSTANCE;
    }


    public void setProviderMap(String interfaceName , Object clazz){
        providerMap.putIfAbsent(interfaceName,clazz);
    }


    public Object getProviderClass(String interfaceName){
        return providerMap.get(interfaceName);
    }

}
