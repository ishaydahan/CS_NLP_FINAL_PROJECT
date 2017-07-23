package studentConsoleApi;

import apiHolder.ApiHolder;
import objects.Person;
import objects.Test;

public class ConsoleStudentMain {
		
	private Person t = null;

	protected ConsoleStudentMain(Person t) {
		this.t=t;
	}

	protected void main() {
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Student Screen Options  @@");
			System.out.println("0 - save and exit");
			System.out.println("2 - show tests");
			System.out.println("3 - select test");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				t.save();
				System.out.println("Bye!");
				return;
			}else if(option.equals("2")) {
				showTests();
			}else if(option.equals("3")) {
				selectTest();
			}else {
				System.out.println(">> Bad Input");
			}
		}
	}
	
	protected void showTests() {
        System.out.println("///////////////////////////////////");
        t.getTests().forEach((t)->{
        	System.out.println("Test: " + t);
        });
        System.out.println("///////////////////////////////////");
	}
	
	protected void selectTest() {
		System.out.println(">> Enter Test id:");
        String id = ApiHolder.scanner.nextLine();
        
        Test t = this.t.getTest(id);
        if (t==null) {
        	System.err.println("there is no such test!");
        	return;
        }
    	ConsoleStudentTest api = new ConsoleStudentTest(t.load());
    	api.main();
	}		
}