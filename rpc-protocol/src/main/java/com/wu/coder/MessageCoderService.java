package com.wu.coder;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface MessageCoderService {
    /**
     * 编码
     * @param out
     * @param message
     */
    public void encoder(ByteBuf out , Object message) throws IOException;

    /**
     * 解码
     * @param body
     * @return
     */
    public Object decoder(byte[] body) throws IOException;
}
