package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;

import apiHolder.ApiHolder;
import syntaxAnalyzer.AnswertAnalyzer;

/**
 * @author ishay
 * class resresenting Teacher question. the class holds {@link Answer}s.
 * the class can manipulate answers (create, delete, correct, select..)
 */
public class Question {
	
	private String content;//the question itself
	private ObjectId tid;//test id
	private ObjectId qid;//question id
	private ArrayList<Answer> answers = new ArrayList<Answer>();//list of all answers

	public Question(ObjectId tid, ObjectId qid, String content, ArrayList<Document> answers) {
		this.content=content;
		this.tid=tid;
		this.qid=qid; 
		//building the answers from the list got from server
		for(Document d : answers) {
			this.answers.add(new Answer(d.getObjectId("_id"), d.getString("content"), d.getString("writer"), d.getInteger("grade"), d.getInteger("answerWords"), d.getBoolean("verified"), d.getBoolean("learnable")));
		}	
	}
	
	/**
	 * @param teacher_ans - some string
	 * @param grade - probably 100
	 * @return answer object
	 * the function creates {@link Answer} and put it in the list and on the server.
	 * if answers already contains this answer will return null
	 */
	public Answer createAns(String teacher_ans, int grade) {        
        Answer toAdd = new Answer(new ObjectId(), teacher_ans, "TEACHER", grade, new Integer(-1), true, true);
        if (answers.contains(toAdd)) {
        	System.err.println("Already has that answer!");
        	return null;
        }else {
            answers.add(toAdd.build());
    		ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
    				.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
    				new Document("$push", new Document().append("questions.$.answers", answerToDoc(toAdd))));	
    		return toAdd;
        }
	}
	
	/**
	 * @param student_ans
	 * @return
	 * wont check containing answer since each student may write same answer content
	 */
	public Answer addStudentAns(String student_ans){
        Answer toAdd = new Answer(new ObjectId(), student_ans, "STUDENT", new Integer(-1), new Integer(-1), false, false);
        answers.add(toAdd);
		ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
				.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
				new Document("$push", new Document().append("questions.$.answers", answerToDoc(toAdd))));	
		return toAdd;
	}
		
	/**
	 * @param grade
	 * @param toFix
	 * @return
	 * will return false if there is verified ans with different grade.
	 * will change verified = true, learnable = true;
	 */
	public boolean fixAns(int grade, Answer toFix){	
    	if (toFix.getWriter().equals("STUDENT")) {
            Answer toAdd = new Answer(new ObjectId(), toFix.getContent(), toFix.getWriter(), grade, new Integer(-1), true, toFix.getLearnable());
            if (answers.contains(toAdd)) {
            	System.err.println("there is verified ans with same content. fix it first!");
            	return false;
            }
    	}
    	
    	//if it was a teacher ans, fix it first
    	if (toFix.getWriter().equals("TEACHER")) toFix.setGrade(grade);
    	
    	//fix all similar student ans with the new grade
		answers.stream().filter(x -> x.getWriter().equals("STUDENT") && x.getContent().equals(toFix.getContent()))
		.collect(Collectors.toList()).forEach((x)->{
			
			//if teacher fixes ans, it means its verified and learnable
	        x.setGrade(grade);
			x.setVerified(true);
			x.setLearnable(true);
		
			ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
					.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
					new Document("$set", new Document().append("questions.$.answers."+answerToInt(x), answerToDoc(x))));	
		});

		return true;
	}
		
	/**
	 * @param toCheck - list of answers to check(all student ans.. only ungraded..)
	 * @param verified - list of verified answers, mostly teacher ans / learnable ans
	 * this 
	 */
	public void checkQuestion(List<Answer> toCheck, List<Answer> verified) {
		//sort the list first from high to low to get the maximum grade in min time
		verified.sort((x,y) -> y.getGrade()-x.getGrade());
				
		for (Answer student_ans: toCheck) {
			//first build the answer (google api)
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
	
	/**
	 * mark all student answers as true; (will create teacher answer)
	 */
	public void approveAll() {
		getStudentAnswers().forEach((ans)->{
			fixAns(ans.getGrade(), ans);
			ApiHolder.getCollection().updateOne(new Document().append("_id", tid)
					.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
					new Document("$set", new Document().append("questions.$.answers."+answerToInt(ans), answerToDoc(ans))));	
		});
	}
	
	/**
	 * @param toRemove some answer that you can get from getAnswer
	 */
	public void removeAnswer(Answer toRemove) {
		answers.remove(toRemove);
		ApiHolder.getCollection().updateOne(new Document().append("_id", tid).append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
				new Document("$pull", new Document().append("questions.$.answers", answerToDoc(toRemove))));	
	}	
	
	/**
	 * @param id - get it from {@link Answer} get_Id
	 * @return
	 */
	public Answer getAnswer(String id) {
		for(Answer ans : answers) {
			if (ans.get_id().toString().equals(id)) {
				return ans;
			}
		}
		System.err.println("there is no such answer!");
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
