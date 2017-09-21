package analyzer;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Moshiko
 *
 */
public class checkWords {
	
	static HashSet<StringM> wrong = new HashSet<StringM>();
	static HashSet<StringM> good = new HashSet<StringM>();
	static HashSet<StringM> deletedWrong = new HashSet<StringM>();
	static HashSet<StringM> deletedGood = new HashSet<StringM>();
	
	public static void main(String[] args) {
//		check("Helps solve problems between students",true);
//		check("He is helping the students",true);
//		check("He helps the students",true);
//		check("He halp the students",true);
//		check("He is solving problms for students",true);
//		check("He is solving problems between students",true);
//		check("The councilor helps solving problems between students",true);
//		check("He helps solving problems between students",true);
//		check("The councilor deal with conflicts between students",true);
//	
//		check("He listens to students", false);
//		check("He solves math problems", false);
//		check("He seats in his office", false);
//		check("He helps students with homework", false);
//		check("the councilor does nothing", false);
//		check("the councilor works for school", false);
//
//		System.out.println(checkSentence("He solves math problems"));
//		System.out.println("wrong words - " + wrong);
//		System.out.println("good words - " + good);
	}
	
	
	/**
	 * the functions analyze the sentence and change the hash set of the good/wrong words
	 * @param st the sentence to analyze
	 * @param grade decide if it is good or wrong sentence
	 */
	public static void check(String st, boolean grade) { 
			StringM string = new StringM(st);
			String[] arr = string.underlyingString.split(" ");    
			 for ( String ss : arr) {
				 StringM mm = new StringM(ss);
					if(grade) {
						changeList(wrong, deletedWrong, good, mm);
					} else {
						changeList(good, deletedGood, wrong, mm);
					}
			}
		}

/**
 * the function checks if the word already in @arg1 , if yes add to @arg2 and delete
 * if not, and its in @arg2 don't do nothing,
 * if not, add to @arg3
 * @param arg1 - hashSet which we check against  (good word - check against WrongHashSet)
 * @param arg2 - hashSet that keeping the deleted words from @arg1
 * @param arg3 - hashSet which keep the word
 * @param word - the word to be checked
 */
private static void changeList(HashSet<StringM> arg1, HashSet<StringM> arg2, HashSet<StringM> arg3,
			StringM word) {
	if(arg1.contains(word)) {
		arg2.add(word);
		arg1.remove(word);
	} else if(arg2.contains(word)) {
	} else {
		arg3.add(word);						
	}
}

/**
 * the functions check each word in the @string, 
 * if any of the words appear in the good HashSet - we return null
 * if not, we check if we have "bad" words in the sentence, 
 * if yes, we return false
 * if not, return null
 * @param string - the sentence that we check
 * @return
 */
public static Boolean checkSentence(String string) {
	Iterator<StringM> itr_good = good.iterator();
	Iterator<StringM> itr_wrong = wrong.iterator();
	String[] arr = string.split(" ");
	 for ( String ss : arr) {
		 	itr_good = good.iterator();
			while(itr_good.hasNext()){ 
				int a = LevenshteinDistance.computeLevenshteinDistance(ss, itr_good.next().underlyingString);
				if (a <= 3) { //if the Levenshtein Distance <= 3 that means we have the same meaning
					return null;
				}
			}
	  }
	for ( String ss : arr) {
		itr_wrong = wrong.iterator();
		while(itr_wrong.hasNext()){ 
			int a = LevenshteinDistance.computeLevenshteinDistance(ss, itr_wrong.next().underlyingString);
			if (a <= 3) {
				return false;
			}
		}
	}
	return null;	
}
}
