package com.nlp;

import java.util.ArrayList;

import com.nlp.analyzer.AnswerAnalyzer;
import com.nlp.common.ApiHolder;
import com.nlp.models.Answer;

public class Tests {

	public void test1() {		
		ArrayList<Answer> ver = new ArrayList<Answer>();
		ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
		ArrayList<Answer> badToCheck = new ArrayList<Answer>();

		ver.add(ApiHolder.getInstance().factory.createAnswer("because people want to help" ,100, "TEACHER").build());
		ver.add(ApiHolder.getInstance().factory.createAnswer("people volunteer to help people" ,100, "TEACHER").build());

		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people want to help", 0, "STUDENT"));// exact match
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people love to help", 0, "STUDENT"));// close meaning1
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people choose to help", 0, "STUDENT"));// close meaning2
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people want to halp", 0, "STUDENT"));// spelling
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people wants to help", 0, "STUDENT"));// grammar
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("people want to help", 0, "STUDENT"));//without because
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("they want to help", 0, "STUDENT"));//people > they
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want to help", 0, "STUDENT"));//with "the"
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("thats because people want to help people", 0, "STUDENT"));// extra information
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("want to help", 0, "STUDENT"));//90
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("to help", 0, "STUDENT"));//80
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("to help people", 0, "STUDENT"));//60
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("People choose to volunteer because they want to help.", 0, "STUDENT"));//because in the middle
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("they choose to volunteer to help people.", 0, "STUDENT"));
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("people volunteer to help", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("people choose to volunteer to help people.", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("they love to volunteer", 0, "STUDENT"));//tricky one
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want to fly", 0, "STUDENT"));//bad verb
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the people want money", 0, "STUDENT"));//no verb
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the apes want to help", 0, "STUDENT"));//bad subject
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because he want to help", 0, "STUDENT"));//bad subject+grammer
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because he wants to help", 0, "STUDENT"));//bad subject
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because the people have to help", 0, "STUDENT"));//bad verb
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("people want help", 0, "STUDENT"));//bad meaning
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("they are bored", 0, "STUDENT"));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("they are working for their own free will", 0, "STUDENT"));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("the people need money", 0, "STUDENT"));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("they dont have money", 0, "STUDENT"));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("People choose to volunteer because they want money.", 0, "STUDENT"));
		badToCheck.add(ApiHolder.getInstance().factory.createAnswer("because people need to help", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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
		public  void test2() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world" ,100, "TEACHER").build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is the best", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("they are the best in the world", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is one of the best in the world", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("they are very good", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is very good", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is not good", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system has bad service", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They have only one store", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is slow", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is one of the worst in the world", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("This is a bad company", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They are slow and bad", 0, "STUDENT"));

			
			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}
		public  void test3() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
			ArrayList<Answer> badToCheck = new ArrayList<Answer>();
			
			ver.add(ApiHolder.getInstance().factory.createAnswer("The counselor Helps solve problems between students" ,100, "TEACHER").build());

			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The counselor Helps solve problems between students", 0, "STUDENT"));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor Helps solve problems between students", 0, "STUDENT"));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("he Helps solve problems between students", 0, "STUDENT"));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("Helps solve problems between students", 0, "STUDENT"));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("He is solving problems between students ", 0, "STUDENT"));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor helps solving problems between students", 0, "STUDENT"));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps solving problems between students", 0, "STUDENT"));
			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor deal with conflicts between students", 0, "STUDENT"));

			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He halp the students", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He is solving problms for students", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helping the students", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps the students", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He listens to students", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He solves math problems", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He seats in his office", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps students with homework", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("the councilor does nothing", 0, "STUDENT"));
			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("the councilor works for school", 0, "STUDENT"));

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
		public  void test4() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("Because she used to fight too" ,100, "TEACHER").build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she used to fight too", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is regular to fight", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is regular to fight also", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is also fighting", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is fighting too", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is used to fight too", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is different because she is also a fighter", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is good at fighting", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she likes fighting", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is strong", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is older", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is different because she is like parting", 0, "STUDENT"));
			
			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}
		public  void test5() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("To control her anger" ,100, "TEACHER").build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Controlling anger", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To control the anger", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to relax", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to not get angry", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her first lesson was to control her anger", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To cntrl her angar", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She learned how to not be angry", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To control her dragons", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her first lesson was math", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to be angry", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To listen to other people", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("His first lesson was history", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("to keep her anger", 0, "STUDENT"));

			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}
		public  void test6() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("She sleeps late" ,100, "TEACHER").build());
//			ver.add(ApiHolder.getInstance().factory.createAnswer("she goes to the park" ,100, "TEACHER").build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She sleeps late", 0, "STUDENT"));

			//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She goes to sleep late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She hangs out in the park", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She like to go to the park", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She likes going to sleep late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to sleep late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to the park with her family", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She takes her dog to the park", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She sleep late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She stays up until late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is going to the park on the weekend", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is going to sleep late on the weekend", 0, "STUDENT"));	
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She goes to football match", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She sleeps early", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to sleep early", 0, "STUDENT"));
///			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She doesn’t like sleeping late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat does nothing on the weekend", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is doing homework", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat see television", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("she goes to the stadium", 0, "STUDENT"));

			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}
		public  void test7() {		
			ArrayList<Answer> ver = new ArrayList<Answer>();
			ArrayList<Answer> tocheck = new ArrayList<Answer>();
			ver.add(ApiHolder.getInstance().factory.createAnswer("They were free" ,100, "TEACHER").build());
			ver.add(ApiHolder.getInstance().factory.createAnswer("they didn’t have to pay" ,100, "TEACHER").build());

			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it was free", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because they didn’t have to pay", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it was a gift", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Fred and Jenny were surprised because they didn’t have to pay", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it didn’t cost them money", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips was free", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because they didn’t has to pay ", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They were surprised because they got bad chips", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips were stolen", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips are not for them", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They was surprised because they got a god", 0, "STUDENT"));
			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Fred and Jenny were surprised because they didn’t have any chips", 0, "STUDENT"));

			for (Answer student_ans: tocheck) {
				Integer grade = new AnswerAnalyzer(student_ans.build()).SyntaxAnalyze(ver);
				if (grade==-2) return; //error
				if (grade>-1) {
					System.out.println(grade + " " + student_ans.getContent());
				}
			}
	}

}
