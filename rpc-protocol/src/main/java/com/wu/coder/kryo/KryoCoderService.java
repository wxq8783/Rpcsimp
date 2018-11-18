package com.wu.coder.kryo;

import com.esotericsoftware.kryo.Kryo;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.google.common.io.Closer;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KryoCoderService  {



    public void encoder(ByteBuf out, Object message) throws IOException {
        Closer closer = Closer.create();
        try {
            KryoPool pool = KryoPoolFactory.poolFactory.getPool();
            Kryo kryo = pool.borrow();
            ByteArrayOutputStream OutputStream = new ByteArrayOutputStream();
            closer.register(OutputStream);

            Output output = new Output(OutputStream);
            kryo.writeClassAndObject(output,message);

            byte[] body = OutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            output.close();
            OutputStream.close();
            pool.release(kryo);
        } finally {
            closer.close();
        }

    }



    public Object decoder(byte[] body) throws IOException {
        Closer closer = Closer.create();
        try {
            KryoPool pool = KryoPoolFactory.poolFactory.getPool();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
            closer.register(inputStream);
            Kryo kryo = pool.borrow();
            Input input = new Input(inputStream);
            Object object = kryo.readClassAndObject(input);
            input.close();
            pool.release(kryo);
            return object;
        }  finally {
            closer.close();
        }
    }
}
