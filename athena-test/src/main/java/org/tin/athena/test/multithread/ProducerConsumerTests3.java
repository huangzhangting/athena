package org.tin.athena.test.multithread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用阻塞队列
 * */
public class ProducerConsumerTests3 {

    private BlockingQueue<Integer> blockingQueue;

    public ProducerConsumerTests3(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void produce(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            blockingQueue.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("生产者：" + Thread.currentThread().getName() + " count: " + blockingQueue.size());

    }

    public void consume(){
        try {
            blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("消费者：" + Thread.currentThread().getName() + " count: " + blockingQueue.size());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        final ProducerConsumerTests3 tests3 = new ProducerConsumerTests3(new ArrayBlockingQueue<Integer>(10));

        new Thread(new Runnable() {
            public void run() {
                while (true){
                    tests3.produce();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true){
                    tests3.consume();
                }
            }
        }).start();

    }

}
