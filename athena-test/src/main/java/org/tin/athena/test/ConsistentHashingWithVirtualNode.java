package org.tin.athena.test;

import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashingWithVirtualNode {

    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.3:111"};

    private static SortedMap<Integer, String> virtualNodes = new TreeMap<Integer, String>();

    private static final int VIRTUAL_COUNT = 5;

    static {
        for(String s : servers){
            for(int i=0; i<VIRTUAL_COUNT; i++){
                String vn = s + "&&VN" + String.valueOf(i);
                virtualNodes.put(getHash(vn), s);
            }
        }
    }

    private static int getHash(String str){
        final int p = 16777619;
        int hash = (int) 2166136261L;

        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;

        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        if (hash < 0)
            hash = Math.abs(hash);

        return hash;
    }

    public static String getServer(String c){
        int hash = getHash(c);
        SortedMap<Integer, String> tailMap = virtualNodes.tailMap(hash);
        if(tailMap.isEmpty()){
            tailMap = virtualNodes;
        }
        Integer firstKey = tailMap.firstKey();
        return tailMap.get(firstKey);
    }


    public static void main(String[] args) {
        String[] clients = {"127.0.0.1:1111"};
        System.out.println(getServer(clients[0]));
    }

}
