package qengine.program;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
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
	static final String dataFile = workingDir + "sample_data.nt";

	// ========================================================================

	/**
	 * Méthode utilisée ici lors du parsing de requête sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query) {
		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());

		System.out.println("first pattern : " + patterns.get(0));

		System.out.println("object of the first pattern : " + patterns.get(0).getObjectVar().getValue());

		System.out.println("variables to project : ");

		// Utilisation d'une classe anonyme
		query.getTupleExpr().visit(new AbstractQueryModelVisitor<RuntimeException>() {

			public void meet(Projection projection) {
				System.out.println(projection.getProjectionElemList().getElements());
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
		// Imprimer le dictionaire
		System.out.println("Dictionary: ");
		rdfHandler.dict.entrySet().forEach(entry -> {
		    System.out.println("key: "+entry.getKey() + " - " + "value: " + entry.getValue());
		});
		
		//Créer un instance de IndexManager
		IndexManager idx = new IndexManager();
		idx.setSPO(rdfHandler.SPO, rdfHandler.dict);
		idx.setSOP(rdfHandler.SOP, rdfHandler.dict);
		idx.setPSO(rdfHandler.PSO, rdfHandler.dict);
		idx.setOPS(rdfHandler.OPS, rdfHandler.dict);
		idx.setPOS(rdfHandler.POS, rdfHandler.dict);
		idx.setOSP(rdfHandler.OSP, rdfHandler.dict);
		//Imprimer les index
		
		System.out.println("Index SPO: ");
		for (int i=0; i< idx.getSPO().size(); i++) {
			System.out.println(idx.getSPO().get(i).toString());
		}
		
		System.out.println("Index SOP: ");
		for (int i=0; i< rdfHandler.SOP.size(); i++) {
			System.out.println(idx.getSOP().get(i).toString());
		}
		
		System.out.println("Index PSO: ");
		for (int i=0; i< idx.getPSO().size(); i++) {
			System.out.println(idx.getPSO().get(i).toString());
		}
		
		System.out.println("Index OPS: ");
		for (int i=0; i< idx.getOPS().size(); i++) {
			System.out.println(idx.getOPS().get(i).toString());
		}
		
		System.out.println("Index POS: ");
		for (int i=0; i< idx.getPOS().size(); i++) {
			System.out.println(idx.getPOS().get(i).toString());
		}
		
		System.out.println("Index OSP: ");
		for (int i=0; i< idx.getOSP().size(); i++) {
			System.out.println(idx.getOSP().get(i).toString());
		}
		
	}
}
