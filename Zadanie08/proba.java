import java.util.HashMap;
import java.util.Map;

public class proba {
	private static Map<String, Integer> map = new HashMap<>();
	
	public static void main(String[] args) {
		map.put("Jeden", 1);
		String s1 = "Jeden";
		String s2 = "Jeden";
		
		System.out.println(map.get("Jeden") + "," + map.get(s1) + "," + map.get(s2));
	}

}
