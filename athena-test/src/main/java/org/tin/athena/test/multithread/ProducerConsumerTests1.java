package org.tin.athena.test.multithread;

/**
 * 使用 synchronized，wait / notifyAll
 * */
public class ProducerConsumerTests1 {
    private final Object LOCK = new Object();
    private int count = 0;
    private final int MAX_COUNT = 10;

    public void produce(){
        synchronized(LOCK){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (count == MAX_COUNT) {
                try {
                    System.out.println("生产者：" + Thread.currentThread().getName() + " 等待");
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count++;
            LOCK.notifyAll();

            System.out.println("生产者：" + Thread.currentThread().getName() + " count: " + count);
        }
    }

    public void consume(){
        synchronized(LOCK){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(count == 0){
                try {
                    System.out.println("消费者：" + Thread.currentThread().getName() + " 等待");
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            count--;
            LOCK.notifyAll();

            System.out.println("消费者：" + Thread.currentThread().getName() + " count: " + count);
        }
    }


    public static void main(String[] args) {
        final ProducerConsumerTests1 tests1 = new ProducerConsumerTests1();

        Thread producer = new Thread(new Runnable() {
            public void run() {
                while (true){
                    tests1.produce();
                }
            }
        });
        producer.start();

        Thread consumer = new Thread(new Runnable() {
            public void run() {
                while (true){
                    tests1.consume();
                }
            }
        });
        consumer.start();

    }

}
