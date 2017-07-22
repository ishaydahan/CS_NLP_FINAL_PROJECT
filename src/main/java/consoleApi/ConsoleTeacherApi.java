package consoleApi;

import apiHolder.ApiHolder;
import objects.Teacher;
import objects.Test;

public class ConsoleTeacherApi {
		
	private Teacher t = null;

	protected ConsoleTeacherApi(Teacher t) {
		this.t=t;
	}

	protected void main() {
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Teacher Screen Options  @@");
			System.out.println("0 - exit");
			System.out.println("1 - create test");
			System.out.println("2 - show tests");
			System.out.println("3 - select test");
			System.out.println("4 - remove test");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				System.out.println("Bye!");
				ApiHolder.scanner.close();
				ApiHolder.client.close();
				return;
			}else if(option.equals("1")) {
				try {createTest();}catch(Exception e) {System.out.println("problem with data");}
			}else if(option.equals("2")) {
				try {showTests();}catch(Exception e) {System.out.println("problem with data");}
			}else if(option.equals("3")) {
				try {selectTest();}catch(Exception e) {System.out.println("problem with data");}
			}else if(option.equals("4")) {
				try {remove();}catch(Exception e) {e.printStackTrace(); System.out.println("problem with data");}
			}else {
				System.out.println(">> Bad Input");
			}
		}
	}
	
	protected void createTest() {
		System.out.println(">> Enter Test:");
        String t = ApiHolder.scanner.nextLine();
        
        this.t.createTest(t);
		System.out.println(">> Done");
	}
		
	protected void showTests() {
        t.getTests().forEach((k,v)->{
        	System.out.println("Test: " + k + ", id: " + v);
        });

	}
	
	protected void selectTest() {
		System.out.println(">> Enter Test id:");
        String id = ApiHolder.scanner.nextLine();
        
        Test t = this.t.getTest(id);
    	ConsoleTestApi api = new ConsoleTestApi(t);
    	api.main();
	}		
	
	protected void remove() {
		System.out.println(">> Enter Test id:");
        String id = ApiHolder.scanner.nextLine();
        t.removeTest(id);
	}	
}