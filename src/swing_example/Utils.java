package swing_example;

public class Utils {

	public static  boolean isStringEmpty(String str1, String str2) {
		if(str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
}
