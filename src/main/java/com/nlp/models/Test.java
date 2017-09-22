package com.nlp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tests2")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Test {
    
	@Id
    private String id;

    @NotBlank
    @Size(max=100)
    @Indexed(unique=true)
    private String content;

	private List<String> questions = new ArrayList<String>();

    private Date createdAt = new Date();

    public Test() {
        super();
    }

    public Test(String content) {
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
                "Todo[id=%s, content='%s', questions='%s']",
                id, content, questions.toString());
    }

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}
}