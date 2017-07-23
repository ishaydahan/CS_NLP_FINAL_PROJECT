package studentConsoleApi;

import apiHolder.ApiHolder;
import objects.Person;
import objects.Test;

public class ConsoleStudentMain {
		
	private Person p = null;

	protected ConsoleStudentMain(Person p) {
		this.p=p;
	}

	protected void main() {
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Student Screen Options  @@");
			System.out.println("0 - exit");
			System.out.println("2 - show tests");
			System.out.println("3 - select test");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
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
        p.getTests().forEach((t)->{
        	System.out.println("Test: " + t);
        });
        System.out.println("///////////////////////////////////");
	}
	
	protected void selectTest() {
		System.out.println(">> Enter Test id:");
        String id = ApiHolder.scanner.nextLine();
        
        Test t = this.p.getTest(id);
        if (t==null) {
        	System.err.println("there is no such test!");
        	return;
        }
    	ConsoleStudentTest api = new ConsoleStudentTest(t.load());
    	api.main();
	}		
}