package qengine.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class IndexManager {
	
	
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> SPO = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> SOP = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> PSO = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> OPS = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> POS = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> OSP = new HashMap<>();
	
	
	
	public IndexManager() {
		
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getSPO() {
		return SPO;
	}



	public void setSPO(List<List<String>> sPO, Map<Integer, String> dict ) {
		SPO = addToList(sPO, dict);
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getSOP() {
		return SOP;
	}




	public void setSOP(List<List<String>> sOP, Map<Integer, String> dict ) {
		SOP = addToList(sOP, dict);
	}




	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getPSO() {
		return PSO;
	}



	public void setPSO(List<List<String>> pSO, Map<Integer, String> dict ) {
		PSO = addToList(pSO, dict);
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getOPS() {
		return OPS;
	}




	public void setOPS(List<List<String>> oPS, Map<Integer, String> dict ) {
		OPS = addToList(oPS, dict);
	}




	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getPOS() {
		return POS;
	}




	public void setPOS(List<List<String>> pOS, Map<Integer, String> dict ) {
		POS = addToList(pOS, dict);
	}




	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getOSP() {
		return OSP;
	}




	public void setOSP(List<List<String>> oSP, Map<Integer, String> dict ) {
		OSP = addToList(oSP, dict);
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> addToList(List<List<String>> idxList, Map<Integer, String> dict) {
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> hashMapIndex = new HashMap<>(); 
		for (int i = 0; i < idxList.size(); i++) {
			/*
			 * In there, each cycle of loop we have 3 values ex: S-P-O or O-P-S or P-S-O, ...
			 * mainKey is the first value ex: S or O or P,...
			 * subKey is the second value ex: P or P or S, ...
			 * subsubKey is the third value ex: O or S or O, ...
			 * */
			int mainKey = findKey(idxList.get(i).get(0), dict);
			int subKey  = findKey(idxList.get(i).get(1), dict);
			int subSubKey = findKey(idxList.get(i).get(2), dict);
			
			//If the mainKey is not in hashMapIndex (SPO index, OPS index, ...) we add new main HashMap<mainKey, HashMap<subkey, [subSubKey]>>
			if (hashMapIndex.get(mainKey) == null) {
				//Create subHashMap to store value subKey and subArray
				HashMap<Integer, ArrayList<Integer>> subHashMap = new HashMap<>(); 
				//Create ArrayList to store value of subSubKey
				ArrayList<Integer> subArray = new ArrayList<>();
				subArray.add(subSubKey);
				subHashMap.put(subKey, subArray);
				hashMapIndex.put(mainKey, subHashMap);
			}
			//If the mainKey in hashMapIndex and the subKey is not in subHashMapIndex we add new <subKey, [subSubKey]> to subHashMap
			else if (hashMapIndex.get(mainKey)!= null && hashMapIndex.get(mainKey).get(subKey)==null) {
				//Create a new subArray of Integer inside subHashMap
				ArrayList<Integer> subArray = new ArrayList<>();
				subArray.add(subSubKey);
				//Add to subHashMap values of subKey and subArray
				hashMapIndex.get(mainKey).put(subKey, subArray);
			}
			//If the main key in hashMapIndex and the subKey in subHashMapIndex we add new value to array of subHashMap
			else if (hashMapIndex.get(mainKey)!= null && hashMapIndex.get(mainKey).get(subKey)!=null) {
				//Add value to subArray
				hashMapIndex.get(mainKey).get(subKey).add(subSubKey);
			}
			
			
		}
		
		
		
		return hashMapIndex;
	}
	

	//This function return the key in dictionary
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
