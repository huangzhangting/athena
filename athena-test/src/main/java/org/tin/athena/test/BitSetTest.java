package org.tin.athena.test;

import java.util.BitSet;

public class BitSetTest {

    public static void test_1(){
        int[] nums = {6, 6, 8, 3, 7, 9, 7};
        int maxNum = 9;

        BitSet bitSet = new BitSet(maxNum);
        System.out.println(bitSet.size());
        for(int i : nums){
            if(bitSet.get(i)){
                System.out.println("is exist：" + i);
            }
            bitSet.set(i);
        }
        System.out.println(bitSet.toString());

        int i = bitSet.nextSetBit(0);
        System.out.println(i);

        System.out.println(bitSet.nextClearBit(3));

    }


    public static void main(String[] args) {
        //test_1();

        System.out.println(1L << 5);

        long l = 1L;

        System.out.println(l & (1L << 64));
    }

}
