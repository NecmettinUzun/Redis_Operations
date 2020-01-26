package swing_example;

import com.google.common.primitives.Longs;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ImsiMsisdnRedisCache implements RedisCache {

	private JedisPool jedisPool;

	public ImsiMsisdnRedisCache(String ip, int port) {
		jedisPool = getJedisResource(ip, port);
	}

	@Override
	public String get(String key) {

		Jedis jedis = jedisPool.getResource();
		if (jedis.isConnected()) {
			System.out.println("Jedis connection successfully");

			try {
				byte[] value = jedis.get(Longs.toByteArray(Long.valueOf(key)));

				if (value == null) {
					return null;
				}

				long result = Longs.fromByteArray(value);
				return String.valueOf(result);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void put(String key, String value) {

		Jedis jedis = jedisPool.getResource();
		if (jedis.isConnected()) {
			System.out.println("Jedis connection successfully");

			try {
				long keyLong = Long.valueOf(key);
				long valueLong = Long.valueOf(value);
				
				jedis.getSet(Longs.toByteArray(keyLong), Longs.toByteArray(valueLong));
			} catch (NumberFormatException e) {
				throw e;
			}
		}

	}

	public JedisPool getJedisResource(String host, int port) {

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		int timeout = 1 * 1000; // 1 Second
		jedisPoolConfig.setMaxWaitMillis(timeout);
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setTestOnBorrow(false);

		return new JedisPool(jedisPoolConfig, host, port, timeout);
	}
}
