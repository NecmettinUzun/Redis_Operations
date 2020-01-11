package swing_example;

public interface RedisCache {

	public String get(String key);
	public void put(String key, String value);
}
