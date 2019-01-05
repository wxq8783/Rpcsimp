package com.wu.netty;

import com.wu.coder.kryo.KryoCodecUtil;
import com.wu.coder.kryo.KryoDecoder;
import com.wu.coder.kryo.KryoEncoder;
import com.wu.coder.kryo.KryoPoolFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyClientHandlerInitializer extends ChannelInitializer<SocketChannel> {
    private NettyClient nettyClient;

    public NettyClientHandlerInitializer(NettyClient NettyClient) {
        this.nettyClient = NettyClient;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());
        pipeline.addLast(new KryoEncoder(util));
        pipeline.addLast(new KryoDecoder(util));
        pipeline.addLast("rpcHandler",new RpcClientHandler(nettyClient));
    }
}
