package consoleApi;

import objects.Teacher;

public class ConsoleProgram {
	
	public static void main(String[] args) {	
		System.out.print("WELCOME TEACHER!");
		ConsoleTeacherApi api = new ConsoleTeacherApi(new Teacher());
		api.main();		
	}	
}