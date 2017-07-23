package teacherConsoleApi;

import apiHolder.ApiHolder;
import objects.Person;
import objects.Test;

public class ConsoleTeacherMain {
		
	private Person p = null;

	protected ConsoleTeacherMain(Person p) {
		this.p=p;
	}

	protected void main() {
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Teacher Screen Options  @@");
			System.out.println("0 - save and exit");
			System.out.println("1 - create test");
			System.out.println("2 - show tests");
			System.out.println("3 - select test");
			System.out.println("4 - remove test");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				p.save();
				System.out.println("Bye!");
				return;
			}else if(option.equals("1")) {
				createTest();
			}else if(option.equals("2")) {
				showTests();
			}else if(option.equals("3")) {
				selectTest();
			}else if(option.equals("4")) {
				remove();
			}else {
				System.out.println(">> Bad Input");
			}
		}
	}
	
	protected void createTest() {
		System.out.println(">> Enter Test:");
        String t = ApiHolder.scanner.nextLine();
        
        this.p.createTest(t);
	}
		
	protected void showTests() {
        System.out.println("///////////////////////////////////");
        p.getTests().forEach((t)->{
        	System.out.println(t);
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
    	ConsoleTeacherTest api = new ConsoleTeacherTest(t.load());
    	api.main();
	}		
	
	protected void remove() {
		System.out.println(">> Enter Test id:");
        String id = ApiHolder.scanner.nextLine();
        
        Test t = this.p.getTest(id);
        if (t==null) {
        	System.err.println("there is no such test!");
        	return;
        }
        p.removeTest(t);
	}	
}