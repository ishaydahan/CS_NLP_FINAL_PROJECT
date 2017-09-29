package com.nlp.models;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.Token;
import com.nlp.analyzer.AnswerAnalyzer;
import com.nlp.common.ApiHolder;

@Document(collection="answers2")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Answer {

    @Id
	private String id ;//answer id
    private String qid;
    @NotBlank
    @Size(max=100)
	private String content;//the answer itself
    @NotBlank
	private String writer;//who wrote the answer
	private String writerId ;//writer id
    @NotNull
	private Integer grade;//submitted, Wasn't graded = -1 //not submitted, Wasn't graded = -2 //submitted, graded >-1
	private Integer answerWords;//num of significant words.
	private Boolean verified;//there is enough data to learn from this answer
	private Boolean syntaxable;//there is enough data to learn from this answer
	private Boolean syntaxMatchFound;//there is enough data to learn from this answer

    private Date createdAt = new Date();

	private HashMap<String, Integer> map = new HashMap<String, Integer>();//map to get better runtime. student->grade, relates to this answer
    private AnalyzeSyntaxResponse Analyzed_ans = null;//google analyzed syntax

    public Answer() {
        super();
    }

    public Answer(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getAnswerWords() {
		return answerWords;
	}

	public void setAnswerWords(Integer answerWords) {
		this.answerWords = answerWords;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Boolean getSyntaxable() {
		return syntaxable;
	}

	public void setSyntaxable(Boolean syntaxable) {
		this.syntaxable = syntaxable;
	}
	
	public HashMap<String, Integer> getMap() {
		return map;
	}

    public void setMap(HashMap<String, Integer> map) {
		this.map = map;
	}
    @JsonIgnore
    public AnalyzeSyntaxResponse getAnalyzed_ans() {
		return Analyzed_ans;
	}
    @JsonIgnore
    public void setAnalyzed_ans(AnalyzeSyntaxResponse analyzed_ans) {
		Analyzed_ans = analyzed_ans;
	}

//	//two answers are the same if they have the same id (wont happen)
//	//or the answer written by teacher and has same content
//	//or the answer is verified and has same content
//	public boolean equals (Object other) {
//		if (other instanceof Answer) {
//			Answer o = (Answer) other;
//			return (this.content.equals(o.content) 
//					&& !this.writer.equals("STUDENT") 
//					&& !o.writer.equals("STUDENT")     
//					);
//		}
//		return false;
//	}

	public String toString() {
		String s = "";
		s=s+"Answer: " + content;
		s=s+", Wrriten by: " + writer;
		s=s+", Grade: " + grade;
		s=s+", Significant Words: " + answerWords;
		s=s+", Teacher verified: " + verified;
		s=s+", syntaxable: " + syntaxable;
		s=s+", aid: " + id;
	
		return s;
	}

	/**
	 * @return same Answer but has called the google api Syntax checker and counted significant words
	 */
	public Answer build() {
		if (Analyzed_ans==null) {
			//google analyzer
			com.google.cloud.language.v1.Document doc = com.google.cloud.language.v1.Document.newBuilder()
		            .setContent(content).setType(Type.PLAIN_TEXT).build();

			ApiHolder.getInstance().logger.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$ " + content + " $$ google api");
			Analyzed_ans = ApiHolder.getInstance().langClient.analyzeSyntax(doc, EncodingType.UTF8);
		}
		
		if (answerWords==-1) {
			answerWords++;
			for(Token t : Analyzed_ans.getTokensList()) {
				if (!Arrays.asList(ApiHolder.getInstance().del).contains(t.getPartOfSpeech().getTag())) {
						ApiHolder.getInstance().logger.println("ANALYZER :::: Teacher parts: " + t.getText().getContent());
						ApiHolder.getInstance().logger.println("ANALYZER :::: Teacher parts: " + t.getPartOfSpeech().getTag());
						answerWords++;
				}
			}
		}
		return this;
	}
	
	public void checkAnswer(List<Answer> verified, List<Answer> syntaxable) {
		//first build the answer
		AnswerAnalyzer analyzer = new AnswerAnalyzer(this.build());
		
		int grade;
		if ((grade = analyzer.levenshteinAnalyze(verified))>-1) {
			
			this.setGrade(grade);
			
		}else if ((grade = analyzer.SyntaxAnalyze(syntaxable))>-2) {//there is no -1 option in last check. must return grade.
			
			this.setGrade(grade);
			
			if ((grade = analyzer.WordsAnalyze(verified))==ApiHolder.getInstance().MINGRADE && !this.getSyntaxMatchFound()) {
				
				this.setGrade(grade);
				
			}
		}
	}

	public Boolean getSyntaxMatchFound() {
		return syntaxMatchFound;
	}

	public void setSyntaxMatchFound(Boolean syntaxMatchFound) {
		this.syntaxMatchFound = syntaxMatchFound;
	}

}