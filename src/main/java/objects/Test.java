package objects;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import apiHolder.ApiHolder;

/**
 * @author ishay
 * class representing test. contains list of questions.
 * 
 */
public class Test {
	
	private String content;// the test name
	private ObjectId tid;//test id
	private List<Question> questions = new ArrayList<Question>();
	
	public Test(ObjectId tid, String content) {
		this.tid=tid;
		this.content=content;
	}
	
	/**
	 * @return test object with loaded data from DB
	 */
	public Test load() {
		questions = new ArrayList<Question>();
		
		@SuppressWarnings("unchecked")
		List<Document> docQuestions = (List<Document>) ApiHolder.getInstance().getCollection().find(new Document().append("_id", tid))
			.first().get("questions");
		
		for(Document d : docQuestions) {
			this.questions.add(new Question(d.getObjectId("_id"), d.getString("content")));
		}		
		return this;
	}
		
	public boolean renameTest(String content){
		this.content = content;
		ApiHolder.getInstance().getCollection().updateOne(new Document().append("_id", tid),
				new Document("$set", new Document().append("content", content)));	
		return true;
	}

	/**
	 * @param q - the question string
	 * @return Question object. null if the database contains same question string.
	 */
	public Question createQuestion(String q) {
        Question toAdd = new Question(new ObjectId(), q);
        if (questions.contains(toAdd)) {
        	System.err.println("Already has that question!");
        	return null;
        }else {
        	questions.add(toAdd);
    		ApiHolder.getInstance().getCollection().updateOne(new Document().append("_id", tid),
    				new Document("$push", new Document().append("questions", questionToDoc(toAdd))));	
    		return toAdd;
        }
	}
	
	/**
	 * @param toRemove - some question
	 * @return boolean success
	 */
	public boolean removeQuestion(Question toRemove) {
		questions.remove(toRemove);
		ApiHolder.getInstance().getCollection().deleteOne(new Document("_id", toRemove.getQid()));
		return true;
	}	

	/**
	 * for each question, check all Student questions with teacher answers.
	 */
	public void checkTest() {
		questions.forEach((q)->{
			q.load().checkQuestion(q.getStudentAnswers(), q.getVerifiedAnswers(), q.getVerifiedSyntaxableAnswers());
		});
	}
	
	/**
	 * @param id - can get it from {@link Question} getQId
	 * @return boolean success
	 */
	public Question getQuestion(String id) {
		return questions.stream().filter(x->x.getQid().toString().equals(id)).findFirst().orElse(null);
	}

	/**
	 * @return list of questions
	 */
	public List<Question> getQuestions() {
	    return questions;
	}

	public ObjectId getTid() {
		return tid;
	}

	public String getContent() {
		return content;
	}

	public boolean equals (Object other) {
		if (other instanceof Test) {
			Test o = (Test) other;
			return this.tid.equals(o.tid); 
		}
		return false;
	}

	public String toString() {
		String s = "";
		s=s+"Test: " + content;
		s=s+", tid: " + tid;
	
		return s;
	}

	/**
	 * @param a question
	 * @return doc object that can be used in mongodb
	 * this is private database related function
	 */
	private Document questionToDoc(Question a) {
		return new Document().append("_id", a.getQid()).append("content", a.getContent()).append("answers", a.getAnswers());
	}
	
	public Object[][] questionsToArr(List<Question> lst){
		Object[][] arr = new Object[lst.size()][2];
		for(int i=0; i<lst.size(); i++) {
			arr[i][0] = lst.get(i).getContent();
			arr[i][1] = lst.get(i).getQid().toString();
		}
		return arr;
	}
	
	public Double getMyTestGrade() {
		Double i=(double) 0;
		
		for(Question q : questions) {
			q.load();
			if (q.getThisStudentAns().isEmpty() || q.getThisStudentAns().get(0).getGrade()<0) {
				return null;
			}else {
				i=i+q.getThisStudentAns().get(0).getGrade();
			}
		}
		return i/questions.size();
	}

	public boolean submitTest() {
		for(Question q : questions) {
			q.load();
			if (q.getThisStudentAns().isEmpty() || q.getThisStudentAns().get(0).getGrade()>-2) {
				return false;
			}else {
				q.submitAnswer(q.getThisStudentAns().get(0));
			}
		}
		return true;
	}

}
