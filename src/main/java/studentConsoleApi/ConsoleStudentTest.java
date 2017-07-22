package studentConsoleApi;

import apiHolder.ApiHolder;
import objects.Question;
import objects.Test;

public class ConsoleStudentTest {

	private Test t = null;
	
	public ConsoleStudentTest(Test t) {
		this.t=t;
	}
	
	public void main() {
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Student Screen Options  @@");
			System.out.println("0 - exit");
			System.out.println("2 - show questions");
			System.out.println("3 - select question");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				System.out.println(">> Returning Teacher Screen");
				return;
			}else if(option.equals("2")) {
				showQuestions();
			}else if(option.equals("3")) {
				selectQuestion();
			}else {
				System.out.println(">> Bad Input!");
			}
		}
	}
		
	public void showQuestions() {
        int i=1;
        System.out.println("///////////////////////////////////");
        for (Question s : t.getQuestions()) {
        	System.out.print(i+ " - ");
        	System.out.println(s);
        	i++;
        }
        System.out.println("///////////////////////////////////");
	}
	
	public void selectQuestion() {
		System.out.println(">> Enter Question id:");
        String id = ApiHolder.scanner.nextLine();
        
        Question q = t.getQuestion(id);
        if (q==null) return;
        ConsoleStudentQuestion qapi = new ConsoleStudentQuestion(q);
    	qapi.main();
	}
}
