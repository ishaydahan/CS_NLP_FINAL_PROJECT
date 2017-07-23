package objects;
import java.util.ArrayList;
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
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	public Test(ObjectId tid, String content) {
		this.tid=tid;
		this.content=content;
	}
	
	/**
	 * @return
	 */
	public Test load() {
		questions = new ArrayList<Question>();
		
		@SuppressWarnings("unchecked")
		ArrayList<Document> docQuestions = (ArrayList<Document>) ApiHolder.getCollection().find(new Document().append("_id", tid))
			.first().get("questions");
		
		for(Document d : docQuestions) {
			this.questions.add(new Question(tid, d.getObjectId("_id"), d.getString("content")));
		}		
		return this;
	}
	
	/**
	 * @return
	 */
	public Test save() {		
		questions.forEach((q)->{
			ApiHolder.getCollection().updateOne(new Document().append("_id", tid),
					new Document("$set", new Document().append("content", content)));	
		});
		
		return this;
	}
	
	/**
	 * @param q - the question string
	 * @return question object
	 * creates question object, update database.
	 * will return null if the database contains same question string.
	 */
	public Question createQuestion(String q) {
        Question toAdd = new Question(tid, new ObjectId(), q);
        if (questions.contains(toAdd)) {
        	System.err.println("Already has that question!");
        	return null;
        }else {
        	questions.add(toAdd);
    		ApiHolder.getCollection().updateOne(new Document().append("_id", tid),
    				new Document("$push", new Document().append("questions", questionToDoc(toAdd))));	
    		return toAdd;
        }
	}
	
	public void removeQuestion(Question toRemove) {
		questions.remove(toRemove);
		ApiHolder.getCollection().deleteOne(new Document("_id", toRemove.getQid()));
	}	

	public ArrayList<Question> getQuestions() {
        return questions;
	}
		
	/**
	 * for each question, check all Student questions with teacher answers.
	 */
	public void checkTest() {
		questions.forEach((q)->{
			q.checkQuestion(q.getStudentAnswers(), q.getVerifiedAnswers(), q.getVerifiedSyntaxableAnswers());
		});
	}
	
	
	/**
	 * @param id - can get it from {@link Question} getQId
	 * @return
	 */
	public Question getQuestion(String id) {
		for(Question q : questions) {
			if (q.getQid().toString().equals(id)) {
				return q;
			}
		}
		System.err.println("there is no such question!");
		return null;
	}
	
	/**
	 * @param a
	 * @return
	 * this is private database related function
	 */
	private Document questionToDoc(Question a) {
		return new Document().append("_id", a.getQid()).append("content", a.getContent()).append("answers", a.getAnswers());
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

	public ObjectId getTid() {
		return tid;
	}

	public String getContent() {
		return content;
	}

}
