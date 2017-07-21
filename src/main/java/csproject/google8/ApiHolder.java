package csproject.google8;

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
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.textrazor.AnalysisException;
import com.textrazor.TextRazor;
import com.textrazor.annotations.Entailment;
	
public class ApiHolder {
    
	protected static MongoCollection<Document> collection = null;
	protected static MongoClient client = null;
	protected static Scanner scanner = null;      
	protected static HashMap<String, List<String>> spellingList = new HashMap<String, List<String>>();
	protected static JLanguageTool lang = new JLanguageTool(new BritishEnglish());
	protected static HashMap<String, List<Entailment>> entailmentList = new HashMap<String, List<Entailment>>();
	protected static LanguageServiceClient langClient = null;
	protected static PrintStream logger = null;

	static {
		try {
			logger = new PrintStream(new FileOutputStream("log.txt"));
			startGoogle();
			scanner = new Scanner(System.in);  
			Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
			mongoLogger.setLevel(Level.SEVERE);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	protected static void startGoogle() throws Exception {
		 try {
			 langClient = LanguageServiceClient.create();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			langClient.close();
		}
	}
	
	protected static List<String> getSpelling(String s) {
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
	
	protected static List<Entailment> getEntailmentList(String s) {
		if (entailmentList.containsKey(s)) {
			return entailmentList.get(s);
		}

		List<Entailment> lst = new ArrayList<Entailment>();
		try {
			TextRazor[] arr = new TextRazor[4];
//			arr[0] = new TextRazor("9faf2230e35c366e74ea0f1d72cf167697e09ae6488645916d1693ef");//bacoola
//			arr[0] = new TextRazor("bbd736f436e10ef989bb0bd155f251756220de90b8d6498262ea2661");//ishay
			arr[0] = new TextRazor("eb5d5e6284d0ed04a6b7beec96d2805a9bc0f4396cb3fe0c219e5374");//ishaydah
//			arr[0] = new TextRazor("03f996454010ffc4e2696dbc1656e7ff4e105c0e886ad4b4e8d31a89");//cs
//			arr[0].addExtractor("entailments");
//			arr[0].addExtractor("entailments");
			arr[0].addExtractor("entailments");
//			arr[0].addExtractor("entailments");

			try {
				ApiHolder.logger.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ razor api");
				lst = arr[0].analyze(s).getResponse().getEntailments();
			} catch (AnalysisException e) {
				e.printStackTrace();
			}
			entailmentList.put(s, lst);
			
			return lst;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lst;
	}

	protected static MongoCollection<Document> getCollection() {
		try {
			collection.count();
			return collection;
		}catch(Exception e) {
			System.out.println("\nconnecting to database...");

			MongoClientOptions.Builder options = MongoClientOptions.builder()
	                .maxConnectionIdleTime(0)
	                .maxConnectionLifeTime(0)
	                .socketKeepAlive(true);

	        MongoClientURI uri  = new MongoClientURI("mongodb://ishaydah:nlpuser@ds161012.mlab.com:61012/csproject", options); 
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