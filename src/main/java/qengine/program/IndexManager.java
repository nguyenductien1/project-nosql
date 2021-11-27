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



	public void setSPO(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> sPO) {
		SPO = sPO;
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getSOP() {
		return SOP;
	}



	public void setSOP(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> sOP) {
		SOP = sOP;
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getPSO() {
		return PSO;
	}



	public void setPSO(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> pSO) {
		PSO = pSO;
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getOPS() {
		return OPS;
	}



	public void setOPS(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> oPS) {
		OPS = oPS;
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getPOS() {
		return POS;
	}



	public void setPOS(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> pOS) {
		POS = pOS;
	}



	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getOSP() {
		return OSP;
	}



	public void setOSP(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> oSP) {
		OSP = oSP;
	}



	


	
		

}










