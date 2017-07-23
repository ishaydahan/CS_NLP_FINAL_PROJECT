package objects;

import java.util.Arrays;
import java.util.HashMap;

import org.bson.types.ObjectId;

import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.PartOfSpeech.Tag;

import apiHolder.ApiHolder;

import com.google.cloud.language.v1.Token;

/**
 * @author ishay
 * This class represent one answer, could be written by teacher or student or even computer.
 * this class can easily transfered to json {@link org.bson.Document}.
 * this is the object that should be sent to the syntax analyzer algorithm {@link AnswertAnalyzer}.
 */
public class Answer {
	
	private ObjectId _id ;//answer id
	private String content;//the answer itself
	private Writer writer;//who wrote the answer
	private Integer grade;//Wasn't graded = -1
	private Integer answerWords;//num of significant words.
	private Boolean verified;//there is enough data to learn from this answer
	private Boolean syntaxable;//there is enough data to learn from this answer
	private HashMap<String, Integer> map = new HashMap<String, Integer>();//map to get better runtime. student->grade, relates to this answer
	private AnalyzeSyntaxResponse Analyzed_ans = null;//google analyzed syntax
	
	//Enemas for insignificant parts of sentence
	private Tag[] del = {Tag.PUNCT, Tag.UNKNOWN, Tag.ADP, Tag.X, Tag.AFFIX, Tag.DET};

	public Answer(ObjectId _id, String content, Writer writer, Integer grade, Integer answerWords,Boolean verified, Boolean syntaxable) {
		this._id=_id;
		this.content=content.toLowerCase();
		this.writer=writer;
		this.grade=grade;
		this.answerWords=answerWords;
		this.verified = verified;
		this.syntaxable=syntaxable;
	}
	
	/**
	 * @return same Answer but has called the google api Syntax checker and counted significant words
	 */
	public Answer build() {
		if (Analyzed_ans==null) {
			//google analyzer
			Document doc = Document.newBuilder()
		            .setContent(content).setType(Type.PLAIN_TEXT).build();
			ApiHolder.logger.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$ " + content + " $$ google api");
			Analyzed_ans = ApiHolder.langClient.analyzeSyntax(doc, EncodingType.UTF8);
		}
		
		if (answerWords==-1) {
			answerWords++;
			for(Token t : Analyzed_ans.getTokensList()) {
				if (!Arrays.asList(del).contains(t.getPartOfSpeech().getTag())) {
						ApiHolder.logger.println("ANALYZER :::: Teacher parts: " + t.getText().getContent());
						ApiHolder.logger.println("ANALYZER :::: Teacher parts: " + t.getPartOfSpeech().getTag());
						answerWords++;
				}
			}
		}
		return this;
	}
	
	public Integer getGrade() {
		return grade;
	}
	

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public ObjectId get_id() {
		return _id;
	}

	public String getContent() {
		return content;
	}

	public Writer getWriter() {
		return writer;
	}

	public Integer getAnswerWords() {
		return answerWords;
	}

	public Boolean getsyntaxable() {
		return syntaxable;
	}

	public void setsyntaxable(Boolean syntaxable) {
		this.syntaxable = syntaxable;
	}

	public AnalyzeSyntaxResponse getAnalyzed_ans() {
		return Analyzed_ans;
	}

	public HashMap<String, Integer> getMap() {
		return map;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	//two answers are the same if they have the same id (wont happen)
	//or the answer written by teacher and has same content
	//or the answer is verified and has same content
	public boolean equals (Object other) {
		if (other instanceof Answer) {
			Answer o = (Answer) other;
			return (this.content.equals(o.content) 
					&& !this.writer.equals(Writer.STUDENT) 
					&& !o.writer.equals(Writer.STUDENT)     
					);
		}
		return false;
	}

	public String toString() {
		String s = "";
		s=s+"Answer: " + content;
		s=s+", Wrriten by: " + writer.name();
		s=s+", Grade: " + grade;
		s=s+", Significant Words: " + answerWords;
		s=s+", Teacher verified: " + verified;
		s=s+", syntaxable: " + syntaxable;
		s=s+", aid: " + _id;
	
		return s;
	}
}
