package objects;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.types.ObjectId;

import analyzer.AnswerAnalyzer;
import apiHolder.ApiHolder;

/**
 * @author ishay
 * class representing Teacher question. the class holds {@link Answer}s.
 * the class can manipulate answers (create, delete, correct, select..)
 */
public class Question {
	
	private String content;//the question itself
	private ObjectId qid;//question id
	private List<Answer> answers = new ArrayList<Answer>();//list of all answers
	
	protected Question(ObjectId qid, String content) {
		this.content=content;
		this.qid=qid; 
	}
		
	@SuppressWarnings("unchecked")
	public Question load() {
		answers = new ArrayList<Answer>();
		
		List<Document> docAnswers = (List<Document>) ApiHolder.getInstance().getCollection().find(new Document().append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))))
		.first().get("questions");
		docAnswers = (List<Document>) docAnswers.get(0).get("answers");

		for(Document d : docAnswers) {
			this.answers.add(new Answer(d.getObjectId("_id"), d.getString("content"), 
					Writer.valueOf((d.getString("writer"))), d.getInteger("grade"), d.getInteger("answerWords"), 
					d.getBoolean("verified"), d.getBoolean("syntaxable")));
		}
		
		return this;
	}
	
	public boolean renameQuestion(String content){
		this.content = content;
		ApiHolder.getInstance().getCollection().updateOne(new Document()
				.append("questions._id", qid),
				new Document("$set", new Document().append("questions.0.content", content)));
		return true;
	}

	/**
	 * @param teacher_ans - some string
	 * @param grade - probably 100
	 * @return answer object
	 * the function creates {@link Answer} and put it in the list and on the server.
	 * if answers already contains this answer will return null
	 */
	public Answer addTeacherAns(String teacher_ans, int grade) {        
        Answer toAdd = ApiHolder.getInstance().factory.createAnswer(teacher_ans, grade, Writer.TEACHER);
        if (answers.contains(toAdd)) {
        	System.out.println("Already has that answer!" + toAdd.getContent());
        	return null;
        }else {
            DBcreateAnswer(toAdd);
            answers.add(toAdd);
    		return toAdd;
        }
	}
	
	/**
	 * @param student_ans
	 * @return
	 * wont check containing answer since each student may write same answer content
	 */
	public Answer addStudentAns(String student_ans){
        Answer toAdd = ApiHolder.getInstance().factory.createAnswer(student_ans, -1, Writer.STUDENT);
        DBcreateAnswer(toAdd);
        answers.add(toAdd);
		return toAdd;
	}
		
	/**
	 * @param grade
	 * @param toFix
	 * @return
	 * will return false if there is verified ans with different grade.
	 * will change verified = true, syntaxable = true;
	 */
	public boolean fixAns(int grade, Answer toFix){	
		answers.stream().filter(ans -> ans.getContent().equals(toFix.getContent()))
		.collect(Collectors.toList()).forEach((x)->{
	    	x.setGrade(grade);
	    	x.setVerified(true);
			DBeditAnswer(x);
		});
		return true;
	}
	
	public boolean renameAns(String content, Answer toFix) {
		toFix.setContent(content);
		DBeditAnswer(toFix);
		return true;
	}
	
	/**
	 * @param ans
	 */
	public boolean approveAnswer(Answer ans) {
		answers.stream().filter(x->x.get_id().equals(ans.get_id()))
		.collect(Collectors.toList()).forEach((x)->{
	    	x.setVerified(true);
	    	DBeditAnswer(x);
		});
		return true;
	}

	/**
	 * mark all student answers as true;
	 * will use fix ans to all unique answers.
	 */
	public boolean approveAll() {		
		answers.forEach((x)->{
	    	x.setVerified(true);
	    	DBeditAnswer(x);
		});
		return true;
	}
	
	/**
	 * @param toRemove some answer that you can get from getAnswer
	 */
	public boolean removeAnswer(Answer toRemove) {
		DBremoveAnswer(toRemove);
		answers.remove(toRemove);
		return true;
	}	
	
	/**
	 * 
	 */
	public boolean removeAll() {
		answers.forEach(ans->{
			removeAnswer(ans);
		});
		return true;
	}	

	/**
	 * @param toCheck - list of answers to check(all student ans.. only ungraded..)
	 * @param verified - list of verified answers, mostly teacher ans / syntaxable ans
	 * this 
	 */
	public void checkQuestion(List<Answer> toCheck, List<Answer> verified, List<Answer> syntaxable) {		
		//create log file
		PrintStream logger = null;
		try {
			logger = new PrintStream(new FileOutputStream("logs/question output.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		//sort the list first from high to low to get the maximum grade in min time
		verified.sort((x,y) -> y.getGrade()-x.getGrade());
		//build each verified question.
		verified.forEach((ans)->ans.build());	
		//sort the list first from high to low to get the maximum grade in min time
		syntaxable.sort((x,y) -> y.getGrade()-x.getGrade());
		//build each verified question.
		syntaxable.forEach((ans)->ans.build());	
	
		
		//determine min value for syntaxable
		int minsyntaxable = Integer.MAX_VALUE;
		for (Answer ans : getTeacherAnswers()) {
			if (ans.getWriter().equals(Writer.TEACHER))  minsyntaxable = Math.min(minsyntaxable, ans.getAnswerWords());
		}
		
		//check
		for (Answer student_ans: toCheck) {
			//first build the answer
			AnswerAnalyzer analyzer = new AnswerAnalyzer(student_ans.build());
			
			int grade;
			if ((grade = analyzer.levenshteinAnalyze(verified))>-1) {
				
				student_ans.setGrade(grade);
				
			}else if ((grade = analyzer.SyntaxAnalyze(syntaxable))>-2) {//there is no -1 option in last check. must return grade.
				
				student_ans.setGrade(grade);
	
			}else {
				//error
				return;
			}
			
			//set syntaxable value
	    	if(student_ans.getAnswerWords()>=minsyntaxable) student_ans.setsyntaxable(true);
	    	else student_ans.setsyntaxable(false);
	    	
			//log to file
			logger.println(student_ans.toString());
			System.out.println(student_ans);
			
			DBeditAnswer(student_ans);
		}
	}

	/**
	 * @param id - get it from {@link Answer} get_Id
	 * @return
	 */
	public Answer getAnswer(String id) {
		return answers.stream().filter(x->x.get_id().toString().equals(id)).findFirst().orElse(null);
	}
	
	public boolean DBeditAnswer(Answer x) {
		ApiHolder.getInstance().getCollection().updateOne(new Document()
				.append("questions.answers._id", x.get_id()),
				new Document("$set", new Document().append("questions.0.answers.$", answerToDoc(x))));
		return true;
	}
	
	public boolean DBremoveAnswer(Answer x) {
		ApiHolder.getInstance().getCollection().updateOne(new Document()
				.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
				new Document("$pull", new Document().append("questions.$.answers", answerToDoc(x))));	
		return true;
	}

	public boolean DBcreateAnswer(Answer x) {
		ApiHolder.getInstance().getCollection().updateOne(new Document()
				.append("questions", new Document().append("$elemMatch", new Document().append("_id", qid))),
				new Document("$push", new Document().append("questions.$.answers", answerToDoc(x))));	
		return true;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	//answers that teacher wrote
	public List<Answer> getTeacherAnswers() {
		return answers.stream().filter(x -> x.getWriter().equals(Writer.TEACHER)).collect(Collectors.toList());
	}
	
	public List<Answer> getStudentAnswers() {
		return answers.stream().filter(x -> x.getWriter().equals(Writer.STUDENT)).collect(Collectors.toList());
	}

	public List<Answer> getUngradedStudentAnswers() {
		return answers.stream().filter(x -> x.getWriter().equals(Writer.STUDENT) && x.getGrade()==-1).collect(Collectors.toList());
	}

	public List<Answer> getVerifiedAnswers() {
		return answers.stream().filter(x -> x.getVerified()).collect(Collectors.toList());
	}

	public List<Answer> getVerifiedSyntaxableAnswers() {
		return answers.stream().filter(x -> x.getVerified() && x.getsyntaxable()).collect(Collectors.toList());
	}

	public List<Answer> getSyntaxableAnswers() {
		return answers.stream().filter(x -> x.getsyntaxable()).collect(Collectors.toList());
	}
	
	public ObjectId getQid() {
		return qid;
	}

	public String getContent() {
		return content;
	}

	public String toString() {
		String s = "";
		s=s+"Question: " + content;
		s=s+", qid: " + qid;
	
		return s;
	}

	public boolean equals (Object other) {
		if (other instanceof Question) {
			Question o = (Question) other;
			return this.content.equals(o.content); 
		}
		return false;
	}

	private Document answerToDoc(Answer a) {
		return new Document().append("_id", a.get_id()).append("content", a.getContent()).append("writer", a.getWriter().name()).append("grade", a.getGrade()).append("answerWords", a.getAnswerWords()).append("verified", a.getVerified()).append("syntaxable", a.getsyntaxable());
	}
	
	public Object[][] AnswersToArr(List<Answer> lst){
		Object[][] arr = new Object[lst.size()][3];
		for(int i=0; i<lst.size(); i++) {
			arr[i][0] = lst.get(i).getContent();
			arr[i][1] = lst.get(i).getGrade();
			arr[i][2] = lst.get(i).get_id().toString();
		}
		return arr;
	}



}
