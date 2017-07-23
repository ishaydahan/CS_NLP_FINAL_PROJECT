package teacherConsoleApi;

import apiHolder.ApiHolder;
import objects.Question;
import objects.Test;

public class ConsoleTeacherTest {

	private Test t = null;
	
	public ConsoleTeacherTest(Test t) {
		this.t=t;
	}
	
	public void main() {
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Test Screen Options  @@");
			System.out.println("0 - save and exit");
			System.out.println("1 - create question");
			System.out.println("2 - show questions");
			System.out.println("3 - select question");
			System.out.println("4 - check test");
			System.out.println("5 - remove question");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				t.save();
				System.out.println(">> Returning Teacher Screen");
				return;
			}else if(option.equals("1")) {
				createQuestion();
			}else if(option.equals("2")) {
				showQuestions();
			}else if(option.equals("3")) {
				selectQuestion();
			}else if(option.equals("4")) {
				checkTest();
			}else if(option.equals("5")) {
				remove();
			}else {
				System.out.println(">> Bad Input!");
			}
		}
	}
	
	public void createQuestion() {
		System.out.println(">> Enter Question:");
        String question = ApiHolder.scanner.nextLine();

        t.createQuestion(question);
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
    	ConsoleTeacherQuestion qapi = new ConsoleTeacherQuestion(q.load());
    	qapi.main();
	}

	public void checkTest() {
		t.checkTest();
	}
	
	public void remove() {
		try {
			System.out.println(">> Enter Question id:");
			String id = ApiHolder.scanner.nextLine();
			t.removeQuestion(t.getQuestion(id));
		}catch(Exception e) {
			return;
		}
	}
}
