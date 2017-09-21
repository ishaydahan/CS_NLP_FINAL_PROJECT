package analyzer;
import java.util.HashSet;
import java.util.Iterator;

<<<<<<< HEAD
=======
import apiHolder.ApiHolder;

import objects.Writer;

>>>>>>> 44265a1ae2e98363adc8f11d61cfe4a465511d86
public class checkWords {
	
	static HashSet<StringM> wrong = new HashSet<StringM>();
	static HashSet<StringM> good = new HashSet<StringM>();
	static HashSet<StringM> deletedWrong = new HashSet<StringM>();
	static HashSet<StringM> deletedGood = new HashSet<StringM>();

	
	public static void main(String[] args) {
		check("Helps solve problems between students",true);
		check("He is helping the students",true);
		check("He helps the students",true);
		check("He halp the students",true);
		check("He is solving problms for students",true);
		check("He is solving problems between students",true);
		check("The councilor helps solving problems between students",true);
		check("He helps solving problems between students",true);
		check("The councilor deal with conflicts between students",true);
	
		check("He listens to students", false);
		check("He solves math problems", false);
		check("He seats in his office", false);
		check("He helps students with homework", false);
		check("the councilor does nothing", false);
		check("the councilor works for school", false);

		System.out.println(checkWord("He solves math problems"));
		System.out.println("wrong words - " + wrong);
		System.out.println("good words - " + good);
	}
	
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

private static void changeList(HashSet<StringM> arg1, HashSet<StringM> arg2, HashSet<StringM> arg3,
			StringM mm) {
	if(arg1.contains(mm)) {
		arg2.add(mm);
		arg1.remove(mm);
	} else if(arg2.contains(mm)) {
	} else {
		arg3.add(mm);						
	}
}

public static Boolean checkWord(String string) {
	Iterator<StringM> itr_good = good.iterator();
	Iterator<StringM> itr_wrong = wrong.iterator();
	String[] arr = string.split(" ");    
	 for ( String ss : arr) {
		 	itr_good = good.iterator();
			while(itr_good.hasNext()){ 
				int a = LevenshteinDistance.computeLevenshteinDistance(ss, itr_good.next().underlyingString);
				if (a <= 3) {
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
