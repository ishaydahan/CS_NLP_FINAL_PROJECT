package csproject.google8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Question {
	
	private String content;
	private ObjectId tid;
	private ObjectId qid;
	private ArrayList<Answer> answers = new ArrayList<Answer>();

	public Question(ObjectId tid, ObjectId qid, String content, ArrayList<Document> answers) {
		this.content=content;
		this.tid=tid;
		this.qid=qid; 
		for(Document d : answers) {
			this.answers.add(new Answer(d.getObjectId("_id"), d.getString("content"), d.getString("writer"), d.getInteger("grade"), d.getInteger("answerWords"), d.getBoolean("verified"), d.getBoolean("learnable")));
		}	
	}
	
	public Answer createAns(String teacher_ans, int grade) {        
        Answer toAdd = new Answer(new ObjectId(), teacher_ans, "TEACHER", grade, new Integer(-1), true, true);
        if (answers.contains(toAdd)) {
        	System.out.println("Already has that answer!");
        	return null;
        }else {
            answers.add(toAdd.build());
    		ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
    				.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
    				new Document("$push", new Document().append("questions.$.answers", answerToDoc(toAdd))));	
    		return toAdd;
        }
	}
	
	public Answer addStudentAns(String student_ans){
        Answer toAdd = new Answer(new ObjectId(), student_ans, "STUDENT", new Integer(-1), new Integer(-1), false, false);
        if (answers.contains(toAdd)) {
        	System.out.println("Already has that answer!");
        	return null;
        }else {
            answers.add(toAdd);
    		ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
    				.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
    				new Document("$push", new Document().append("questions.$.answers", answerToDoc(toAdd))));	
    		return toAdd;
        }
	}
		
	public boolean fixAns(int grade, Answer toFix){		
        if(toFix.getWriter().equals("TEACHER")) {
	        toFix.setGrade(Integer.valueOf(grade));
        }else if(toFix.getWriter().equals("STUDENT")) {
        	if(answers.contains(new Answer(new ObjectId(), toFix.getContent(), "TEACHER", toFix.getGrade(), toFix.getAnswerWords(), true, true))) {
		        System.out.println(">> There is same teacher answer with different grade");
		        return false;
        	}
        }else{
			toFix.setVerified(true);
			toFix.setLearnable(true);
        }
        
		ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
				.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
				new Document("$set", new Document().append("questions.$.answers."+answerToInt(toFix), answerToDoc(toFix))));	
		return true;
	}
		
	public void checkQuestion(List<Answer> toCheck, List<Answer> verified) {
		verified.sort((x,y) -> y.getGrade()-x.getGrade());
				
		for (Answer student_ans: toCheck) {
			Integer grade = new AnswertAnalyzer(student_ans.build(), verified).analyze();
			if (grade==-2) continue; //error
			if (grade>-1) {
				System.out.println(student_ans);
				ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
						.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
						new Document("$set", new Document().append("questions.$.answers."+answerToInt(student_ans), answerToDoc(student_ans))));	
			}
		}
	}
	
	public void approveAll() {
		answers.forEach((ans)->{
			fixAns(ans.getGrade(), ans);
			ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
					.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
					new Document("$set", new Document().append("questions.$.answers."+answerToInt(ans), answerToDoc(ans))));	
		});
	}
	
	public void removeAnswer(Answer toRemove) {
		answers.remove(toRemove);
		ApiHolder.getCollection().updateOne(new Document().append("_id", tid).append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
				new Document("$pull", new Document().append("questions.$.answers", answerToDoc(toRemove))));	
	}	
	
	public Answer getAnswer(String id) {
		for(Answer ans : answers) {
			if (ans.get_id().toString().equals(id)) {
				return ans;
			}
		}
		System.out.println("there is no such answer!");
		return null;
	}
	
	public List<Answer> getVerifiedAnswers() {
		return answers.stream().filter(x -> x.getVerified()==true).collect(Collectors.toList());
	}
	
	public List<Answer> getTeacherAnswers() {
		return answers.stream().filter(x -> x.getWriter().equals("TEACHER")).collect(Collectors.toList());
	}
	
	public List<Answer> getStudentAnswers() {
		return answers.stream().filter(x -> x.getWriter().equals("STUDENT")).collect(Collectors.toList());
	}

	public List<Answer> getUnverifiedStudentAnswers() {
		return answers.stream().filter(x -> x.getWriter().equals("STUDENT") && x.getVerified()==false).collect(Collectors.toList());
	}

	public List<Answer> getUngradedStudentAnswers() {
		return answers.stream().filter(x -> x.getWriter().equals("STUDENT") && x.getGrade()==-1).collect(Collectors.toList());
	}

	private Document answerToDoc(Answer a) {
		return new Document().append("_id", a.get_id()).append("content", a.getContent()).append("writer", a.getWriter()).append("grade", a.getGrade()).append("answerWords", a.getAnswerWords()).append("verified", a.getVerified()).append("learnable", a.getLearnable());
	}
	
	private int answerToInt(Answer ans) {
		int i=0;
		for(Answer a : answers) {
			if (ans.equals(a)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public boolean equals (Object other) {
		if (other instanceof Question) {
			Question o = (Question) other;
			return this.content.equals(o.content); 
		}
		return false;
	}	
	
	public String toString() {
		String s = "";
		s=s+"Question: " + content;
		s=s+", qid: " + qid;

		return s;
	}

	public ObjectId getQid() {
		return qid;
	}

	public String getContent() {
		return content;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

}
