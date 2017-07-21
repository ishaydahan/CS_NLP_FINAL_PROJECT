package csproject.google8;

/**
 * @author ishay
 * These are the methods that publicly available to users.
 */
public class TestApi {

	private Test t = null;
	
	/**
	 * @param t
	 * constructor for the Api. needs Test object that can be retrieved from Teacher Api select function
	 */
	public TestApi(Test t) {
		this.t=t;
	}
	
	/**
	 * main api function. to be deleted.
	 */
	public void main() {
		String option = "-1";
		while (true) {
			System.out.println("\n@@  Test Screen Options  @@");
			System.out.println("0 - exit");
			System.out.println("1 - create question");
			System.out.println("2 - show questions");
			System.out.println("3 - select question");
			System.out.println("4 - check test");
			System.out.println("5 - remove question");

			option = ApiHolder.scanner.nextLine();
			if (option.equals("0")) {
				System.out.println(">> Returning Teacher Screen");
				return;
			}else if(option.equals("1")) {
				try {createQuestion();}catch(Exception e) {System.out.println("problem with data");}
			}else if(option.equals("2")) {
				try {showQuestions();}catch(Exception e) {System.out.println("problem with data");}
			}else if(option.equals("3")) {
				try {selectQuestion();}catch(Exception e) {System.out.println("problem with data");}
			}else if(option.equals("4")) {
				try {checkTest();}catch(Exception e) {System.out.println("problem with data");}
			}else if(option.equals("5")) {
				try {remove();}catch(Exception e) {System.out.println("problem with data");}
			}else {
				System.out.println(">> Bad Input!");
			}
		}
	}
	
	/**
	 * creates Question and insert it to database
	 */
	public void createQuestion() {
		System.out.println(">> Enter Question:");
        String question = ApiHolder.scanner.nextLine();

        t.createQuestion(question);
		System.out.println(">> Done");
	}
	
	/**
	 * 
	 */
	public void showQuestions() {
        int i=1;
        for (Question s : t.getQuestions()) {
        	System.out.print(i+ " - ");
        	System.out.println(s);
        	i++;
        }
	}
	
	/**
	 * 
	 */
	public void selectQuestion() {
		System.out.println(">> Enter Question id:");
        String id = ApiHolder.scanner.nextLine();
        
        Question q = t.getQuestion(id);
        if (q==null) return;
    	QuestionApi qapi = new QuestionApi(q);
    	qapi.main();
    	return;
	}
	
	/**
	 * 
	 */
	public void checkTest() {
		t.checkTest();
	}
	
	/**
	 * 
	 */
	public void remove() {
		try {
			System.out.println(">> Enter Question id:");
			String id = ApiHolder.scanner.nextLine();
			t.removeQuestion(t.getQuestion(id));
			System.out.println(">> Done");
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
