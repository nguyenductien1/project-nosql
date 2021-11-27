package qengine.program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.rdf4j.query.algebra.Projection;
import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.AbstractQueryModelVisitor;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;

/**
 * Programme simple lisant un fichier de requête et un fichier de données.
 * 
 * <p>
 * Les entrées sont données ici de manière statique,
 * à vous de programmer les entrées par passage d'arguments en ligne de commande comme demandé dans l'énoncé.
 * </p>
 * 
 * <p>
 * Le présent programme se contente de vous montrer la voie pour lire les triples et requêtes
 * depuis les fichiers ; ce sera à vous d'adapter/réécrire le code pour finalement utiliser les requêtes et interroger les données.
 * On ne s'attend pas forcémment à ce que vous gardiez la même structure de code, vous pouvez tout réécrire.
 * </p>
 * 
 * @author Olivier Rodriguez <olivier.rodriguez1@umontpellier.fr>
 */
final class Main {
	static final String baseURI = null;

	/**
	 * Votre répertoire de travail où vont se trouver les fichiers à lire
	 */
	static final String workingDir = "data/";

	/**
	 * Fichier contenant les requêtes sparql
	 */
	static final String queryFile = workingDir + "sample_query.queryset";

	/**
	 * Fichier contenant des données rdf
	 */
	static final String dataFile = workingDir + "100k.nt";

	// ========================================================================

	/**
	 * Méthode utilisée ici lors du parsing de requête sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());

		//System.out.println("first pattern : " + patterns.get(0));

		//System.out.println("object of the first pattern : " + patterns.get(0).getObjectVar().getValue());

		//System.out.println("variables to project : ");

		// Utilisation d'une classe anonyme
		query.getTupleExpr().visit(new AbstractQueryModelVisitor<RuntimeException>() {

			public void meet(Projection projection) {
				//System.out.println(projection.getProjectionElemList().getElements());
			}
		});
	}

	/**
	 * Entrée du programme
	 */
	public static void main(String[] args) throws Exception {
		parseData();
		parseQueries();
	}

	// ========================================================================

	/**
	 * Traite chaque requête lue dans {@link #queryFile} avec {@link #processAQuery(ParsedQuery)}.
	 */
	private static void parseQueries() throws FileNotFoundException, IOException {
		/**
		 * Try-with-resources
		 * 
		 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">Try-with-resources</a>
		 */
		/*
		 * On utilise un stream pour lire les lignes une par une, sans avoir à toutes les stocker
		 * entièrement dans une collection.
		 */
		try (Stream<String> lineStream = Files.lines(Paths.get(queryFile))) {
			SPARQLParser sparqlParser = new SPARQLParser();
			Iterator<String> lineIterator = lineStream.iterator();
			StringBuilder queryString = new StringBuilder();

			while (lineIterator.hasNext())
			/*
			 * On stocke plusieurs lignes jusqu'à ce que l'une d'entre elles se termine par un '}'
			 * On considère alors que c'est la fin d'une requête
			 */
			{
				String line = lineIterator.next();
				queryString.append(line);

				if (line.trim().endsWith("}")) {
					ParsedQuery query = sparqlParser.parseQuery(queryString.toString(), baseURI);

					processAQuery(query); // Traitement de la requête, à adapter/réécrire pour votre programme

					queryString.setLength(0); // Reset le buffer de la requête en chaine vide
				}
			}
		}
	}

	/**
	 * Traite chaque triple lu dans {@link #dataFile} avec {@link MainRDFHandler}.
	 */
	private static void parseData() throws FileNotFoundException, IOException {
		MainRDFHandler rdfHandler = new MainRDFHandler();
		try (Reader dataReader = new FileReader(dataFile)) {
			// On va parser des données au format ntriples
			RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);

			// On utilise notre implémentation de handler
			rdfParser.setRDFHandler(rdfHandler);
			
			// Parsing et traitement de chaque triple par le handler
			rdfParser.parse(dataReader, baseURI);
			
		}
	
		
		//Créer un instace de Dictionary
		Dictionary instanceDict = new Dictionary();
		instanceDict.setDict(rdfHandler.dict);
		
		// Imprimer le dictionaire
		/*
		System.out.println("Dictionary: ");
		instanceDict.getDict().entrySet().forEach(entry -> {
		    System.out.println("key: "+entry.getKey() + " - " + "value: " + entry.getValue());
		});
		*/
		
		//Créer un instance de IndexManager
		IndexManager idx = new IndexManager();
		idx.setSPO(rdfHandler.SPO);
		idx.setSOP(rdfHandler.SOP);
		idx.setPSO(rdfHandler.PSO);
		idx.setPOS(rdfHandler.POS);
		idx.setOPS(rdfHandler.OPS);
		idx.setOSP(rdfHandler.OSP);

		/**
		 * SELECT ?v0 WHERE {
								?v0 <http://purl.org/dc/terms/Location> <http://db.uwaterloo.ca/~galuc/wsdbm/City2> .
								?v0 <http://schema.org/nationality> <http://db.uwaterloo.ca/~galuc/wsdbm/Country169> .
								?v0 <http://db.uwaterloo.ca/~galuc/wsdbm/gender> <http://db.uwaterloo.ca/~galuc/wsdbm/Gender1> . }
		 */
		
		
		//-> Parton vers integer
		int p1 = instanceDict.findKey("http://purl.org/dc/terms/Location", instanceDict.getDict());
		int o1 = instanceDict.findKey("http://db.uwaterloo.ca/~galuc/wsdbm/City2", instanceDict.getDict());
		int p2 = instanceDict.findKey("http://schema.org/nationality", instanceDict.getDict());
		int o2 = instanceDict.findKey("http://db.uwaterloo.ca/~galuc/wsdbm/Country169", instanceDict.getDict());
		int p3 = instanceDict.findKey("http://db.uwaterloo.ca/~galuc/wsdbm/gender", instanceDict.getDict());
		int o3 = instanceDict.findKey("http://db.uwaterloo.ca/~galuc/wsdbm/Gender1", instanceDict.getDict());
		
		
		ArrayList <Integer> s1 = idx.getOPS().get(o1).get(p1);
		ArrayList <Integer> s2 = idx.getOPS().get(o2).get(p2);
		ArrayList <Integer> s3 = idx.getOPS().get(o3).get(p3);
		
		System.out.println("Result for query: ");
		ArrayList <Integer> queryResult = findCommonElement(s1, s2, s3);
		for (int e:queryResult) {
			System.out.println("-"+instanceDict.getDict().get(e));
		}
		
		
		
		//Imprimer les index
		
		/*
		System.out.println("Index SPO: ");
		idx.getSPO().entrySet().forEach(entry -> {
		    System.out.println("subject: "+entry.getKey() + " -> " + entry.getValue());
		});
		
		//HashSet
		
		
		System.out.println("Index SOP: ");
		idx.getSOP().entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + " -> " + entry.getValue());
		});
		
		System.out.println("Index PSO: ");
		idx.getPSO().entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + " -> " + entry.getValue());
		});
		
		System.out.println("Index OPS: ");
		idx.getOPS().entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + " -> " + entry.getValue());
		});
		
		System.out.println("Index POS: ");
		idx.getPOS().entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + " -> " + entry.getValue());
		});
		
		System.out.println("Index OSP: ");
		idx.getOSP().entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + " -> " + entry.getValue());
		}); */
		
		//Imprimer les query result
		/*System.out.println("SPO QUERY: SELECT ?x WHERE {?x p1 o1 . ?x p2 o2 . ?x p3 o3");
		idx.etoileSPO(instanceDict.getDict());
		System.out.println("POS QUERY: SELECT ?x WHERE {?x o1 s1 . ?x o2 s2 . ?x o3 s3}");
		idx.etoilePOS(instanceDict.getDict());*/
	}
	
	public static ArrayList<Integer> findCommonElement(ArrayList<Integer> a, ArrayList<Integer> b, ArrayList<Integer> c){
		ArrayList<Integer> result = new ArrayList<>();
		Set<Integer> set1 = new HashSet<Integer>();
		Set<Integer> set2 = new HashSet<Integer>();
		for (int e:a) {
			set1.add(e);
		}
		if (b != null ) {
			for (int e:b) {
				if (set1.contains(e)) {
					set2.add(e);
				}
			}
		}
		else {
			set2 = set1;
		}
		
		for (int e:c) {
			if (set2.contains(e)) {
				result.add(e);
			}
		}
		
		return result;
	}
}
