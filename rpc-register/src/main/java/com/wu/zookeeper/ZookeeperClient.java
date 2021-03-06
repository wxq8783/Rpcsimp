package com.wu.zookeeper;

import com.wu.entity.RegisterConstant;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.wu.util.CommonConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ZookeeperClient {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);

    public CuratorFramework curatorClient;
    private CountDownLatch downLatch = new CountDownLatch(1);
    public ZookeeperClient() {
        try {
            this.curatorClient = CuratorFrameworkFactory.builder()
                    .connectString("47.98.195.145:2181")
                    .sessionTimeoutMs(RegisterConstant.ZOOKEEPER_SESSION_TIMEOUT.intValue())
                    .retryPolicy(new ExponentialBackoffRetry(1000, 10)).build();
            curatorClient.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void pathChildrenWatch(CuratorFramework curatorClient, String watchPath) {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorClient, watchPath, true);
            pathChildrenCache.getListenable().addListener((client, event) -> {
                switch (event.getType())
                {
                    case CHILD_ADDED:
                        System.out.println("新增节点路径:" + event.getData().getPath() + " 节点内容:" + new String(event
                                .getData().getData()) + "  节点状态:" + event.getData().getStat());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("修改节点路径:" + event.getData().getPath() + " 节点内容:" + new String(event
                                .getData().getData()) + "  节点状态:" + event.getData().getStat());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("移除节点路径:" + event.getData().getPath() + " 节点内容:" + new String(event
                                .getData().getData()) + "  节点状态:" + event.getData().getStat());
                        break;
                }
            }, executorService);
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createPath(String path) {
        try {
            String fullPath = RegisterConstant.ZOOKEEPER_PATH_ROOT ;
            if(path.contains(CommonConstant.PATH_JOIN)){
                String[] arr = path.split("\\"+CommonConstant.PATH_JOIN);
                for(int i = 0;i<arr.length;i++){
                    fullPath = fullPath+"/"+arr[i];
                    if(i < arr.length-1){
                        createPath(fullPath,true);
                    }
                    if(i == (arr.length -1)){
                        createPath(fullPath,false);
                    }
                }
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPath(String path , boolean flag){
        try {
            if (this.curatorClient.checkExists().forPath(path) == null) {
                if(flag){
                    this.curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "11".getBytes());
                    pathChildrenWatch(this.curatorClient, path);
                }else{
                    this.curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "11".getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public List<String> getPathDetail(String interfaceName){
        try {
            List<String> list = curatorClient.getChildren().forPath(RegisterConstant.ZOOKEEPER_PATH_ROOT + "/" + interfaceName);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public void curatorClose()
    {
        this.curatorClient.close();
    }
}