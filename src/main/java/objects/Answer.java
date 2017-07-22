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
import syntaxAnalyzer.AnswertAnalyzer;

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
	private String writer;//who wrote the answer
	private Integer grade;//Wasn't graded = -1
	private Integer answerWords;//num of significant words.
	private Boolean verified;//if teacher marked true
	private Boolean learnable;//there is enough data to learn from this answer
	private HashMap<String, Integer> map = new HashMap<String, Integer>();//map to get better runtime. student->grade, relates to this answer
	private AnalyzeSyntaxResponse Analyzed_ans;//google analyzed syntax
	
	//Enemas for insignificant parts of sentence
	private Tag[] del = {Tag.PUNCT, Tag.UNKNOWN, Tag.ADP, Tag.X, Tag.AFFIX, Tag.DET};

	public Answer(ObjectId _id, String content, String writer, Integer grade, Integer answerWords, Boolean verified, Boolean learnable) {
		this._id=_id;
		this.content=content;
		this.writer=writer;
		this.grade=grade;
		this.answerWords=answerWords;
		this.verified=verified;
		this.learnable=learnable;
	}
	
	/**
	 * @return same Answer but has called the google api Syntax checker and counted significant words
	 */
	public Answer build() {
		//google analyzer
		Document doc = Document.newBuilder()
	            .setContent(content).setType(Type.PLAIN_TEXT).build();
		ApiHolder.logger.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$ " + content+ " $$ google api");
		Analyzed_ans = ApiHolder.langClient.analyzeSyntax(doc, EncodingType.UTF8);
		
		if (answerWords==-1) {
			answerWords++;
			for(Token t : Analyzed_ans.getTokensList()) {
				if (!Arrays.asList(del).contains(t.getPartOfSpeech().getTag())) {
						ApiHolder.logger.println("ANALYZER :::: Teacher parts: " + t.getText().getContent());
						answerWords++;
				}
			}
		}
		return this;
	}
	
	//two answers are the same if they have the same id (wont happen)
	//or the answer written by teacher and has same content
	//or the answer is verified and has same content
	public boolean equals (Object other) {
		if (other instanceof Answer) {
			Answer o = (Answer) other;
			return (this._id.equals(o.get_id())) ||
					(this.content.equals(o.content) && this.verified && o.verified);
		}
		return false;
	}	
	
	public String toString() {
		String s = "";
		s=s+"Answer: " + content;
		s=s+", Wrriten by: " + writer;
		s=s+", Grade: " + grade;
		s=s+", Verified by Teacher: " + verified;
		s=s+", Significant Words: " + answerWords;
		s=s+", Machine Learnable: " + learnable;
		s=s+", aid: " + _id;

		return s;
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

	public String getWriter() {
		return writer;
	}

	public Integer getAnswerWords() {
		return answerWords;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Boolean getLearnable() {
		return learnable;
	}

	public void setLearnable(Boolean learnable) {
		this.learnable = learnable;
	}

	public AnalyzeSyntaxResponse getAnalyzed_ans() {
		return Analyzed_ans;
	}

	public HashMap<String, Integer> getMap() {
		return map;
	}
}
