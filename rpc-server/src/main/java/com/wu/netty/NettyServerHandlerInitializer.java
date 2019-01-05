package com.wu.netty;

import com.wu.coder.kryo.KryoCodecUtil;
import com.wu.coder.kryo.KryoDecoder;
import com.wu.coder.kryo.KryoEncoder;
import com.wu.coder.kryo.KryoPoolFactory;
import com.wu.service.RpcServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());
        pipeline.addLast(new KryoEncoder(util));
        pipeline.addLast(new KryoDecoder(util));
        pipeline.addLast("rpcHandler",new RpcServerHandler());
    }
}
