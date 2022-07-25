package com.connorphee.githubuserinfo.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CacheEvictor {

    private final Logger logger = LoggerFactory.getLogger(CacheEvictor.class);
    private final CacheManager cacheManager;

    @Autowired
    public CacheEvictor(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 600000) // Ten minutes
    public void evictAllCaches() {
        logger.info("Invalidating caches");
        cacheManager.getCacheNames()
                .stream()
                .filter(cacheName -> Objects.nonNull(cacheManager.getCache(cacheName))) // May be redundant
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        logger.info("Invalidating caches completed");
    }
}
