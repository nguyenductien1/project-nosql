package qengine.program;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	@Override
	public void handleStatement(Statement st) {
		
		tripleToDict(st);
		System.out.println("-----------------------------------------");
		
		
	};
	
	public Map<Integer, String> tripleToDict (Statement st){
		
		Resource subject = st.getSubject();
		Resource predicate = st.getPredicate();
		Value object =  st.getObject();
		if (dict.containsValue(subject.toString())) {
			System.out.println("Déjà");
		}
		else {
			dict.put(i, subject.toString());
			i++;
			System.out.println("key: " + i + " value: " + subject.toString());
		}
		
		if (dict.containsValue(predicate.toString())) {
			System.out.println("Déjà");
		}
		else {
			dict.put(i, predicate.toString());
			i++;
			System.out.println("key: " + i + " value: " + predicate.toString());
		}
		
		if (dict.containsValue(object.toString())) {
			System.out.println("Déjà");
		}
		else {
			dict.put(i, object.toString());
			i++;
			System.out.println("key: " + i + " value: " + object.toString());
		}
		
		
		return dict;
		
	}
}