package qengine.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.rio.helpers.AbstractRDFHandler;



/**
 * Le RDFHandler intervient lors du parsing de données et permet d'appliquer un traitement pour chaque élément lu par le parseur.
 * 
 * <p>
 * Ce qui servira surtout dans le programme est la méthode {@link #handleStatement(Statement)} qui va permettre de traiter chaque triple lu.
 * </p>
 * <p>
 * À adapter/réécrire selon vos traitements.
 * </p>
 */
public final class MainRDFHandler extends AbstractRDFHandler {
	int i = 0;
	Map<Integer,String> dict = new HashMap<>();

	public IndexManager index;
	
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> SPO = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> SOP = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> PSO = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> OPS = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> POS = new HashMap<>();
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> OSP = new HashMap<>();
	
	@Override
	public void handleStatement(Statement st) {
		
		Resource subject = st.getSubject();
		Resource predicate = st.getPredicate();
		Value object =  st.getObject();
		
		tripleToDict(st);
		
		int s = findKey(subject.toString(), this.dict);
		int p = findKey(predicate.toString(), this.dict);
		int o = findKey(object.toString(), this.dict);
		
		indexOrganization(SPO, s, p, o);
		indexOrganization(SOP, s, o, p);
		indexOrganization(PSO, p, s, o);
		indexOrganization(OPS, o, p, s);
		indexOrganization(POS, p, o, s);
		indexOrganization(OSP, o, s, p);
		
		
		
	};
	
	
	//Return dictionary
	public Map<Integer, String> tripleToDict (Statement st){
		
		Resource subject = st.getSubject();
		Resource predicate = st.getPredicate();
		Value object =  st.getObject();
		
		
		if (!dict.containsValue(subject.toString())) {
			dict.put(i, subject.toString());
			i++;
		}
	
		
		if (!dict.containsValue(predicate.toString())) {
			dict.put(i, predicate.toString());
			i++;
		}
	
	
		if (!dict.containsValue(object.toString())) {
			dict.put(i, object.toString());
			i++;
		}

		
		return dict;
		
		
	}
	
	public void indexOrganization(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> hashMapIndex, int mainKey, int subKey, int subSubKey) {
		
		
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