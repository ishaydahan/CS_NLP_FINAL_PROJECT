package com.nlp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import analyzer.StringM;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="questions2")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Question {
    
	@Id
    private String id;
    private String tid;
    @NotBlank
    @Size(max=100)
    @Indexed(unique=true)
    private String content;
	private List<String> answers = new ArrayList<String>();
    private HashSet<StringM> wrong = new HashSet<StringM>();
    private HashSet<StringM> good = new HashSet<StringM>();
    private HashSet<StringM> deletedWrong = new HashSet<StringM>();
    private HashSet<StringM> deletedGood = new HashSet<StringM>();
    private Date createdAt = new Date();

    public Question() {
        super();
    }

    public Question(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format(
                "Todo[id=%s, tid=%s, content='%s', answers='%s']",
                id, tid, content, answers.toString());
    }

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
}