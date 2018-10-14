package com.wu.zookeeper;

import com.wu.entity.RegisterConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorClient {
    public CuratorFramework curatorClient;

    public CuratorClient()
    {
        this.curatorClient = CuratorFrameworkFactory.builder().connectString("47.98.195.145:2181").sessionTimeoutMs(RegisterConstant.ZOOKEEPER_SESSION_TIMEOUT.intValue()).retryPolicy(new ExponentialBackoffRetry(1000, 10)).build();
    }
}
