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
    
//	public static void test() {		
//		ArrayList<Answer> ver = new ArrayList<Answer>();
//		ArrayList<Answer> tocheck = new ArrayList<Answer>();
//	
//		ver.add(new Answer (null, "because people want to help", null, new Integer(100), new Integer(-1), true, true).build());
//		ver.add(new Answer (null, "People choose to volunteer to help people.", null, new Integer(100), new Integer(-1), true, true).build());
//		
//		tocheck.add(new Answer (null, "because people want to help", null, new Integer(-1), new Integer(-1), false, false));// exact match
//		tocheck.add(new Answer (null, "because people love to help", null, new Integer(-1), new Integer(-1), false, false));// close meaning1
//		tocheck.add(new Answer (null, "because people choose to help", null, new Integer(-1), new Integer(-1), false, false));// close meaning2
//		tocheck.add(new Answer (null, "because people want to halp", null, new Integer(-1), new Integer(-1), false, false));// spelling
//		tocheck.add(new Answer (null, "because people wants to help", null, new Integer(-1), new Integer(-1), false, false));// grammar
//		tocheck.add(new Answer (null, "people want to help", null, new Integer(-1), new Integer(-1), false, false));//without because
//		tocheck.add(new Answer (null, "they want to help", null, new Integer(-1), new Integer(-1), false, false));//people > they
//		tocheck.add(new Answer (null, "because the people want to help", null, new Integer(-1), new Integer(-1), false, false));//with "the"
//		tocheck.add(new Answer (null, "thats because people want to help people", null, new Integer(-1), new Integer(-1), false, false));// extra information
//		tocheck.add(new Answer (null, "want to help", null, new Integer(-1), new Integer(-1), false, false));//90
//		tocheck.add(new Answer (null, "to help", null, new Integer(-1), new Integer(-1), false, false));//80
//		tocheck.add(new Answer (null, "to help people", null, new Integer(-1), new Integer(-1), false, false));//60
//		tocheck.add(new Answer (null, "People choose to volunteer because they want to help.", null, new Integer(-1), new Integer(-1), false, false));//because in the middle
//		tocheck.add(new Answer (null, "they choose to volunteer to help.", null, new Integer(-1), new Integer(-1), false, false));
//		tocheck.add(new Answer (null, "they volunteer to help", null, new Integer(-1), new Integer(-1), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		tocheck.add(new Answer (null, "People volunteer to help.", null, new Integer(-1), new Integer(-1), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		
//		tocheck.add(new Answer (null, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", null, new Integer(-1), new Integer(-1), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//	
//		tocheck.add(new Answer (null, "because people need to help", null, new Integer(-1), new Integer(-1), false, false));///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		tocheck.add(new Answer (null, "they love to volunteer", null, new Integer(-1), new Integer(-1), false, false));//tricky one
//		tocheck.add(new Answer (null, "because the people want to fly", null, new Integer(-1), new Integer(-1), false, false));//bad verb
//		tocheck.add(new Answer (null, "because the people want money", null, new Integer(-1), new Integer(-1), false, false));//no verb
//		tocheck.add(new Answer (null, "because the apes want to help", null, new Integer(-1), new Integer(-1), false, false));//bad subject
//		tocheck.add(new Answer (null, "because he want to help", null, new Integer(-1), new Integer(-1), false, false));//bad subject+grammer
//		tocheck.add(new Answer (null, "because he wants to help", null, new Integer(-1), new Integer(-1), false, false));//bad subject
//		tocheck.add(new Answer (null, "because the people have to help", null, new Integer(-1), new Integer(-1), false, false));//bad verb
//		tocheck.add(new Answer (null, "because people want help", null, new Integer(-1), new Integer(-1), false, false));//bad meaning
//		tocheck.add(new Answer (null, "they are bored", null, new Integer(-1), new Integer(-1), false, false));
//		tocheck.add(new Answer (null, "they are working for their own free will", null, new Integer(-1), new Integer(-1), false, false));
//		tocheck.add(new Answer (null, "the people need money", null, new Integer(-1), new Integer(-1), false, false));
//		tocheck.add(new Answer (null, "they dont have money", null, new Integer(-1), new Integer(-1), false, false));
//		tocheck.add(new Answer (null, "People choose to volunteer because they want money.", null, new Integer(-1), new Integer(-1), false, false));
//	
//		
//		for (Answer student_ans: tocheck) {
//			Integer grade = new AnswerAnalyzer(student_ans.build(), ver).analyze();
//			if (grade==-2) return; //error
//			if (grade>-1) {
//				System.out.println(student_ans);
//			}
//		}
//	}
}
