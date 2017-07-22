package studentConsoleApi;

import apiHolder.ApiHolder;
import objects.Teacher;
import objects.Test;

public class ConsoleStudentMain {
		
	private Teacher t = null;

	protected ConsoleStudentMain(Teacher t) {
		this.t=t;
	}

	protected void main() {
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Teacher Screen Options  @@");
			System.out.println("0 - exit");
			System.out.println("2 - show tests");
			System.out.println("3 - select test");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				System.out.println("Bye!");
				ApiHolder.scanner.close();
				ApiHolder.client.close();
				return;
			}else if(option.equals("2")) {
				try {showTests();}catch(Exception e) {System.out.println("problem with data");}
			}else if(option.equals("3")) {
				try {selectTest();}catch(Exception e) {System.out.println("problem with data");}
			}else {
				System.out.println(">> Bad Input");
			}
		}
	}
	
	protected void showTests() {
        System.out.println("///////////////////////////////////");
        t.getTests().forEach((k,v)->{
        	System.out.println("Test: " + k + ", id: " + v);
        });
        System.out.println("///////////////////////////////////");
	}
	
	protected void selectTest() {
		System.out.println(">> Enter Test id:");
        String id = ApiHolder.scanner.nextLine();
        
        Test t = this.t.getTest(id);
        ConsoleStudentTest api = new ConsoleStudentTest(t);
    	api.main();
	}		
}