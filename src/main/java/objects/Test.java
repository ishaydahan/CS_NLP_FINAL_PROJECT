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
	
	@SuppressWarnings("unchecked")
	public Test(ObjectId tid, String content, ArrayList<Document> questions) {
		this.tid=tid;
		this.content=content;
		//in the building process we need to transform DB data to objects
		for(Document doc : questions) {
			this.questions.add(new Question(tid, doc.getObjectId("_id"), doc.getString("content"),  (ArrayList<Document>) doc.get("answers")));
		}	
	}
	
	/**
	 * @param q - the question string
	 * @return question object
	 * creates question object, update database.
	 * will return null if the database contains same question string.
	 */
	public Question createQuestion(String q) {
        Question toAdd = new Question(tid, new ObjectId(), q, new ArrayList<Document>());
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
	
	public void removeQuestion(Question toRemove) {
		questions.remove(toRemove);
		ApiHolder.getCollection().deleteOne(new Document("_id", toRemove.getQid()));
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
