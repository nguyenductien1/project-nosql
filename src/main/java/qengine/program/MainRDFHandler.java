package qengine.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
	public List<List<String>> SPO = new ArrayList<>();
	public List<List<String>> SOP = new ArrayList<>();
	public List<List<String>> PSO = new ArrayList<>();
	public List<List<String>> OPS = new ArrayList<>();
	public List<List<String>> POS = new ArrayList<>();
	public List<List<String>> OSP = new ArrayList<>();
	
	@Override
	public void handleStatement(Statement st) {
		
		tripleToDict(st);
		origanizationIndex(st);
		
		
		
	};
	
	
	
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
	
	Dictionary instaceDict = new Dictionary();
	
	
	public void origanizationIndex(Statement st) {
		String s = null, p = null, o = null;
		Resource subject = st.getSubject();
		Resource predicate = st.getPredicate();
		Value object =  st.getObject();
		
		s = subject.toString();
		p = predicate.toString();
		o = object.toString();
		
		SPO.add(createList(s, p, o));
		SOP.add(createList(s, o, p));
		PSO.add(createList(p, s, o));
		OPS.add(createList(o, p, s));
		POS.add(createList(p, o, s));
		OSP.add(createList(o, s, p));

	}
	
	
	public List<String> createList ( String a, String b, String c){
		List<String> list = new ArrayList<>();
		list.add(a);
		list.add(b);
		list.add(c);
		return list;
		
	}
	
	
	
	
	
}