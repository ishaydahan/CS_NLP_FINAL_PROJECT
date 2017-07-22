package studentConsoleApi;

import objects.Teacher;

public class ConsoleStudentProgram {
	
	public static void main(String[] args) {	
		System.out.print("WELCOME STUDENT!");
		ConsoleStudentMain api = new ConsoleStudentMain(new Teacher());
		api.main();		
	}	
}