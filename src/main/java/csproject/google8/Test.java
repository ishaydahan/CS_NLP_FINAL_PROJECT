package csproject.google8;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Test {
	
	private String content;
	private ObjectId tid;
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	@SuppressWarnings("unchecked")
	public Test(ObjectId tid, String content, ArrayList<Document> questions) {
		this.tid=tid;
		this.content=content;
		for(Document doc : questions) {
			this.questions.add(new Question(tid, doc.getObjectId("_id"), doc.getString("content"),  (ArrayList<Document>) doc.get("answers")));
		}	
	}
	
	public Question createQuestion(String q) {
        Question toAdd = new Question(tid, new ObjectId(), q, new ArrayList<Document>());
        if (questions.contains(toAdd)) {
        	System.out.println("Already has that answer!");
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
		
	public void checkTest() {
		questions.forEach((q)->{
			q.checkQuestion(q.getStudentAnswers(), q.getTeacherAnswers());
		});
	}
	
	public void removeQuestion(Question toRemove) {
		questions.remove(toRemove);
		ApiHolder.getCollection().deleteOne(new Document("_id", toRemove.getQid()));
	}	
	
	public Question getQuestion(String id) {
		for(Question q : questions) {
			if (q.getQid().toString().equals(id)) {
				return q;
			}
		}
		System.out.println("there is no such question!");
		return null;
	}

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
