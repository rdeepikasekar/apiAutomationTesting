package AutomationScript;

import java.util.Map;

public class Script {
	public void Login(Map<String,String> map) {
		System.out.println("login");
		System.out.println(map.get("data1"));
		System.out.println(map.get("data2"));
		System.out.println(map.get("data3"));
	}
	
}
