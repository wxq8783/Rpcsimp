package com.wu.zookeeper;


import com.wu.util.CommonConstant;

public class ZookeeperTest {

    public static void main(String[] args) throws InterruptedException {
        ZookeeperClient register = new ZookeeperClient();
        register.createPath("com.wu.service.DemoService"+CommonConstant.PATH_JOIN +"129.22.34.23:8080");
//        Thread.sleep(5000);
//        register.createPath("com.wu.service.DemoService"+CommonConstant.PATH_JOIN +"333.333.333.333:8080");
    }
}
