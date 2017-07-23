package studentConsoleApi;

import objects.Person;

public class ConsoleStudentProgram {
	
	public static void main(String[] args) {	
		System.out.print("WELCOME STUDENT!");
		ConsoleStudentMain api = new ConsoleStudentMain(new Person());
		api.main();		
	}	
}