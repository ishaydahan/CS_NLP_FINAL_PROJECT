package consoleApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import com.textrazor.TextRazor;
import com.textrazor.annotations.AnalyzedText;
import com.textrazor.annotations.Custom;
import com.textrazor.annotations.Entity;
import com.textrazor.annotations.ScoredCategory;
import com.textrazor.annotations.Sentence;
import com.textrazor.annotations.Topic;
import com.textrazor.annotations.Word;
import com.textrazor.annotations.Word.Sense;
import com.textrazor.annotations.Word.Suggestion;

import analyzer.AnswerAnalyzer;
import apiHolder.ApiHolder;
import objects.Answer;
import objects.Person;
import objects.Writer;

class Connector implements Callable<Boolean> {
    public Boolean call() throws Exception {
        MongoClientURI uri  = new MongoClientURI("mongodb://ishaydah:nlpuser@ds161012.mlab.com:61012/csproject"); 
        ApiHolder.getInstance().client = new MongoClient(uri);
        ApiHolder.getInstance().db = ApiHolder.getInstance().client.getDatabase(uri.getDatabase());
        ApiHolder.getInstance().collection = ApiHolder.getInstance().db.getCollection("tests"); 
        ApiHolder.getInstance().users = ApiHolder.getInstance().db.getCollection("users"); 
        return true;
    }
}
   
class login implements Callable<Boolean> {
    public Boolean call() throws Exception {
		System.out.print("Enter Username:");
		ConsoleProgram.user = ConsoleProgram.scanner.nextLine();
		
		System.out.print("Enter Password:");
		ConsoleProgram.pass = ConsoleProgram.scanner.nextLine();

		return true;
    }
}

public class ConsoleProgram {
	
	public static String user;
	public static String pass;
	public static Scanner scanner = new Scanner(System.in);     

<<<<<<< HEAD
	public static void main(String[] args) {
		ApiHolder.getInstance();

//		test1();
//		test2();
		test3();
=======
	public static void main(String[] args) {	
<<<<<<< HEAD
		test();
=======
//		test();
		test2();
//		test3();
>>>>>>> 74ee32370d46661bd24f6417b950b6d9b6b824d7
//		test4();
//		test5();
//		test6();
//		test7();

//
//        //Get ExecutorService from Executors utility class, thread pool size is 10
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        //Create MyCallable instance
//        Callable<Boolean> Connector = new Connector();
//        Callable<Boolean> login = new login();
//        //submit Callable tasks to be executed by thread pool
//        Future<Boolean> future1 = executor.submit(Connector);
//        Future<Boolean> future2 = executor.submit(login);
//
//        try {
//			future1.get();
//			future2.get();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
//        
//        Person p = new Person();
//		while (!p.login(user, pass)) {
//			System.out.print("Enter Username:");
//			ConsoleProgram.user = ConsoleProgram.scanner.nextLine();
//			
//			System.out.print("Enter Password:");
//			ConsoleProgram.pass = ConsoleProgram.scanner.nextLine();
//		}
//		System.out.print("WELCOME " + p.getName());
//		executor.shutdown();
//		ConsolePerson api = new ConsolePerson(p.load());
//		api.main();	

	}	
	
	public static void test1() {		
		ArrayList<Answer> ver = new ArrayList<Answer>();
		ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
		ArrayList<Answer> badToCheck = new ArrayList<Answer>();

		ver.add(ApiHolder.getInstance().factory.createAnswer("because people want to help" ,100, Writer.TEACHER).build());
		ver.add(ApiHolder.getInstance().factory.createAnswer("people volunteer to help people" ,100, Writer.TEACHER).build());

		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people want to help", 0, Writer.STUDENT));// exact match
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people love to help", 0, Writer.STUDENT));// close meaning1
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people choose to help", 0, Writer.STUDENT));// close meaning2
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people want to halp", 0, Writer.STUDENT));// spelling
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people wants to help", 0, Writer.STUDENT));// grammar
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("people want to help", 0, Writer.STUDENT));//without because
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("they want to help", 0, Writer.STUDENT));//people > they
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want to help", 0, Writer.STUDENT));//with "the"
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("thats because people want to help people", 0, Writer.STUDENT));// extra information
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("want to help", 0, Writer.STUDENT));//90
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("to help", 0, Writer.STUDENT));//80
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("to help people", 0, Writer.STUDENT));//60
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("People choose to volunteer because they want to help.", 0, Writer.STUDENT));//because in the middle
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("they choose to volunteer to help people.", 0, Writer.STUDENT));
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("people volunteer to help", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("people choose to volunteer to help people.", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("they love to volunteer", 0, Writer.STUDENT));//tricky one
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want to fly", 0, Writer.STUDENT));//bad verb
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want money", 0, Writer.STUDENT));//no verb
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the apes want to help", 0, Writer.STUDENT));//bad subject
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because he want to help", 0, Writer.STUDENT));//bad subject+grammer
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because he wants to help", 0, Writer.STUDENT));//bad subject
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the people have to help", 0, Writer.STUDENT));//bad verb
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("people want help", 0, Writer.STUDENT));//bad meaning
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("they are bored", 0, Writer.STUDENT));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("they are working for their own free will", 0, Writer.STUDENT));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("the people need money", 0, Writer.STUDENT));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("they dont have money", 0, Writer.STUDENT));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("People choose to volunteer because they want money.", 0, Writer.STUDENT));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people need to help", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

		double passed = 0;
		double tests = 0;

		for (Answer student_ans: goodToCheck) {
			Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
			tests++;
			if (grade==-2) return; //error
			if (grade>=50) {
				passed++;
				System.out.println("PASSED. grade=" + grade + " ans=" + student_ans.getContent());
			}else {
				System.out.println("FAILED. grade=" + grade + " ans=" + student_ans.getContent());
			}
		}
		System.out.println();
		for (Answer student_ans: badToCheck) {
			Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
			tests++;
			if (grade==-2) return; //error
			if (grade>=50) {
				System.out.println("FAILED. grade=" + grade + " ans=" + student_ans.getContent());
			}else {
				passed++;
				System.out.println("PASSED. grade=" + grade + " ans=" + student_ans.getContent());
			}
		}
		
		System.out.println("TEST 1 HITTING PRECENTAGE: " + (int)((passed/tests)*100));

	}
//		public static void test2() {		
//			ArrayList<Answer> ver = new ArrayList<Answer>();
//			ArrayList<Answer> tocheck = new ArrayList<Answer>();
//			ver.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world" ,100, Writer.TEACHER).build());
//
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is the best", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("they are the best in the world", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is one of the best in the world", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("they are very good", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is very good", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is not good", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system has bad service", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They have only one store", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is slow", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is one of the worst in the world", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("This is a bad company", 0, Writer.STUDENT));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They are slow and bad", 0, Writer.STUDENT));
//
//			
//			for (Answer student_ans: tocheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
//				if (grade==-2) return; //error
//				if (grade>-1) {
//					System.out.println(grade + " " + student_ans.getContent());
//				}
//			}
//	}
		public static void test3() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
<<<<<<< HEAD
			ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
			ArrayList<Answer> badToCheck = new ArrayList<Answer>();
			
			ver.add(ApiHolder.getInstance().factory.createAnswer("The counselor Helps solve problems between students" ,100, Writer.TEACHER).build());
=======
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world" ,100, Writer.TEACHER).build());
>>>>>>> 74ee32370d46661bd24f6417b950b6d9b6b824d7

			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The counselor Helps solve problems between students", 0, Writer.STUDENT));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor Helps solve problems between students", 0, Writer.STUDENT));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("he Helps solve problems between students", 0, Writer.STUDENT));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("Helps solve problems between students", 0, Writer.STUDENT));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("He is solving problems between students ", 0, Writer.STUDENT));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor helps solving problems between students", 0, Writer.STUDENT));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps solving problems between students", 0, Writer.STUDENT));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor deal with conflicts between students", 0, Writer.STUDENT));

			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He halp the students", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He is solving problms for students", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helping the students", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps the students", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He listens to students", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He solves math problems", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He seats in his office", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps students with homework", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("the councilor does nothing", 0, Writer.STUDENT));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("the councilor works for school", 0, Writer.STUDENT));

			double passed = 0;
			double tests = 0;

			for (Answer student_ans: goodToCheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				tests++;
				if (grade==-2) return; //error
				if (grade>=50) {
					passed++;
					System.out.println("PASSED. grade=" + grade + " ans=" + student_ans.getContent());
				}else {
					System.out.println("FAILED. grade=" + grade + " ans=" + student_ans.getContent());
				}
			}
			System.out.println();
			for (Answer student_ans: badToCheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				tests++;
				if (grade==-2) return; //error
				if (grade>=50) {
					System.out.println("PASSED. grade=" + grade + " ans=" + student_ans.getContent());
				}else {
					passed++;
					System.out.println("FAILED. grade=" + grade + " ans=" + student_ans.getContent());
				}
			}
			
			System.out.println("TEST 2 HITTING PRECENTAGE: " + (int)((passed/tests)*100));
			
	}
		public static void test4() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("Because she used to fight too" ,100, Writer.TEACHER).build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she used to fight too", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is regular to fight", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is regular to fight also", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is also fighting", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is fighting too", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is used to fight", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is different because she is also a fighter", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is good at fighting", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she likes fighting", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is strong", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is older", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is different because she is like parting", 0, Writer.STUDENT));
			
			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}
		public static void test5() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("To control her anger" ,100, Writer.TEACHER).build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Controlling anger", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To control the anger", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to relax", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to not get angry", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her first lesson was to control her anger", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To cntrl her angar", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She learned how to not be angry", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To control her dragons", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her first lesson was math", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to be angry", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To listen to other people", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("His first lesson was history", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("to keep her anger", 0, Writer.STUDENT));

			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}
		public static void test6() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("She sleeps late" ,100, Writer.TEACHER).build());
			ver.add(ApiHolder.getInstance().factory.createAnswer("she goes to the park" ,100, Writer.TEACHER).build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She goes to sleep late", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She hangs out in the park", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She like to go to the park", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She likes going to sleep late", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to sleep late", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to the park with her family", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She takes her dog to the park", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She sleep late", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She stays up until late", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is going to the park on the weekend", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is going to sleep late on the weekend", 0, Writer.STUDENT));	
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She goes to football match", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She sleeps early", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to sleep early", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She doesn’t like sleeping late", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat does nothing on the weekend", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is doing homework", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat see television", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("she goes to the stadium", 0, Writer.STUDENT));

			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}
		public static void test7() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("They were free" ,100, Writer.TEACHER).build());
			ver.add(ApiHolder.getInstance().factory.createAnswer("they didn’t have to pay" ,100, Writer.TEACHER).build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it was free", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because they didn’t have to pay", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it was a gift", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Fred and Jenny were surprised because they didn’t have to pay", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it didn’t cost them money", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips was free", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because they didn’t has to pay ", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, Writer.STUDENT));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They were surprised because they got bad chips", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips were stolen", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips are not for them", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They was surprised because they got a god", 0, Writer.STUDENT));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Fred and Jenny were surprised because they didn’t have any chips", 0, Writer.STUDENT));

			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}

}