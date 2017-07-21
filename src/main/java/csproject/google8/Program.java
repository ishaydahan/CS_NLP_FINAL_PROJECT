package csproject.google8;

public class Program {
	
	public static void main(String[] args) {	
		System.out.print("WELCOME TEACHER!");
		TeacherApi api = new TeacherApi(new Teacher());
		api.main();		
	}	
}