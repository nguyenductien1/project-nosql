package qengine.program;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Dictionary {

	Map<Integer,String> dict = new HashMap<>();
	
	

	public Dictionary() {
		
	}

	public Map<Integer, String> getDict() {
		return dict;
	}

	public void setDict(Map<Integer, String> dict) {
		
		this.dict = dict;
	}
	
	public int findKey(String value, Map<Integer, String> dict) {
		int key = 0;
		for (Entry<Integer, String> e: dict.entrySet()) {
			if (value.equals(e.getValue())) {
				key= e.getKey();
			}
			
		}
		return key;
		
	}
	
}
