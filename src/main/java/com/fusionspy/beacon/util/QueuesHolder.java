package com.fusionspy.beacon.util;

import com.google.common.collect.MapMaker;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * BlockingQueue Map的持有者.
 *
 * @author qc
 */
@SuppressWarnings("unchecked")
@ManagedResource(objectName = QueuesHolder.QUEUEHOLDER_MBEAN_NAME, description = "Queues Holder Bean")
public class QueuesHolder {
    /**
     * QueueManager注册的名称.
     */
    public static final String QUEUEHOLDER_MBEAN_NAME = "SpringSide:type=QueueManagement,name=queueHolder";

    private static ConcurrentMap<String, BlockingQueue> queueMap = new MapMaker().concurrencyLevel(32).makeMap();//消息队列


    /**
     * 根据queueName获得消息队列的静态函数，
     * 如消息队列还不存在, 会自动进行创建.
     *
     * @param queueSize 创建的队列控制
     */
    public static <T> BlockingQueue<T> getQueue(String queueName, int queueSize) {
        BlockingQueue<T> queue = queueMap.get(queueName);

        if (queue == null) {
            BlockingQueue<T> newQueue = new FixArrayBlockQueue<T>(queueSize);

            //如果之前消息队列还不存在,放入新队列并返回Null.否则返回之前的值.
            queue = queueMap.putIfAbsent(queueName, newQueue);
            if (queue == null) {
                queue = newQueue;
            }
        }
        return queue;
    }

    /**
     * 根据queueName获得消息队列中未处理消息的数量,支持基于JMX查询.
     */
    @ManagedOperation(description = "Get message count in queue")
    @ManagedOperationParameters({@ManagedOperationParameter(name = "queueName", description = "Queue name")})
    public static int getQueueLength(String queueName) {
        return getQueue(queueName, 0).size();
    }

    static class FixArrayBlockQueue<E> extends ArrayBlockingQueue<E> {

        private int oldCapcity;

        public FixArrayBlockQueue(int capacity) {
            super(capacity, true);
            this.oldCapcity = capacity;
        }

        @Override
        public void put(E e) throws InterruptedException {
            for (int i = 0, n = this.size() - oldCapcity; i <= n; i++) {
                super.remove();
            }
            super.put(e);
        }
    }
}