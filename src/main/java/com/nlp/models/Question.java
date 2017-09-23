package com.nlp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nlp.analyzer.LevenshteinDistance;
import com.nlp.analyzer.StringM;

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

	public HashSet<StringM> getWrong() {
		return wrong;
	}

	public void setWrong(HashSet<StringM> wrong) {
		this.wrong = wrong;
	}

	public HashSet<StringM> getGood() {
		return good;
	}

	public void setGood(HashSet<StringM> good) {
		this.good = good;
	}

	public HashSet<StringM> getDeletedWrong() {
		return deletedWrong;
	}

	public void setDeletedWrong(HashSet<StringM> deletedWrong) {
		this.deletedWrong = deletedWrong;
	}

	public HashSet<StringM> getDeletedGood() {
		return deletedGood;
	}

	public void setDeletedGood(HashSet<StringM> deletedGood) {
		this.deletedGood = deletedGood;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void check(String st, boolean grade) {
		StringM string = new StringM(st);
		while(string.underlyingString.length() != 0 ) {
			int i = string.underlyingString.indexOf(" ");
			if(i != -1) {
				String t = string.underlyingString.substring(0, i);
				StringM temp = new StringM(t);
				String s = string.underlyingString.substring(i+1);
				string = new StringM(s);
				if(grade) {
					boolean one = wrong.contains(temp);
					if(one) {
						getDeletedWrong().add(temp);
						wrong.remove(temp);
					} else if(getDeletedWrong().contains(temp)) {
					} else {
						good.add(temp);						
					}
				} else {
					if(good.contains(temp)) {
						deletedGood.add(temp);
						good.remove(temp);
					} else if(deletedGood.contains(temp)) {
					} else {
						wrong.add(temp);						
					}
				}
			} else {
				if(grade) {
					if(wrong.contains(string)) {
						getDeletedWrong().add(string);
						wrong.remove(string);
					} else if(getDeletedWrong().contains(string)) {
					} else {
						good.add(string);						
					}
				} else {
					if(good.contains(string)) {
						deletedGood.add(string);
						good.remove(string);
					} else if(deletedGood.contains(string)) {
					} else {
						wrong.add(string);						
					}
				} break;
			}
		}
	}


	public void learningCheck(String st, boolean grade) {
		StringM string = new StringM(st);
		while(string.underlyingString.length() != 0 ) {
			int i = string.underlyingString.indexOf(" ");
			if(i != -1) {
				String t = string.underlyingString.substring(0, i);
				StringM temp = new StringM(t);
				String s = string.underlyingString.substring(i+1);
				string = new StringM(s);
				if(grade) {
					boolean one = wrong.contains(temp);
					if(one) {
						deletedWrong.add(temp);
						wrong.remove(temp);
					} else if(deletedWrong.contains(temp)) {
					} else {
						good.add(temp);						
					}
				} else {
					if(good.contains(temp)) {
						deletedGood.add(temp);
						good.remove(temp);
					} else if(deletedGood.contains(temp)) {
					} else {
						wrong.add(temp);						
					}
				}
			} else {
				if(grade) {
					if(wrong.contains(string)) {
						deletedWrong.add(string);
						wrong.remove(string);
					} else if(deletedWrong.contains(string)) {
					} else {
						good.add(string);						
					}
				} else {
					if(good.contains(string)) {
						deletedGood.add(string);
						good.remove(string);
					} else if(deletedGood.contains(string)) {
					} else {
						wrong.add(string);						
					}
				} break;
			}
		}
	}


public Boolean checkWord(String string) {
	Iterator<StringM> itr_good = good.iterator();
	Iterator<StringM> itr_wrong = wrong.iterator();
	String[] arr = string.split(" ");   
	 for ( String ss : arr) {
		 	itr_good = good.iterator();
			while(itr_good.hasNext()){ 
				int a = LevenshteinDistance.computeLevenshteinDistance(ss, itr_good.next().underlyingString);
				if (a <= 3) {
					return true;
				}
			}
	  }
	for ( String ss : arr) {
		itr_wrong = wrong.iterator();
		while(itr_wrong.hasNext()){ 
			int a = LevenshteinDistance.computeLevenshteinDistance(ss, itr_wrong.next().underlyingString);
			if (a <= 3) {
				return false;
			}
		}
	}
	return true;	
}
}