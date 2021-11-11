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
		return SOP;
	}



	public void setSOP(List<List<String>> sOP, Map<Integer, String> dict) {
		
		SOP = addToList(sOP, dict);
	}



	public List<List<Integer>> getPSO() {
		return PSO;
	}



	public void setPSO(List<List<String>> pSO, Map<Integer, String> dict) {
	
		PSO = addToList(pSO, dict);
	}



	public List<List<Integer>> getOPS() {
		return OPS;
	}



	public void setOPS(List<List<String>> oPS, Map<Integer, String> dict) {
		
		OPS = addToList(oPS, dict);
	}



	public List<List<Integer>> getPOS() {
		return POS;
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
			for (Entry<Integer, String> e : dict.entrySet()) {
				
			    if (idxList.get(i).get(0).equals(e.getValue())) {
			    	list.add(e.getKey());
			    }
			    if (idxList.get(i).get(1).equals(e.getValue())) {
			    	list.add(e.getKey());
			    }
			    if (idxList.get(i).get(2).equals(e.getValue())) {
			    	list.add(e.getKey());
			    }
			    
			}
		listInteger.add(list); 
			
		}
		return listInteger;
	}


	
	

}
