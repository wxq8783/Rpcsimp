package com.wu.netty;


import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class NettyChannelManager {

    private static final NettyChannelManager INSTANCE = new NettyChannelManager();

    private NettyChannelManager(){

    }

    public static NettyChannelManager getInstance(){
        return INSTANCE;
    }

    private static final ConcurrentHashMap channelMap =new ConcurrentHashMap<String,Channel>();

    public void addChannel(String address , Channel channel){
        channelMap.put(address,channel);
    }

    public void removeChannel(String address){
        channelMap.remove(address);
    }

    public Channel getChannel(String address){
        return (Channel) channelMap.get(address);
    }



}
