package teacherConsoleApi;

import java.util.ArrayList;
import objects.Answer;
import objects.Teacher;
import objects.Writer;
import syntaxAnalyzer.AnswerAnalyzer;

public class ConsoleTeacherProgram {
	
	public static void main(String[] args) {	
//		test();
		System.out.print("WELCOME TEACHER!");
		ConsoleTeacherMain api = new ConsoleTeacherMain(new Teacher());
		api.main();	
	}	
	
	public static void test() {		
		ArrayList<Answer> ver = new ArrayList<Answer>();
		ArrayList<Answer> tocheck = new ArrayList<Answer>();
	
		ver.add(new Answer (null, "because people want to help", Writer.TEACHER, new Integer(100), new Integer(-1), true, true).build());
		ver.add(new Answer (null, "People choose to volunteer to help people.", Writer.TEACHER, new Integer(100), new Integer(-1), true, true).build());
		
		tocheck.add(new Answer (null, "because people want to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));// exact match
		tocheck.add(new Answer (null, "because people love to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));// close meaning1
		tocheck.add(new Answer (null, "because people choose to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));// close meaning2
		tocheck.add(new Answer (null, "because people want to halp", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));// spelling
		tocheck.add(new Answer (null, "because people wants to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));// grammar
		tocheck.add(new Answer (null, "people want to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//without because
		tocheck.add(new Answer (null, "they want to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//people > they
		tocheck.add(new Answer (null, "because the people want to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//with "the"
		tocheck.add(new Answer (null, "thats because people want to help people", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));// extra information
		tocheck.add(new Answer (null, "want to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//90
		tocheck.add(new Answer (null, "to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//80
		tocheck.add(new Answer (null, "to help people", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//60
		tocheck.add(new Answer (null, "People choose to volunteer because they want to help.", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//because in the middle
		tocheck.add(new Answer (null, "they choose to volunteer to help.", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));
		tocheck.add(new Answer (null, "they volunteer to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		tocheck.add(new Answer (null, "People volunteer to help.", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		tocheck.add(new Answer (null, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
		tocheck.add(new Answer (null, "because people need to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		tocheck.add(new Answer (null, "they love to volunteer", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//tricky one
		tocheck.add(new Answer (null, "because the people want to fly", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//bad verb
		tocheck.add(new Answer (null, "because the people want money", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//no verb
		tocheck.add(new Answer (null, "because the apes want to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//bad subject
		tocheck.add(new Answer (null, "because he want to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//bad subject+grammer
		tocheck.add(new Answer (null, "because he wants to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//bad subject
		tocheck.add(new Answer (null, "because the people have to help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//bad verb
		tocheck.add(new Answer (null, "because people want help", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));//bad meaning
		tocheck.add(new Answer (null, "they are bored", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));
		tocheck.add(new Answer (null, "they are working for their own free will", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));
		tocheck.add(new Answer (null, "the people need money", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));
		tocheck.add(new Answer (null, "they dont have money", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));
		tocheck.add(new Answer (null, "People choose to volunteer because they want money.", Writer.STUDENT, new Integer(-1), new Integer(-1), false, false));
	
		
		for (Answer student_ans: tocheck) {
			Integer grade = new AnswerAnalyzer(student_ans.build(), ver).analyze();
			if (grade==-2) return; //error
			if (grade>-1) {
				System.out.println(student_ans);
			}
		}
	}

}