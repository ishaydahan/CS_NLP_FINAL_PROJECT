//package consoleApi;
//
//import objects.Question;
//import objects.Test;
//
//public class ConsoleTest {
//
//	private Test t = null;
//	
//	public ConsoleTest(Test t) {
//		this.t=t;
//	}
//	
//	public void main() {
//		String option = "-1";
//		while (true) {
//			System.out.println("\n@@  Test Screen Options  @@");
//			System.out.println("0 - exit");
//			System.out.println("1 - create question");
//			System.out.println("2 - show questions");
//			System.out.println("3 - select question");
//			System.out.println("4 - check test");
//			System.out.println("5 - remove question");
//			System.out.println("6 - (STUDENT) get my test grade");
//			System.out.println("7 - (STUDENT) submitTest");
//
//			option = ConsoleProgram.scanner.nextLine();
//			if (option.equals("0")) {
//				System.out.println(">> Returning Teacher Screen");
//				return;
//			}else if(option.equals("1")) {
//				createQuestion();
//			}else if(option.equals("2")) {
//				showQuestions();
//			}else if(option.equals("3")) {
//				selectQuestion();
//			}else if(option.equals("4")) {
//				checkTest();
//			}else if(option.equals("5")) {
//				remove();
//			}else if(option.equals("6")) {
//				getMyTestGrade();
//			}else if(option.equals("7")) {
//				submitTest();
//			}else {
//				System.out.println(">> Bad Input!");
//			}
//		}
//	}
//	
//	public void createQuestion() {
//		System.out.println(">> Enter Question:");
//        String question = ConsoleProgram.scanner.nextLine();
//
//        t.createQuestion(question);
//	}
//	
//	public void showQuestions() {
//        int i=1;
//        System.out.println("///////////////////////////////////");
//        for (Question s : t.getQuestions()) {
//        	System.out.print(i+ " - ");
//        	System.out.println(s);
//        	i++;
//        }
//        System.out.println("///////////////////////////////////");
//	}
//	
//	public void selectQuestion() {
//		System.out.println(">> Enter Question id:");
//        String id = ConsoleProgram.scanner.nextLine();
//        
//        Question q = t.getQuestion(id);
//        if (q==null) return;
//    	ConsoleQuestion qapi = new ConsoleQuestion(q.load());
//    	qapi.main();
//	}
//
//	public void checkTest() {
//		t.checkTest();
//	}
//	
//	public void remove() {
//		try {
//			System.out.println(">> Enter Question id:");
//			String id = ConsoleProgram.scanner.nextLine();
//			t.removeQuestion(t.getQuestion(id));
//		}catch(Exception e) {
//			return;
//		}
//	}
//	
//	public void getMyTestGrade() {
//		Double grade = t.getMyTestGrade();
//		if (grade!=null) System.out.println(grade);
//		else System.out.println("didnt answered all questions / test wasent checked yet / teacher's answers wasnet submited to some questions in this test");
//	}
//	
//	public void submitTest() {
//		if (!t.submitTest()) System.out.println("already submitted / didnt answerd all questions");
//	}
//
//}
