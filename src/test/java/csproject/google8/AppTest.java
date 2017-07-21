package csproject.google8;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}

//public static void test() {		
//ArrayList<Answer> answers = new ArrayList<Answer>();
//
//answers.add(new Answer ("because people want to help", new Integer(100), new Integer(0), true, true).build());
//answers.add(new Answer ("People choose to volunteer to help people.", new Integer(30), new Integer(0), true, true).build());
//
//answers.add(new Answer ("because people want to help", new Integer(-1), new Integer(0), false, false));// exact match
//answers.add(new Answer ("because people love to help", new Integer(-1), new Integer(0), false, false));// close meaning1
//answers.add(new Answer ("because people choose to help", new Integer(-1), new Integer(0), false, false));// close meaning2
//answers.add(new Answer ("because people want to halp", new Integer(-1), new Integer(0), false, false));// spelling
//answers.add(new Answer ("because people wants to help", new Integer(-1), new Integer(0), false, false));// grammar
//answers.add(new Answer ("people want to help", new Integer(-1), new Integer(0), false, false));//without because
//answers.add(new Answer ("they want to help", new Integer(-1), new Integer(0), false, false));//people > they
//answers.add(new Answer ("because the people want to help", new Integer(-1), new Integer(0), false, false));//with "the"
//answers.add(new Answer ("thats because people want to help people", new Integer(-1), new Integer(0), false, false));// extra information
//answers.add(new Answer ("want to help", new Integer(-1), new Integer(0), false, false));//90
//answers.add(new Answer ("to help", new Integer(-1), new Integer(0), false, false));//80
//answers.add(new Answer ("to help people", new Integer(-1), new Integer(0), false, false));//60
//answers.add(new Answer ("People choose to volunteer because they want to help.", new Integer(-1), new Integer(0), false, false));//because in the middle
//answers.add(new Answer ("they choose to volunteer to help.", new Integer(-1), new Integer(0), false, false));
//answers.add(new Answer ("they love to volunteer", new Integer(-1), new Integer(0), false, false));//tricky one
//
//answers.add(new Answer ("because people need to help", new Integer(-1), new Integer(0), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//answers.add(new Answer ("they volunteer to help", new Integer(-1), new Integer(0), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//answers.add(new Answer ("People volunteer to help.", new Integer(-1), new Integer(0), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//answers.add(new Answer ("because the people want to fly", new Integer(-1), new Integer(0), false, false));//bad verb
//answers.add(new Answer ("because the people want money", new Integer(-1), new Integer(0), false, false));//no verb
//answers.add(new Answer ("because the apes want to help", new Integer(-1), new Integer(0), false, false));//bad subject
//answers.add(new Answer ("because he want to help", new Integer(-1), new Integer(0), false, false));//bad subject+grammer
//answers.add(new Answer ("because he wants to help", new Integer(-1), new Integer(0), false, false));//bad subject
//answers.add(new Answer ("because the people have to help", new Integer(-1), new Integer(0), false, false));//bad verb
//answers.add(new Answer ("because people want help", new Integer(-1), new Integer(0), false, false));//bad meaning
//answers.add(new Answer ("they are bored", new Integer(-1), new Integer(0), false, false));
//answers.add(new Answer ("they are working for their own free will", new Integer(-1), new Integer(0), false, false));
//answers.add(new Answer ("the people need money", new Integer(-1), new Integer(0), false, false));
//answers.add(new Answer ("they dont have money", new Integer(-1), new Integer(0), false, false));
//answers.add(new Answer ("People choose to volunteer because they want money.", new Integer(-1), new Integer(0), false, false));
//
//List<Answer> toCheck = answers.stream()
//	    .filter(x -> x.grade==-1).collect(Collectors.toList());
//
//List<Answer> verified = answers.stream()
//	    .filter(x -> x.verified==true).collect(Collectors.toList());
//
//verified.sort((x,y) -> y.grade-x.grade);
//
//for (Answer student_ans: toCheck) {
//	Integer grade = new AnswertAnalyzer(student_ans.build(), verified).analyze();
//	if (grade==-2) return; //error
//	if (grade>-1) {
//		System.out.println(student_ans.content + " >> " + grade);
//	}
//}
//}