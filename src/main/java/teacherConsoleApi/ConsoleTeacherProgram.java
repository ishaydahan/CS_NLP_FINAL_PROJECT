package teacherConsoleApi;

import java.util.ArrayList;
import objects.Answer;
import objects.Person;
import objects.QuestionFactory;
import objects.Writer;
import syntaxAnalyzer.AnswerAnalyzer;

public class ConsoleTeacherProgram {
	
	public static void main(String[] args) {	
		test();
//		System.out.print("WELCOME TEACHER!");
//		ConsoleTeacherMain api = new ConsoleTeacherMain(new Person());
//		api.main();	
	}	
	
	public static void test() {		
		ArrayList<Answer> ver = new ArrayList<Answer>();
		ArrayList<Answer> tocheck = new ArrayList<Answer>();
		ver.add(new QuestionFactory().create("because people want to help" ,100, Writer.TEACHER).build());
//		ver.add(new QuestionFactory().create("they volunteer to help" ,100, Writer.TEACHER).build());
		
//		tocheck.add(new QuestionFactory().create("because people want to help", 0, Writer.STUDENT));// exact match
//		tocheck.add(new QuestionFactory().create("because people love to help", 0, Writer.STUDENT));// close meaning1
//		tocheck.add(new QuestionFactory().create("because people choose to help", 0, Writer.STUDENT));// close meaning2
//		tocheck.add(new QuestionFactory().create("because people want to halp", 0, Writer.STUDENT));// spelling
//		tocheck.add(new QuestionFactory().create("because people wants to help", 0, Writer.STUDENT));// grammar
//		tocheck.add(new QuestionFactory().create("people want to help", 0, Writer.STUDENT));//without because
//		tocheck.add(new QuestionFactory().create("they want to help", 0, Writer.STUDENT));//people > they
//		tocheck.add(new QuestionFactory().create("because the people want to help", 0, Writer.STUDENT));//with "the"
//		tocheck.add(new QuestionFactory().create("thats because people want to help people", 0, Writer.STUDENT));// extra information
//		tocheck.add(new QuestionFactory().create("want to help", 0, Writer.STUDENT));//90
//		tocheck.add(new QuestionFactory().create("to help", 0, Writer.STUDENT));//80
//		tocheck.add(new QuestionFactory().create("to help people", 0, Writer.STUDENT));//60
//		tocheck.add(new QuestionFactory().create("People choose to volunteer because they want to help.", 0, Writer.STUDENT));//because in the middle
//		tocheck.add(new QuestionFactory().create("they choose to volunteer to help.", 0, Writer.STUDENT));
//		tocheck.add(new QuestionFactory().create("they volunteer to help", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		tocheck.add(new QuestionFactory().create("People volunteer to help.", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		
//		tocheck.add(new QuestionFactory().create("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//	
//		tocheck.add(new QuestionFactory().create("because people need to help", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
//		tocheck.add(new QuestionFactory().create("they love to volunteer", 0, Writer.STUDENT));//tricky one
//		tocheck.add(new QuestionFactory().create("because the people want to fly", 0, Writer.STUDENT));//bad verb
//		tocheck.add(new QuestionFactory().create("because the people want money", 0, Writer.STUDENT));//no verb
//		tocheck.add(new QuestionFactory().create("because the apes want to help", 0, Writer.STUDENT));//bad subject
//		tocheck.add(new QuestionFactory().create("because he want to help", 0, Writer.STUDENT));//bad subject+grammer
//		tocheck.add(new QuestionFactory().create("because he wants to help", 0, Writer.STUDENT));//bad subject
//		tocheck.add(new QuestionFactory().create("because the people have to help", 0, Writer.STUDENT));//bad verb
		tocheck.add(new QuestionFactory().create("people want help", 0, Writer.STUDENT));//bad meaning
//		tocheck.add(new QuestionFactory().create("they are bored", 0, Writer.STUDENT));
//		tocheck.add(new QuestionFactory().create("they are working for their own free will", 0, Writer.STUDENT));
//		tocheck.add(new QuestionFactory().create("the people need money", 0, Writer.STUDENT));
//		tocheck.add(new QuestionFactory().create("they dont have money", 0, Writer.STUDENT));
//		tocheck.add(new QuestionFactory().create("People choose to volunteer because they want money.", 0, Writer.STUDENT));
	
		
		for (Answer student_ans: tocheck) {
			Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
			if (grade==-2) return; //error
			if (grade>-1) {
				System.out.println(grade + " " + student_ans.getContent());
			}
		}
	}

}