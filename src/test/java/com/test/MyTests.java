package com.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.nlp.common.ApiHolder;
import com.nlp.models.Answer;

/**
 * @author isdahan
 * POSSITIVE GRADE:
 * + exact match
 * + very similar match (up to 3 chars) -5 points
 * + similar meaning words -5 points each word
 * + similar lemma of word -5 points each
 * + grammar mistakes -5 points
 * + dropping unimportant sentence parts (the, because) -0 points
 * + using pronouns (they, he) -0 points
 * + extra information -0 points
 * 
 * PARTIAL GRADE:
 * +- partial answer - you will get the partial percent of correct sentence parts
 * 
 * ZERO GRADE:
 * - partial sentence + extra information will cause 0
 * - bad part of sentence will cause 0
 * - opposite meaning word will cause 0
 * - if there is word that exists only in marked 0 sentences, will cause 0
 * - any other option will cause 0
 */
public class MyTests {
	
	ArrayList<Answer> verified = new ArrayList<Answer>();

	private void mainTestCode(String answer, int expected) {
		Answer toCheck = ApiHolder.getInstance().factory.createAnswer(answer, -1, "STUDENT");
    	toCheck.checkAnswer(verified, verified);
        assertEquals(expected, toCheck.getGrade().intValue());
	}

    @Before
    public void setUp() {
    	verified.add(ApiHolder.getInstance().factory.createAnswer("because people want to help" ,100, "TEACHER").build());
    }
	
    @Test
    public void test1() {
    	//exact match
    	mainTestCode("they are wanting to help", 100);
    }

    @Test
    public void test2() {
    	//close meaning
    	mainTestCode("because people wish to help", 95);
    }
	
    @Test
    public void test3() {
    	//close meaning
    	mainTestCode("because people desire to help", 95);
    }
	
    @Test
    public void test4() {
    	//spelling mistake
    	mainTestCode("because peipole want to halp", 95);
    }
	
    @Test
    public void test5() {
    	//Grammar mistake
    	mainTestCode("because people wanting to help", 95);
    }

    @Test
    public void test6() {
    	//without because
    	mainTestCode("people want to help", 100);
    }
	
    @Test
    public void test7() {
    	//people > they
    	mainTestCode("they want to help", 100);
    }
	
    @Test
    public void test8() {
    	//with "the"
    	mainTestCode("because the people want to help", 100);
    }

    @Test
    public void test9() {
    	//extra information
    	mainTestCode("thats because people want to help people", 100);
    }

    @Test
    public void test10() {
    	//partial answer
    	mainTestCode("want to help", 75);
    }
	
    @Test
    public void test11() {
    	//partial answer
    	mainTestCode("To help", 50);
    }
	
    @Test
    public void test12() {
    	//partial answer + extra information = not good
    	mainTestCode("to help people", 0);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @Test
    public void test13() {
    	//bad verb
    	mainTestCode("because people hate to help", 0);
    }

    @Test
    public void test14() {
    	//bad subject
    	mainTestCode("because children want to help", 0);
    }

    @Test
    public void test15() {
    	//bad pronoun
    	mainTestCode("because he want to help", 0);
    }

    @Test
    public void test16() {
    	//very close meaning verb but mistake
    	mainTestCode("because people love to help", 0);
    }
    
    @Test
    public void test17() {
    	//very close meaning verb but mistake
    	mainTestCode("because people have to help", 0);
    }
    
    @Test
    public void test18() {
    	//very close meaning verb but mistake
    	mainTestCode("they wish to halp", 90);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @Test
    public void test19() {
    	verified.add(ApiHolder.getInstance().factory.createAnswer("because people need to help" ,0, "TEACHER").build());
    	mainTestCode("they need to help", 0);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @Test
    public void test20() {
    	verified.add(ApiHolder.getInstance().factory.createAnswer("because people need to help" ,0, "TEACHER").build());
    	mainTestCode("need to help", 0);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

//    
//    	@Test
//		public void test2() {		
//			ArrayList<Answer> ver = new ArrayList<Answer>();
//			ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
//			ArrayList<Answer> badToCheck = new ArrayList<Answer>();
//			
//			ver.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world" ,100, "TEACHER").build());
//
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system is one of the best in the world", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is the best", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("they are the best in the world", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is one of the best in the world", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("they are very good", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("it is very good", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is not good", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("the British postal system has bad service", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They have only one store", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is slow", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is one of the worst in the world", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("This is a bad company", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They are slow and bad", 0, "STUDENT"));
//
//			for (Answer student_ans: goodToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade>0, true);
//			}
//			
//			for (Answer student_ans: badToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade==0, true);
//			}
//    	}
//
//    	@Test
//		public void test3() {		
//			ArrayList<Answer> ver = new ArrayList<Answer>();
//			ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
//			ArrayList<Answer> badToCheck = new ArrayList<Answer>();
//			
//			ver.add(ApiHolder.getInstance().factory.createAnswer("The counselor Helps solve problems between students" ,100, "TEACHER").build());
//
//			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The counselor Helps solve problems between students", 0, "STUDENT"));
//			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor Helps solve problems between students", 0, "STUDENT"));
//			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("he Helps solve problems between students", 0, "STUDENT"));
//			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("Helps solve problems between students", 0, "STUDENT"));
//			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("He is solving problems between students ", 0, "STUDENT"));
//			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor helps solving problems between students", 0, "STUDENT"));
//			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps solving problems between students", 0, "STUDENT"));
//			goodToCheck.add(ApiHolder.getInstance().factory.createAnswer("The councilor deal with conflicts between students", 0, "STUDENT"));
//
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He halp the students", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He is solving problms for students", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helping the students", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps the students", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He listens to students", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He solves math problems", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He seats in his office", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("He helps students with homework", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("the councilor does nothing", 0, "STUDENT"));
//			badToCheck.add(ApiHolder.getInstance().factory.createAnswer("the councilor works for school", 0, "STUDENT"));
//
//			for (Answer student_ans: goodToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade>0, true);
//			}
//			
//			for (Answer student_ans: badToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade==0, true);
//			}		
//    	}
//    	
//    	@Test
//		public void test4() {		
//			ArrayList<Answer> ver = new ArrayList<Answer>();
//			ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
//			ArrayList<Answer> badToCheck = new ArrayList<Answer>();
//			
//			ver.add(ApiHolder.getInstance().factory.createAnswer("Because she used to fight too" ,100, "TEACHER").build());
//
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she used to fight too", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is regular to fight", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is regular to fight also", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is also fighting", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is fighting too", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is used to fight too", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is different because she is also a fighter", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is good at fighting", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she likes fighting", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because she is strong", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her story is different because she is older", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("It is different because she is like parting", 0, "STUDENT"));
//			
//			for (Answer student_ans: goodToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade>0, true);
//			}
//			
//			for (Answer student_ans: badToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade==0, true);
//			}
//    	}
//    	
//    	@Test
//		public void test5() {		
//			ArrayList<Answer> ver = new ArrayList<Answer>();
//			ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
//			ArrayList<Answer> badToCheck = new ArrayList<Answer>();
//			
//			ver.add(ApiHolder.getInstance().factory.createAnswer("To control her anger" ,100, "TEACHER").build());
//
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Controlling anger", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To control the anger", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to relax", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to not get angry", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her first lesson was to control her anger", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To cntrl her angar", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She learned how to not be angry", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To control her dragons", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Her first lesson was math", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("How to be angry", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("To listen to other people", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("His first lesson was history", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("to keep her anger", 0, "STUDENT"));
//
//			for (Answer student_ans: goodToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade>0, true);
//			}
//			
//			for (Answer student_ans: badToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade==0, true);
//			}
//    	}
//    	
//		public void test6() {		
//			ArrayList<Answer> ver = new ArrayList<Answer>();
//			ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
//			ArrayList<Answer> badToCheck = new ArrayList<Answer>();
//			
//			ver.add(ApiHolder.getInstance().factory.createAnswer("She sleeps late" ,100, "TEACHER").build());
//			ver.add(ApiHolder.getInstance().factory.createAnswer("she goes to the park" ,100, "TEACHER").build());
//
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She sleeps late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She goes to sleep late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She hangs out in the park", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She like to go to the park", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She likes going to sleep late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to sleep late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to the park with her family", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She takes her dog to the park", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She sleep late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She stays up until late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is going to the park on the weekend", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is going to sleep late on the weekend", 0, "STUDENT"));	
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She goes to football match", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She sleeps early", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She is going to sleep early", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("She doesn’t like sleeping late", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat does nothing on the weekend", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat is doing homework", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Anat see television", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("she goes to the stadium", 0, "STUDENT"));
//
//			for (Answer student_ans: goodToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade>0, true);
//			}
//			
//			for (Answer student_ans: badToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade==0, true);
//			}
//		}
//		
//		@Test
//		public void test7() {		
//			ArrayList<Answer> ver = new ArrayList<Answer>();
//			ArrayList<Answer> goodToCheck = new ArrayList<Answer>();
//			ArrayList<Answer> badToCheck = new ArrayList<Answer>();
//			
//			ver.add(ApiHolder.getInstance().factory.createAnswer("They were free" ,100, "TEACHER").build());
//			ver.add(ApiHolder.getInstance().factory.createAnswer("they didn’t have to pay" ,100, "TEACHER").build());
//
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it was free", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because they didn’t have to pay", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it was a gift", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Fred and Jenny were surprised because they didn’t have to pay", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because it didn’t cost them money", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips was free", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because they didn’t has to pay ", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 0, "STUDENT"));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They were surprised because they got bad chips", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips were stolen", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Because the chips are not for them", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("They was surprised because they got a god", 0, "STUDENT"));
//			tocheck.add(ApiHolder.getInstance().factory.createAnswer("Fred and Jenny were surprised because they didn’t have any chips", 0, "STUDENT"));
//
//			for (Answer student_ans: goodToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade>0, true);
//			}
//			
//			for (Answer student_ans: badToCheck) {
//				Integer grade = new AnswerAnalyzer(student_ans.build()).WordsAnalyze(ver);
//				assertEquals(grade==0, true);
//			}
//		}
//
}
