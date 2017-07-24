package teacherConsoleApi;

import java.util.ArrayList;

import analyzer.AnswerAnalyzer;
import apiHolder.ApiHolder;
import objects.Answer;
import objects.Person;
import objects.Writer;

public class ConsoleProgram {
	
	public static void main(String[] args) {	
//		test();
		ApiHolder a = ApiHolder.getInstance();
		
		System.out.print("Enter Username:");
		String user = a.scanner.nextLine();
		
		System.out.print("Enter Password:");
		String pass = ApiHolder.getInstance().scanner.nextLine();
		
		Person p = new Person();
		
		while (!p.login(user, pass)) 
			System.out.println("wrong user/pass");
		
		System.out.print("WELCOME " + p.getName());
		ConsolePerson api = new ConsolePerson(p.load());
		api.main();	

	}	
	
	public static void test() {		
		ArrayList<Answer> ver = new ArrayList<Answer>();
		ArrayList<Answer> tocheck = new ArrayList<Answer>();
		ver.add(ApiHolder.getInstance().factory.createAnswer("because people want to help" ,100, Writer.TEACHER).build());
		ver.add(ApiHolder.getInstance().factory.createAnswer("they volunteer to help" ,100, Writer.TEACHER).build());
		
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because people want to help", 0, Writer.STUDENT));// exact match
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because people love to help", 0, Writer.STUDENT));// close meaning1
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because people choose to help", 0, Writer.STUDENT));// close meaning2
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because people want to halp", 0, Writer.STUDENT));// spelling
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because people wants to help", 0, Writer.STUDENT));// grammar
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("people want to help", 0, Writer.STUDENT));//without because
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("they want to help", 0, Writer.STUDENT));//people > they
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want to help", 0, Writer.STUDENT));//with "the"
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("thats because people want to help people", 0, Writer.STUDENT));// extra information
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("want to help", 0, Writer.STUDENT));//90
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("to help", 0, Writer.STUDENT));//80
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("to help people", 0, Writer.STUDENT));//60
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("People choose to volunteer because they want to help.", 0, Writer.STUDENT));//because in the middle
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("they choose to volunteer to help.", 0, Writer.STUDENT));
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("they volunteer to help", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("People volunteer to help.", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because people need to help", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("they love to volunteer", 0, Writer.STUDENT));//tricky one
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want to fly", 0, Writer.STUDENT));//bad verb
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want money", 0, Writer.STUDENT));//no verb
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because the apes want to help", 0, Writer.STUDENT));//bad subject
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because he want to help", 0, Writer.STUDENT));//bad subject+grammer
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because he wants to help", 0, Writer.STUDENT));//bad subject
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("because the people have to help", 0, Writer.STUDENT));//bad verb
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("people want help", 0, Writer.STUDENT));//bad meaning
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("they are bored", 0, Writer.STUDENT));
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("they are working for their own free will", 0, Writer.STUDENT));
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("the people need money", 0, Writer.STUDENT));
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("they dont have money", 0, Writer.STUDENT));
		tocheck.add(ApiHolder.getInstance().factory.createAnswer("People choose to volunteer because they want money.", 0, Writer.STUDENT));
	
		
		for (Answer student_ans: tocheck) {
			Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
			if (grade==-2) return; //error
			if (grade>-1) {
				System.out.println(grade + " " + student_ans.getContent());
			}
		}
	}

}