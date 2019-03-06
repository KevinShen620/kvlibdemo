/*
 * 2016年6月24日 
 */
package kevsn.libdemo.ehcache;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.jsr107.Eh107Configuration;

/**
 * @author Kevin
 *
 */
public class JSRCache {

	public static void testJsrCache() {
		CachingProvider provider = Caching.getCachingProvider();
		javax.cache.CacheManager cacheManager = provider.getCacheManager();
		MutableConfiguration<Long, String> configuration = new MutableConfiguration<Long, String>();
		configuration.setTypes(Long.class, String.class);
		Cache<Long, String> cache = cacheManager.createCache("someCache",
				configuration);

		@SuppressWarnings("unchecked")
		CompleteConfiguration<Long, String> completeConfiguration = cache
				.getConfiguration(CompleteConfiguration.class);

		@SuppressWarnings("unchecked")
		Eh107Configuration<Long, String> eh107Configuration = cache
				.getConfiguration(Eh107Configuration.class);

		CacheRuntimeConfiguration<Long, String> runtimeConfiguration = eh107Configuration
				.unwrap(CacheRuntimeConfiguration.class);

	}
}
