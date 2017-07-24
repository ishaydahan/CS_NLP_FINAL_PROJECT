package teacherConsoleApi;

import java.util.List;

import apiHolder.ApiHolder;
import objects.Answer;
import objects.Question;

public class ConsoleQuestion {
	
	protected Question q;

	protected ConsoleQuestion(Question q) {
		this.q=q;
	}
	
	protected void main() {	
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Question Screen Options  @@");
			System.out.println("0 - exit");
			System.out.println("1 - add teacher answer");
			System.out.println("2 - add student answer");
			System.out.println();			
			System.out.println("31 - show TEACHER answers");
			System.out.println("32 - show STUDENT answers");
			System.out.println("33 - show STUDENT ungraded answers");
			System.out.println("34 - show VERIFIED answers");
			System.out.println("35 - show VERIFIED SYNTAXABLE answers");
			System.out.println("36 - show SYNTAXABLE answers");
			System.out.println("39 - show ALL answers");
			System.out.println();			
			System.out.println("41 - check all questions(32) with verified by teacher syntax and levenshtein(34,35)");
			System.out.println("42 - check ungraded questions(33) with verified by teacher syntax and levenshtein(34,35)");
			System.out.println();			
			System.out.println("5 - fix answer");
			System.out.println();			
			System.out.println("61 - remove answer");
			System.out.println("62 - remove all answers");
			System.out.println();			
			System.out.println("71 - approve answer");
			System.out.println("72 - approve all answers");

			option = ApiHolder.getInstance().scanner.nextLine();
			if (option.equals("0")) {
				System.out.println(">> Returning Test Screen");
				return;
			}else if(option.length()>0 && option.charAt(0)=='1') {
				addTeacherAns();
			}else if(option.length()>0 && option.charAt(0)=='2') {
				addStudentAns();
			}else if(option.length()>0 && option.charAt(0)=='3') {
				showAnswers(Integer.valueOf(""+option.charAt(1)));
			}else if(option.length()>0 && option.charAt(0)=='4') {
				checkQuestion(Integer.valueOf(""+option.charAt(1)));
			}else if(option.length()>0 && option.charAt(0)=='5') {
				fixAns();
			}else if(option.length()>0 && option.charAt(0)=='6') {
				remove(Integer.valueOf(""+option.charAt(1)));
			}else if(option.length()>0 && option.charAt(0)=='7') {
				approve(Integer.valueOf(""+option.charAt(1)));
			}else if(option.equals("9")) {
				q.renameQuestion("ss");
			}else {
				System.out.println(">> Bad Input");
			}
		}
	}
	
	protected void addTeacherAns() {
		System.out.println(">> Enter Answer:");
        String ans = ApiHolder.getInstance().scanner.nextLine();

		System.out.println(">> Enter Answer grade:");
        String grade = ApiHolder.getInstance().scanner.nextLine();

        if (q.addTeacherAns(ans, Integer.valueOf(grade))!=null)
        	System.out.println("operation OK!");
	}
		
	protected void addStudentAns() {
		System.out.println(">> Enter Answer:");
        String ans = ApiHolder.getInstance().scanner.nextLine();

        if (q.addStudentAns(ans)!=null)
        	System.out.println("operation OK!");
	}

	protected void showAnswers (int choice) {
        System.out.println("///////////////////////////////////");
		List<Answer> filterdList = null;
		if (choice==1) filterdList = q.getTeacherAnswers();
		if (choice==2) filterdList = q.getStudentAnswers();
		if (choice==3) filterdList = q.getUngradedStudentAnswers();
		if (choice==4) filterdList = q.getVerifiedAnswers();
		if (choice==5) filterdList = q.getVerifiedSyntaxableAnswers();
		if (choice==6) filterdList = q.getSyntaxableAnswers();
		if (choice==9) filterdList = q.getAnswers();
		
		filterdList.sort((x,y) -> y.getGrade()-x.getGrade());
        int i=1;
        for (Answer s : filterdList) {
        	System.out.print(i+ " - ");
        	System.out.println(s);
        	i++;
        }      
        System.out.println("///////////////////////////////////");
	}
	
	protected void fixAns(){
		System.out.println(">> Enter Answer id:");
		String id = ApiHolder.getInstance().scanner.nextLine();
		
		System.out.println(">> Enter Answer grade:");
        String grade = ApiHolder.getInstance().scanner.nextLine();
		
		if (q.fixAns(Integer.parseInt(grade), q.getAnswer(id)))
        	System.out.println("operation OK!");
	}
		
	protected void checkQuestion(int choice) {
        System.out.println("///////////////////////////////////");
		if (choice==1) q.checkQuestion(q.getStudentAnswers(), q.getVerifiedAnswers(), q.getVerifiedSyntaxableAnswers());
		if (choice==2) q.checkQuestion(q.getUngradedStudentAnswers(), q.getVerifiedAnswers(), q.getVerifiedSyntaxableAnswers());
        System.out.println("///////////////////////////////////");
	}
	
	protected void approve(int choice) {
		if (choice==2) {
			if (q.approveAll()) {
	        	System.out.println("operation OK!");
			}
		}
		
		if (choice==1) {
			System.out.println(">> Enter Answer id:");
			String id = ApiHolder.getInstance().scanner.nextLine();
			if (q.approveAnswer(q.getAnswer(id))){
		    	System.out.println("operation OK!");
			}
		}
	}
	
	protected void remove(int choice) {
		if (choice==2) {
			if(q.removeAll()) {
	        	System.out.println("operation OK!");
			}
		}
		
		if (choice==1) {
			System.out.println(">> Enter Answer id:");
			String id = ApiHolder.getInstance().scanner.nextLine();
			if(q.removeAnswer(q.getAnswer(id))) {
	        	System.out.println("operation OK!");
			}
		}	
	}
	
}
