package qengine.program;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.rdf4j.model.Statement;
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
	Map<String, Integer> dict = new HashMap<>();
	@Override
	public void handleStatement(Statement st) {
		if (dict.containsKey(st.getSubject().toString())) {
			System.out.print("contenu déjà existé \n" );
		}
		else {
			dict.put(st.getSubject().toString(), i);
			i++;
		}
		Set<Map.Entry<String, Integer>> objects = dict.entrySet();
		for (Map.Entry<String, Integer> d: objects) {
			String name = d.getKey();
			Integer value = d.getValue();
			System.out.print("name: "+ name+ " value: " + value + "\n");
		}
	};
}