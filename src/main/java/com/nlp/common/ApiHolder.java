package com.nlp.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.PartOfSpeech.Tag;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mongodb.MongoClient;
import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import com.textrazor.TextRazor;
import com.textrazor.annotations.Entailment;

	
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
	
	//Singleton field
	private final static ApiHolder INSTANCE = new ApiHolder();
	
	//Constants
	public int LEVENSHTEIN = 3;//constant for levenshtein calc
	public double MEANING = 0;//constant for textrazor calc
	public int REDUCE = 5;//points for each different
	public int COMP = 5;//points for each spelling or meaning correct
	public int MAXGRADE = 100;//max points per answer
	public int MINGRADE = 0;//min points per answer
	
	//Spelling
	public HashMap<String, List<String>> spellingList = new HashMap<String, List<String>>();
	public JLanguageTool lang = new JLanguageTool(new BritishEnglish());
	
	//Entailments
	public HashMap<String, List<Entailment>> entailmentList = new HashMap<String, List<Entailment>>();
	public TextRazor t;
	public int i =0;
	public TextRazor[] razor = new TextRazor[4];

	//Syntax
	public LanguageServiceClient langClient = null;
	public Tag[] del = {Tag.PUNCT, Tag.UNKNOWN, //Enemas for insignificant parts of sentence
			Tag.ADP, 
			Tag.X, Tag.AFFIX, Tag.DET};

	//Logger
	public PrintStream logger = null;
	
	//Factory design pattern
	public Factory factory = new Factory();
	

	// Private constructor suppresses generation of a (public) default constructor
	private ApiHolder() {
		try {
	
			logger = new PrintStream(new FileOutputStream("logs/analyze_log.txt"));
//			PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
//			System.setOut(out);
//			System.setErr(out);

			langClient = LanguageServiceClient.create();
			
			razor[0] = new TextRazor("eb5d5e6284d0ed04a6b7beec96d2805a9bc0f4396cb3fe0c219e5374");//bacoola
			razor[1] = new TextRazor("bbd736f436e10ef989bb0bd155f251756220de90b8d6498262ea2661");//ishay
			razor[2] = new TextRazor("03f996454010ffc4e2696dbc1656e7ff4e105c0e886ad4b4e8d31a89");//ishaydah
			razor[3] = new TextRazor("9faf2230e35c366e74ea0f1d72cf167697e09ae6488645916d1693ef");//cs
			razor[0].addExtractor("entailments");
			razor[1].addExtractor("entailments");
			razor[2].addExtractor("entailments");
			razor[3].addExtractor("entailments");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ApiHolder getInstance() {
		return INSTANCE;
	}


	
	/**
	 * @param s - a word
	 * @return list of spelling suggestion
	 * we keep all spelling suggestion in hashmap to save time.
	 */
	public List<String> getSpelling(String s) {
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
	public void switchrazor() {
		i++;
		t = razor[i%4];
	}
	/**
	 * @param s a sentence
	 * @return list of contextual meanings
	 * we keep all Entailment in hashmap to save time
	 */
	public List<Entailment> getEntailmentList(String s) {
		if (entailmentList.containsKey(s)) {
			return entailmentList.get(s);
		}
		List<Entailment> lst = new ArrayList<Entailment>();

		try {
			t = razor[i%4];
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
	
	public List<String> getSyn(String s) {
		
		try {
			HttpResponse<JsonNode> response = Unirest.get("https://wordsapiv1.p.mashape.com/words/"+s+"/synonyms")
					.header("X-Mashape-Key", "GpWksHEwG3msh2Gv8DGQAojVWiZ7p1vUQm4jsnp0VDuSUhBkL2")
					.header("Accept", "application/json")
					.asJson();
			@SuppressWarnings("unchecked")
			 JSONArray syn = response.getBody().getObject().getJSONArray("synonyms");
			List<String> arr = new ArrayList<String>();
			for(int i=0;i < syn.length();i++){
				arr.add(syn.getString(i));
			}
			return arr;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getUnSyn(String s) {
		
		try {
			HttpResponse<JsonNode> response = Unirest.get("https://wordsapiv1.p.mashape.com/words/"+s+"/antonyms")
					.header("X-Mashape-Key", "GpWksHEwG3msh2Gv8DGQAojVWiZ7p1vUQm4jsnp0VDuSUhBkL2")
					.header("Accept", "application/json")
					.asJson();
			@SuppressWarnings("unchecked")
			 JSONArray syn = response.getBody().getObject().getJSONArray("antonyms");
			List<String> arr = new ArrayList<String>();
			for(int i=0;i < syn.length();i++){
				arr.add(syn.getString(i));
			}
			return arr;
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
}