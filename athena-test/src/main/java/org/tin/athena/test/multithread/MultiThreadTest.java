package org.tin.athena.test.multithread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadTest {

    public static void main(String[] args) {
        int count = 10;
        final Lock lock = new ReentrantLock();
        final Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        Thread ta = new Thread(new MyThread(lock, conditionA, conditionB, "A", count));
        Thread tb = new Thread(new MyThread(lock, conditionB, conditionC, "B", count));
        Thread tc = new Thread(new MyThread(lock, conditionC, conditionA, "C", count));

        ta.start();
        tb.start();
        tc.start();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                lock.lock();
                try {
                    System.out.println("开始打印");
                    conditionA.signal();
                }finally {
                    lock.unlock();
                }
            }
        });
        thread.start();

    }

}

class MyThread implements Runnable{
    private Lock lock;
    private Condition startCondition;
    private Condition endCondition;
    private String printChar;
    private int count;

    public MyThread(Lock lock, Condition startCondition, Condition endCondition, String printChar, int count) {
        this.lock = lock;
        this.startCondition = startCondition;
        this.endCondition = endCondition;
        this.printChar = printChar;
        this.count = count;
    }

    public void run() {
        lock.lock();

        try {

            for(int i=0; i<count; i++) {
                doJob();
            }

        }finally {
            lock.unlock();
        }

    }

    private void doJob(){
        try {
            startCondition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print(printChar);

        endCondition.signal();
    }

}
