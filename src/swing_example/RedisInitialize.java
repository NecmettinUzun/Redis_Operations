package swing_example;

public class RedisInitialize {

	public static void initializeImsiMsisdn(String IP, String port) {
		System.out.println("IMSI-MSISDN Redis cache initialing...");
		System.out.println(IP + "- " + port);
		
		RedisCache imsiMsisdnRedisCache = new ImsiMsisdnRedisCache(IP, Integer.parseInt(port));
		RedisCacheManager.getInstance().setRedisCache(imsiMsisdnRedisCache);
		
	}
	
	public static void initializeMsisdnImsi(String IP, String port) {
		System.out.println("MSISDN-IMSI Redis cache initialing...");
		System.out.println(IP + "- " + port);
		
		RedisCache msisdnImsiRedisCache = new MsisdnImsiRedisCache(IP, Integer.parseInt(port));
		RedisCacheManager.getInstance().setRedisCache(msisdnImsiRedisCache);
	}
	
}
