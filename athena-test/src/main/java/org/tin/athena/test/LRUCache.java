package org.tin.athena.test;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int CACHE_SIZE;

    public LRUCache(int cacheSize) {
        // 根据传入的容量计算map容量实际，如果不处理，map到达阈值会自动扩容，
        // 那么removeEldestEntry方法的逻辑，实际上要扩容后才能生效
        super((int)Math.ceil(cacheSize/.75f) + 1, .75f, true);
        CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > CACHE_SIZE;
    }

}
