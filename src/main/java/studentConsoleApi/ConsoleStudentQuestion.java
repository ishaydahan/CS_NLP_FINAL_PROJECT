package studentConsoleApi;

import apiHolder.ApiHolder;
import objects.Question;

public class ConsoleStudentQuestion {
	
	protected Question q;

	protected ConsoleStudentQuestion(Question q) {
		this.q=q;
	}
	
	protected void main() {	
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Student Screen Options  @@");
			System.out.println("0 - exit");
			
			System.out.println("2 - add student answer");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				System.out.println(">> Returning Test Screen");
				return;
			}else if(option.equals("2")) {
				addStudentAns();
			}else {
				System.out.println(">> Bad Input");
			}
		}
	}
		
	protected void addStudentAns(){
		System.out.println(">> Enter Answer:");
        String teacher_ans = ApiHolder.scanner.nextLine();

        if (q.addStudentAns(teacher_ans)!=null)
        	System.out.println("operation OK!");
	}	
}
