package com.wu.zookeeper;
import com.alibaba.fastjson.JSON;
import com.wu.entity.ZookeeperPathBean;
import java.io.PrintStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.api.ProtectACLCreateModeStatPathAndBytesable;
import org.apache.curator.framework.listen.ListenerContainer;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;

public class ZookeeperRegister
        extends CuratorClient
{
    public ZookeeperRegister()
    {
        try
        {
            pathChildrenWathch(this.curatorClient, "/rpc-simp");
        }
        catch (Exception e)
        {
            System.out.println("1111");
            e.printStackTrace();
        }
    }

    private void pathChildrenWathch(CuratorFramework curatorClient, String watchPath)
    {
        try
        {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorClient, watchPath, true);
            pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener()
            {
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
                        throws Exception
                {
//                    switch ()
//                    {
//                        case 1:
//                            System.out.println("CHILD_ADDED��������" + event.getData().getPath() + "��������" + new String(event
//                                    .getData().getData()) + "��������" + event
//                                    .getData().getStat());
//                            break;
//                        case 2:
//                            System.out.println("CHILD_UPDATED��������" + event.getData().getPath() + "��������" + new String(event
//                                    .getData().getData()) + "��������" + event
//                                    .getData().getStat());
//                            break;
//                        case 3:
//                            System.out.println("CHILD_REMOVED��������" + event.getData().getPath() + "��������" + new String(event
//                                    .getData().getData()) + "��������" + event
//                                    .getData().getStat());
//                            break;
//                    }
                }
            }, executorService);

            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
    }

    public boolean createPath(String path, ZookeeperPathBean bean)
    {
        try
        {
            String fullPath = "/rpc-simp/" + path;
            if (this.curatorClient.checkExists().forPath(fullPath) == null) {
                ((ACLBackgroundPathAndBytesable)this.curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)).forPath(fullPath, JSON.toJSONString(bean).getBytes());
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void curatorClose()
    {
        this.curatorClient.close();
    }
}