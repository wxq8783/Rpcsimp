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

    public void addChannel(String interfaceName , Channel channel){
        channelMap.put(interfaceName,channel);
    }


    public Channel getChannel(String interfaceName){
        return (Channel) channelMap.get(interfaceName);
    }
}
