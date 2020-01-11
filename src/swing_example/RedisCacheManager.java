package swing_example;

public class RedisCacheManager {

	private  RedisCache redisCache;
	private static RedisCacheManager instance = new RedisCacheManager();
	
	public static RedisCacheManager getInstance() {
		return instance;
	}
	
	public void setRedisCache(RedisCache redisCache) {
		this.redisCache = redisCache;
	}
	
	public RedisCache getRedisCache() {
		return redisCache;
	}
}
