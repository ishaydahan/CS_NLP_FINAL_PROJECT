package csproject.google8;

import java.util.List;

/**
 * @author ishay
 * This class is the first step for syntax check.
 * it should call all the analyzing features.
 * at this point we analyze by {@link LevenshteinDistance} or by syntax {@link CheckAnswerCase}
 */
public class AnswertAnalyzer {
	
	private Answer students_ans;
	private List<Answer> verified;

	protected AnswertAnalyzer(Answer students_ans, List<Answer> verified) {
		this.students_ans = students_ans;
		this.verified = verified;
	}
				
	/**
	 * @return maximum grade of the analyzing process.
	 * <pre>
	 * there must be at least one answer approved by teacher
	 * </pre>
	 * 
	 */
	protected Integer analyze() {
		if (verified.isEmpty()) {
			System.out.println("There are no teacher's verified answers at database at all");
			return -2;
		}
		
		//here we check for exact mach with LevenshteinDistance.
		//we can change the parameters to different LevenshteinDistance!=0
		for (Answer teachers_ans: verified) {
			if (LevenshteinDistance.computeLevenshteinDistance(teachers_ans.getContent(), students_ans.getContent())==0) {
				ApiHolder.logger.println("### LEVENSHTEIN SUCSESS!");
				ApiHolder.logger.println("### teacher: " + teachers_ans.getContent());
				ApiHolder.logger.println("### student: " + students_ans.getContent());
				students_ans.setGrade(teachers_ans.getGrade());
				return students_ans.getGrade();
			}
		}
		
		//here we activate the syntax analyzer for every verified answer and takes the maximum grade.
		Integer maxGrade=0;		
		for (Answer teachers_ans: verified) {
			CheckAnswerCase master = new CheckAnswerCase(students_ans, teachers_ans);
			maxGrade= Math.max(maxGrade, master.getGrade());
		}
		ApiHolder.logger.println("### SYNTAX ANALYZER RESULT:");
		ApiHolder.logger.println("### student: " + students_ans);
		ApiHolder.logger.println("### grade: " + maxGrade);
		students_ans.setGrade(maxGrade);
		return maxGrade;		
	}
}
