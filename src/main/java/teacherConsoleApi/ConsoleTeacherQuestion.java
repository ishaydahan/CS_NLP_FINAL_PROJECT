package teacherConsoleApi;

import java.util.List;

import apiHolder.ApiHolder;
import objects.Answer;
import objects.Question;

public class ConsoleTeacherQuestion {
	
	protected Question q;

	protected ConsoleTeacherQuestion(Question q) {
		this.q=q;
	}
	
	protected void main() {	
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Question Screen Options  @@");
			System.out.println("0 - exit");
			System.out.println("1 - add teacher answer");
			System.out.println();			
			System.out.println("31 - show teacher answers");
			System.out.println("32 - show studet answers");
			System.out.println("33 - show student unverified answers");
			System.out.println("34 - show verified answers");
			System.out.println("35 - show ungraded answers");
			System.out.println("36 - show all answers");
			System.out.println();			
			System.out.println("41 - check all questions with verified");
			System.out.println("42 - check ungraded questions with verified");
			System.out.println();			
			System.out.println("5 - fix answer");
			System.out.println();			
			System.out.println("6 - remove answer");
			System.out.println();			
			System.out.println("71 - approve all answers");
			System.out.println("72 - approve answer");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				System.out.println(">> Returning Test Screen");
				return;
			}else if(option.equals("1")) {
				addAns();
			}else if(option.charAt(0)=='3') {
				showAnswers(Integer.valueOf(""+option.charAt(1)));
			}else if(option.charAt(0)=='4') {
				checkQuestion(Integer.valueOf(""+option.charAt(1)));
			}else if(option.equals("5")) {
				fixAns();
			}else if(option.equals("6")) {
				remove();
			}else if(option.charAt(0)=='7') {
				approve(Integer.valueOf(""+option.charAt(1)));
			}else {
				System.out.println(">> Bad Input");
			}
		}
	}
	
	protected void addAns() {
		System.out.println(">> Enter Answer:");
        String teacher_ans = ApiHolder.scanner.nextLine();

		System.out.println(">> Enter Answer grade:");
        String grade = ApiHolder.scanner.nextLine();

        if (q.createAns(teacher_ans, Integer.valueOf(grade))!=null)
        	System.out.println("operation OK!");
	}
		
	protected void showAnswers (int choice) {
        System.out.println("///////////////////////////////////");
		List<Answer> filterdList = null;
		if (choice==1) filterdList = q.getTeacherAnswers();
		if (choice==2) filterdList = q.getStudentAnswers();
		if (choice==3) filterdList = q.getUnverifiedStudentAnswers();
		if (choice==4) filterdList = q.getVerifiedAnswers();
		if (choice==5) filterdList = q.getUngradedStudentAnswers();
		if (choice==6) filterdList = q.getAnswers();
		
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
		String id = ApiHolder.scanner.nextLine();
		
		System.out.println(">> Enter Answer grade:");
        String grade = ApiHolder.scanner.nextLine();
		
        try {
			if (q.fixAns(Integer.parseInt(grade), q.getAnswer(id))) {
	        	System.out.println("operation OK!");
			};
		}catch(Exception e) {
			return;
		}
	}
		
	protected void checkQuestion(int choice) {
        System.out.println("///////////////////////////////////");
		if (choice==1) q.checkQuestion(q.getStudentAnswers(), q.getTeacherAnswers());
		if (choice==1) q.checkQuestion(q.getUngradedStudentAnswers(), q.getTeacherAnswers());
        System.out.println("///////////////////////////////////");
	}
	
	protected void approve(int choice) {
		q.approveAll();
    	System.out.println("operation OK!");
	}
	
	protected void remove() {
		try {
			System.out.println(">> Enter Answer id:");
			String id = ApiHolder.scanner.nextLine();
			q.removeAnswer(q.getAnswer(id));
        	System.out.println("operation OK!");
		}catch(Exception e) {
			return;
		}
	}	
	
}
