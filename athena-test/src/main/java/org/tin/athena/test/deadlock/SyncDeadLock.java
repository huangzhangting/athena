package org.tin.athena.test.deadlock;

public class SyncDeadLock {
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void m1(){
        synchronized (lockA){
            System.out.println("m1 get lock a");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("m1 wait lock b");
            synchronized (lockB){
                System.out.println("m1 get lock b");
            }

        }
    }

    public void m2(){
        synchronized (lockB){
            System.out.println("m2 get lock b");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("m2 wait lock a");
            synchronized (lockA){
                System.out.println("m2 get lock a");
            }

        }
    }


    public static void main(String[] args) {
        final SyncDeadLock syncDeadLock = new SyncDeadLock();

        Thread ta = new Thread(new Runnable() {
            public void run() {
                syncDeadLock.m1();
            }
        });

        Thread tb = new Thread(new Runnable() {
            public void run() {
                syncDeadLock.m2();
            }
        });

        ta.start();
        tb.start();

    }

}
