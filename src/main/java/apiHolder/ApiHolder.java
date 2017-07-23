package apiHolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import com.google.cloud.language.v1.LanguageServiceClient;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import com.textrazor.TextRazor;
import com.textrazor.annotations.Entailment;

import objects.Factory;
	
/**
 * @author ishay
 * the {@link ApiHolder} class holds all the APIs in static class.
 * we assume that there is only 0 or 1 instance of this class.
 * this class holds:
 * 1. {@link LanguageServiceClient} google syntax analyzer client.
 * 2. {@link MongoClient} connector + collection getter
 * 3. {@link Scanner} for user input (to be deleted)
 * 4. {@link TextRazor} for contextual meaning analyze.
 * 5. {@link JLanguageTool} for spelling corrections
 * 6. {@link PrintStream} logger
 */
public class ApiHolder {
    
	public static int LEVENSHTEIN = 3;//constant for levenshtein calc
	public static double MEANING = 0;//constant for textrazor calc
	public static int REDUCE = 5;//points for each different
	public static int COMP = 5;//points for each different
	public static int MAXGRADE = 100;//max points per answer
	public static int MINGRADE = 0;//min points per answer

	public static MongoCollection<Document> collection;
	public static MongoClient client;
	
	public static Scanner scanner = new Scanner(System.in);     
	
	public static HashMap<String, List<String>> spellingList = new HashMap<String, List<String>>();
	public static JLanguageTool lang = new JLanguageTool(new BritishEnglish());
	
	public static HashMap<String, List<Entailment>> entailmentList = new HashMap<String, List<Entailment>>();
	
	public static LanguageServiceClient langClient = null;
	
	public static PrintStream logger = null;
	
	public static TextRazor t;
	public static int i =0;
	public static TextRazor[] razor = new TextRazor[4];
	
	public static Factory factory = new Factory();
	
	static {
		try {
			Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
			mongoLogger.setLevel(Level.SEVERE);
						
			logger = new PrintStream(new FileOutputStream("analyze_log.txt"));
			
			langClient = LanguageServiceClient.create();
						
			razor[0] = new TextRazor("9faf2230e35c366e74ea0f1d72cf167697e09ae6488645916d1693ef");//bacoola
			razor[1] = new TextRazor("bbd736f436e10ef989bb0bd155f251756220de90b8d6498262ea2661");//ishay
			razor[2] = new TextRazor("eb5d5e6284d0ed04a6b7beec96d2805a9bc0f4396cb3fe0c219e5374");//ishaydah
			razor[3] = new TextRazor("03f996454010ffc4e2696dbc1656e7ff4e105c0e886ad4b4e8d31a89");//cs
			razor[0].addExtractor("entailments");
			razor[1].addExtractor("entailments");
			razor[2].addExtractor("entailments");
			razor[3].addExtractor("entailments");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	/**
	 * @param s - a word
	 * @return list of spelling suggestion
	 * we keep all spelling suggestion in hashmap to save time.
	 */
	public static List<String> getSpelling(String s) {
		if (spellingList.containsKey(s)) {
			return spellingList.get(s);
		}
		ArrayList<String> lst = new ArrayList<String>();
		try {
			List<RuleMatch> rules = lang.check(s);
			if(!rules.isEmpty()) {
				spellingList.put(s, rules.get(0).getSuggestedReplacements());
				return rules.get(0).getSuggestedReplacements();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	/**
	 * 
	 */
	public static void switchrazor() {
		t = razor[i%4];
		i++;
	}
	/**
	 * @param s a sentence
	 * @return list of contextual meanings
	 * we keep all Entailment in hashmap to save time
	 */
	public static List<Entailment> getEntailmentList(String s) {
		
		if (entailmentList.containsKey(s)) {
			return entailmentList.get(s);
		}
		List<Entailment> lst = new ArrayList<Entailment>();

		try {
			lst = t.analyze(s).getResponse().getEntailments();
		} catch (Exception e) {
			switchrazor();
			try {
				lst = t.analyze(s).getResponse().getEntailments();
			} catch (NetworkException | AnalysisException e1) {

			}
		}
		entailmentList.put(s, lst);
		return lst;
	}

	/**
	 * @return test collection
	 * this function is activated once in a while... please check.
	 */
	public static MongoCollection<Document> getCollection() {
		try {
			collection.count();
			return collection;
		}catch(Exception e) {
			System.out.println("\nconnecting to database...");

	        MongoClientURI uri  = new MongoClientURI("mongodb://ishaydah:nlpuser@ds161012.mlab.com:61012/csproject"); 
	        client = new MongoClient(uri);
	        MongoDatabase db = client.getDatabase(uri.getDatabase());
	        collection = db.getCollection("tests"); 
	        return collection;
		} finally {
//			client.close();
//			scanner.close();
		}
	}

	
	
}