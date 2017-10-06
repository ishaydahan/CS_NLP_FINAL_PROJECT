package com.nlp.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.nlp.common.ApiHolder;
import com.nlp.models.Answer;

/**
 * @author ishay
 * This class is the first step for syntax check.
 * it should call all the analyzing features.
 * at this point we analyze by {@link LevenshteinDistance} or by syntax {@link CheckAnswerCase}
 */
public class AnswerAnalyzer {
	
	private Answer students_ans;

	public AnswerAnalyzer(Answer students_ans) {
		this.students_ans = students_ans;
	}
		
	/**
	 * @param
	 * @return maximum grade of the analyzing process.
	 * <pre>
	 * there must be at least one answer approved by teacher
	 * </pre>
	 * 
	 */
	public int levenshteinAnalyze(List<Answer> verified) {
		if (verified.isEmpty()) {
			System.out.println("There are no teacher's verified answers at database at all");
			return -2;
		}
		
		//here we check for exact mach with LevenshteinDistance.
		//we can change the parameters to different LevenshteinDistance!=0
		for (Answer teachers_ans: verified) {
			int dist = LevenshteinDistance.computeLevenshteinDistance(teachers_ans.getContent().toLowerCase(), students_ans.getContent().toLowerCase());
			if (dist<ApiHolder.getInstance().LEVENSHTEIN) {
				ApiHolder.getInstance().logger.println("### LEVENSHTEIN SUCSESS!");
				ApiHolder.getInstance().logger.println("### teacher: " + teachers_ans);
				ApiHolder.getInstance().logger.println("### student: " + students_ans);
				if (dist==0) return teachers_ans.getGrade();
				return teachers_ans.getGrade()-ApiHolder.getInstance().REDUCE;
			}
		}
		return -1;//no match		
	}

	/**
	 * @param
	 * @return maximum grade of the analyzing process.
	 * <pre>
	 * there must be at least one answer approved by teacher
	 * </pre>
	 * 
	 */
	public Integer SyntaxAnalyze(List<Answer> syntaxable) {
		if (syntaxable.isEmpty()) {
			System.out.println("There are no teacher's syntaxable answers at database at all");
			return -2;
		}
				
		//here we activate the syntax analyzer for every verified answer and takes the maximum grade.
		List<Integer> exactMatchgrades= new ArrayList<>();	
		List<Integer> partialMatchgrades= new ArrayList<>();	
		Map<Integer, Integer> partialMap = new HashMap<>();
		
		for (Answer teachers_ans: syntaxable) {
			int grade = new CheckAnswerCase(students_ans, teachers_ans).getGrade();
			if (grade==ApiHolder.getInstance().MAXGRADE) exactMatchgrades.add(teachers_ans.getGrade());
			else if (grade!=ApiHolder.getInstance().MINGRADE) {
				partialMatchgrades.add(grade); 
				partialMap.put(grade, teachers_ans.getGrade());
				System.out.println(teachers_ans.getContent() + " " + students_ans.getContent() + " " + grade);
				HashMap<String, Integer> m1 = teachers_ans.getMap();
				m1.put(students_ans.getContent(), grade);
				teachers_ans.setMap(m1);
			}
		}		
		if (exactMatchgrades.stream().count()>0) students_ans.setSyntaxMatchFound(true);
		
		Integer finalGrade = exactMatchgrades.stream().max(Integer::compare).orElse(null);
		
		if (finalGrade!=null) {
			ApiHolder.getInstance().logger.println("### SYNTAX ANALYZER RESULT:");
			ApiHolder.getInstance().logger.println("### student: " + students_ans);
			ApiHolder.getInstance().logger.println("### grade: " + finalGrade);
			
			return finalGrade;		
		}else {
			finalGrade = partialMatchgrades.stream().max(Integer::compare).orElse(null);		
			finalGrade = finalGrade!=null
					? (int)(((double)finalGrade/ApiHolder.getInstance().MAXGRADE)*partialMap.get(finalGrade))
					: ApiHolder.getInstance().MINGRADE;
	
			ApiHolder.getInstance().logger.println("### SYNTAX ANALYZER RESULT:");
			ApiHolder.getInstance().logger.println("### student: " + students_ans);
			ApiHolder.getInstance().logger.println("### grade: " + finalGrade);
			return finalGrade;	
		}
	}
	
	public Integer WordsAnalyze(List<Answer> verified) {
		if (verified.isEmpty()) {
			System.out.println("There are no teacher's verified answers at database at all");
			return -2;
		}
				
		Integer maxGrade=ApiHolder.getInstance().MINGRADE;		
		
	    HashSet<String> good = new HashSet<String>();
	    HashSet<String> bad = new HashSet<String>();
		
		verified.stream().filter(x->x.getGrade()>=50).forEach(x->{
			Arrays.asList(x.getContent().split(" ")).stream().forEach(y->{
				good.add(y);
			});
		});

		verified.stream().filter(x->x.getGrade()==0).forEach(x->{
			Arrays.asList(x.getContent().split(" ")).stream().forEach(y->{
				
				if (!good.contains(y)) {
					bad.add(y);
				}
			});
		});

		if (Arrays.asList(students_ans.getContent().split(" ")).stream().filter(x->bad.contains(x)).findFirst().orElse(null) ==null ) 	maxGrade=students_ans.getGrade();

		ApiHolder.getInstance().logger.println("### WORDS ANALYZER RESULT:");
		ApiHolder.getInstance().logger.println("### student: " + students_ans);
		ApiHolder.getInstance().logger.println("### grade: " + maxGrade);
		return maxGrade;		
	}


}
