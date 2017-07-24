package analyzer;

import java.util.List;

import apiHolder.ApiHolder;
import objects.Answer;

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
			if (LevenshteinDistance.computeLevenshteinDistance(teachers_ans.getContent(), students_ans.getContent())<ApiHolder.getInstance().LEVENSHTEIN) {
				ApiHolder.getInstance().logger.println("### LEVENSHTEIN SUCSESS!");
				ApiHolder.getInstance().logger.println("### teacher: " + teachers_ans);
				ApiHolder.getInstance().logger.println("### student: " + students_ans);
				return teachers_ans.getGrade();
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
		Integer maxGrade=ApiHolder.getInstance().MINGRADE;		
		for (Answer teachers_ans: syntaxable) {
			CheckAnswerCase master = new CheckAnswerCase(students_ans, teachers_ans);
			maxGrade= Math.max(maxGrade, master.getGrade());
			if (maxGrade.equals(ApiHolder.getInstance().MAXGRADE)) break;
		}
		ApiHolder.getInstance().logger.println("### SYNTAX ANALYZER RESULT:");
		ApiHolder.getInstance().logger.println("### student: " + students_ans);
		ApiHolder.getInstance().logger.println("### grade: " + maxGrade);
		return maxGrade;		
	}
	
//	/**
//	 * @param TODO
//	 * @return
//	 */
//	public Integer MachineLearningAnalyze(List<Answer> learnable) {
//		//TODO
//		
//		if (learnable.isEmpty()) {
//			System.out.println("There are no teacher's learnable answers at database at all");
//			return -2;
//		}
//				
//		Integer maxGrade=ApiHolder.getInstance().MINGRADE;		
//		for (Answer teachers_ans: learnable) {
//			DataLearner master = new DataLearner();
//			maxGrade= Math.max(maxGrade, master.getGrade());
//			if (maxGrade.equals(ApiHolder.getInstance().MAXGRADE)) break;
//		}
//		ApiHolder.getInstance().logger.println("### Machine Learning RESULT:");
//		ApiHolder.getInstance().logger.println("### student: " + students_ans);
//		ApiHolder.getInstance().logger.println("### grade: " + maxGrade);
//		return maxGrade;		
//	}

}
