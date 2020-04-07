package org.tin.athena.test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class WeakReferenceTest {

    public static void main(String[] args) {
//        ReferenceQueue<Object> queue = new ReferenceQueue<>();
//
//        WeakReference<TestObject> weakReference = new WeakReference<>(new TestObject(), queue);
//
//        System.out.println(weakReference.get());

        int size = 64;
        HashMap map = new HashMap(size);
        System.out.println(map.size());
    }

}
