package com.connorphee.githubuserinfo.scheduled;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CacheEvictorTest {

    @Test
    public void evictsCaches() {
        List<String> cacheNames = new ArrayList<>();
        cacheNames.add("cache1");
        cacheNames.add("cache2");
        cacheNames.add("cache3");

        CacheManager cacheManager = Mockito.mock(CacheManager.class);
        Mockito.when(cacheManager.getCacheNames()).thenReturn(cacheNames);

        Cache cacheOne = Mockito.mock(Cache.class);
        Cache cacheTwo = Mockito.mock(Cache.class);
        Cache cacheThree = Mockito.mock(Cache.class);

        Mockito.when(cacheManager.getCache("cache1")).thenReturn(cacheOne);
        Mockito.when(cacheManager.getCache("cache2")).thenReturn(cacheTwo);
        Mockito.when(cacheManager.getCache("cache3")).thenReturn(cacheThree);

        CacheEvictor evictor = new CacheEvictor(cacheManager);
        evictor.evictAllCaches();

        Mockito.verify(cacheOne, Mockito.times(1)).clear();
        Mockito.verify(cacheTwo, Mockito.times(1)).clear();
        Mockito.verify(cacheThree, Mockito.times(1)).clear();
    }

    @Test
    public void doesNothingIfNoCachesFound() {
        CacheManager cacheManager = Mockito.mock(CacheManager.class);
        Mockito.when(cacheManager.getCacheNames()).thenReturn(Collections.emptyList());

        CacheEvictor evictor = new CacheEvictor(cacheManager);
        evictor.evictAllCaches();
    }

    @Test
    public void filtersNullCaches() {
        List<String> cacheNames = new ArrayList<>();
        cacheNames.add("cache1");
        cacheNames.add("cache2");
        cacheNames.add("cache3");

        CacheManager cacheManager = Mockito.mock(CacheManager.class);
        Mockito.when(cacheManager.getCacheNames()).thenReturn(cacheNames);

        Cache cacheOne = Mockito.mock(Cache.class);
        Cache cacheThree = Mockito.mock(Cache.class);

        Mockito.when(cacheManager.getCache("cache1")).thenReturn(cacheOne);
        Mockito.when(cacheManager.getCache("cache2")).thenReturn(null);
        Mockito.when(cacheManager.getCache("cache3")).thenReturn(cacheThree);

        CacheEvictor evictor = new CacheEvictor(cacheManager);
        evictor.evictAllCaches();

        Mockito.verify(cacheOne, Mockito.times(1)).clear();
        Mockito.verify(cacheThree, Mockito.times(1)).clear();
    }
}
