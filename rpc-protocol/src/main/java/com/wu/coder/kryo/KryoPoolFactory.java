package com.wu.coder.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 *
 * https://blog.csdn.net/fanjunjaden/article/details/72823866
 */
public class KryoPoolFactory {

    public static final KryoPoolFactory poolFactory = new KryoPoolFactory();

    private KryoPoolFactory(){ }

    KryoFactory factory = new KryoFactory(){
        @Override
        public Kryo create() {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);
            /**
             * Kryo 首先尝试使用无参构造方法，如果尝试失败，再尝试使用 StdInstantiatorStrategy 作为后备方案，因为后备方案不需要调用任何构造方法。这种策略的配置可以这样表示
             */
            kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            return null;
        }
    };

    private KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

    public KryoPool getPool(){
        return pool;
    }
}
