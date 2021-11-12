package qengine.program;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class IndexManager {
	public List<List<Integer>> SPO = new ArrayList<>();
	public List<List<Integer>> SOP = new ArrayList<>();
	public List<List<Integer>> PSO = new ArrayList<>();
	public List<List<Integer>> OPS = new ArrayList<>();
	public List<List<Integer>> POS = new ArrayList<>();
	public List<List<Integer>> OSP = new ArrayList<>();
	
	
	
	public IndexManager() {
		
	}



	public List<List<Integer>> getSPO() {
		return SPO;
	}



	public void setSPO(List<List<String>> sPO, Map<Integer, String> dict) {
	
		SPO = addToList(sPO, dict);
	}



	public List<List<Integer>> getSOP() {
		return this.SOP;
	}



	public void setSOP(List<List<String>> sOP, Map<Integer, String> dict) {
		
		this.SOP = addToList(sOP, dict);
	}



	public List<List<Integer>> getPSO() {
		return this.PSO;
	}



	public void setPSO(List<List<String>> pSO, Map<Integer, String> dict) {
	
		this.PSO = addToList(pSO, dict);
	}



	public List<List<Integer>> getOPS() {
		return this.OPS;
	}



	public void setOPS(List<List<String>> oPS, Map<Integer, String> dict) {
		
		this.OPS = addToList(oPS, dict);
	}



	public List<List<Integer>> getPOS() {
		return this.POS;
	}



	public void setPOS(List<List<String>> pOS, Map<Integer, String> dict) {
		
		POS = addToList(pOS, dict);
	}



	public List<List<Integer>> getOSP() {
		return OSP;
	}



	public void setOSP(List<List<String>> oSP, Map<Integer, String> dict) {
		
		
		OSP = addToList(oSP, dict);
	}

	public List<List<Integer>> addToList(List<List<String>> idxList, Map<Integer, String> dict) {
		List<List<Integer>> listInteger = new ArrayList<>(); 
		for (int i = 0; i< idxList.size(); i ++) {
			List<Integer> list = new ArrayList<>();
			String k1 = idxList.get(i).get(0);
			list.add(findKey(k1, dict));
			
			String k2 = idxList.get(i).get(1);
			list.add(findKey(k2, dict));
			
			String k3 = idxList.get(i).get(2);
			list.add(findKey(k3, dict));
			
			listInteger.add(list);	
			
		}
		return listInteger;
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
