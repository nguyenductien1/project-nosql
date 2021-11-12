package qengine.program;

import java.util.HashMap;
import java.util.Map;


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
	
	
}
