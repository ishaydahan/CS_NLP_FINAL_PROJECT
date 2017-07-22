package teacherConsoleApi;

import objects.Teacher;

public class ConsoleTeacherProgram {
	
	public static void main(String[] args) {	
		System.out.print("WELCOME TEACHER!");
		ConsoleTeacherMain api = new ConsoleTeacherMain(new Teacher());
		api.main();		
	}	
}