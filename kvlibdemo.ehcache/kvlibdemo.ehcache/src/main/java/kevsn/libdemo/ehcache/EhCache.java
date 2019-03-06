/*
 * 2016年6月20日 
 */
package kevsn.libdemo.ehcache;

import java.util.UUID;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

/**
 * @author Kevin
 *
 */
public class EhCache {

	public static void main(String[] args) {
		testCommonCache();
		// testDiskCache();
	}

	private static void testCommonCache() {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(
								Long.class, String.class,
								ResourcePoolsBuilder.heap(10)))
				.build();

		cacheManager.init();
		Cache<Long, String> preConfigured = cacheManager
				.getCache("preConfigured", Long.class, String.class);
		Cache<Long, String> myCache = cacheManager
				.createCache("myCache",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(
								Long.class, String.class,
								ResourcePoolsBuilder.heap(10)).build());
		for (int i = 0; i < 100; i++) {
			String uuid = UUID.randomUUID().toString();
			myCache.put((long) i, uuid);
		}
		System.out.println(myCache.get(1l));
		System.out.println(myCache.get(50l));
		System.out.println(myCache.get(99l));
		cacheManager.close();
	}

	private static void testDiskCache() {
		PersistentCacheManager persistentCacheManager = CacheManagerBuilder
				.newCacheManagerBuilder()
				.with(CacheManagerBuilder.persistence("test.cache"))
				.withCache("persistent-cache",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(
								Long.class, String.class,
								ResourcePoolsBuilder.newResourcePoolsBuilder()
										.heap(10, EntryUnit.ENTRIES)
										.disk(10, MemoryUnit.MB, true)))
				.build(true);
		Cache<Long, String> pcache = persistentCacheManager
				.getCache("persistent-cache", Long.class, String.class);
		for (int i = 0; i < 100; i++) {
			UUID uuid = UUID.randomUUID();
			pcache.put((long) i, uuid.toString());
		}
		System.out.println(pcache.get(5l));
		System.out.println(pcache.get(50l));
		persistentCacheManager.close();
	}

	private static void testJsr() {}
}
