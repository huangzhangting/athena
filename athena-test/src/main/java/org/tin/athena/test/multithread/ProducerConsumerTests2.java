package org.tin.athena.test.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用 Lock api, Condition
 * */
public class ProducerConsumerTests2 {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final int MAX_COUNT = 10;
    private int count = 0;

    public void produce(){
        lock.lock();

        try {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(count == MAX_COUNT){
                try {
                    System.out.println("生产者：" + Thread.currentThread().getName() + " 等待");
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count++;
            notEmpty.signal();
            System.out.println("生产者：" + Thread.currentThread().getName() + " count: " + count);

        }finally {
            lock.unlock();
        }
    }

    public void consume(){
        lock.lock();

        try {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(count == 0){
                try {
                    System.out.println("消费者：" + Thread.currentThread().getName() + " 等待");
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count--;
            notFull.signal();
            System.out.println("消费者：" + Thread.currentThread().getName() + " count: " + count);
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        final ProducerConsumerTests2 tests2 = new ProducerConsumerTests2();

        new Thread(new Runnable() {
            public void run() {
                while (true){
                    tests2.produce();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                while (true){
                    tests2.consume();
                }
            }
        }).start();

    }

}
