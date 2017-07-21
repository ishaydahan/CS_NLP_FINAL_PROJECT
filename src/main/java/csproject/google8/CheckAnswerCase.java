package csproject.google8;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.google.cloud.language.v1.DependencyEdge.Label;
import com.google.cloud.language.v1.PartOfSpeech.Case;
import com.google.cloud.language.v1.PartOfSpeech.Number;
import com.google.cloud.language.v1.PartOfSpeech.Tag;
import com.google.cloud.language.v1.Token;
import com.textrazor.annotations.Entailment;

public class CheckAnswerCase {
	
	private Answer student_ans;
	private Answer teacher_ans;

	private Integer finishedGrade = 0;
	private Tag[] del = {Tag.PUNCT, Tag.UNKNOWN, Tag.ADP, Tag.X, Tag.AFFIX, Tag.DET};
	
	protected CheckAnswerCase(Answer student_ans, Answer teacher_ans)  {
		this.student_ans = student_ans;
		this.teacher_ans = teacher_ans;
	}
	
	protected Integer getGrade() {
		if (teacher_ans.getMap().containsKey(student_ans.getContent())) {
			ApiHolder.logger.println("using map: " + student_ans.getContent());
			return teacher_ans.getMap().get(student_ans.getContent());
		}
		teacher_ans.getMap().put(student_ans.getContent(), new Integer(0));
		Integer firstGrade = this.equalSentences1();
		if (firstGrade==teacher_ans.getGrade()) {
			teacher_ans.getMap().put(student_ans.getContent(), firstGrade);
			return firstGrade;
		}else {
			Integer grade = Math.max(firstGrade, this.equalSentences2());
			teacher_ans.getMap().put(student_ans.getContent(), grade);
			return grade;
		}
	}
	
	private Integer equalSentences1() {
		//teacher side
		boolean found = false;
		Integer grade = 0;
		for(Token teacher : teacher_ans.getAnalyzed_ans().getTokensList()) {
			if (Arrays.asList(del).contains(teacher.getPartOfSpeech().getTag())) continue;
			for(Token student : student_ans.getAnalyzed_ans().getTokensList()) {
				if (Arrays.asList(del).contains(student.getPartOfSpeech().getTag())) continue;
				
				ApiHolder.logger.println("ANALYZER :::: checking " + teacher.getText().getContent() + " " + student.getText().getContent());
				if (equalNodes(teacher, student)) {
					found=true;
					grade++;
					break;
				}
			}
			if (!found) return Math.max(finishedGrade, 0);
			found=false;
		}
		return Math.max(finishedGrade, teacher_ans.getGrade()-((teacher_ans.getAnswerWords()-grade)*10));
	}
	
	private Integer equalSentences2() {
		//teacher side
		boolean found=false;
		Integer grade = 0;
		for(Token student : student_ans.getAnalyzed_ans().getTokensList()) {
			if (Arrays.asList(del).contains(student.getPartOfSpeech().getTag())) continue;
			for(Token teacher : teacher_ans.getAnalyzed_ans().getTokensList()) {
				if (Arrays.asList(del).contains(teacher.getPartOfSpeech().getTag())) continue;
				
				ApiHolder.logger.println("ANALYZER :::: checking " + teacher.getText().getContent() + " " + student.getText().getContent());
				if (equalNodes(teacher, student)) {
					found=true;
					grade++;
					break;
				}
			}
			if (!found) return Math.max(finishedGrade, 0);
			found=false;
		}
		return Math.max(finishedGrade, teacher_ans.getGrade()-((teacher_ans.getAnswerWords()-grade)*10));
	}

	private boolean equalNodes (Token teacher, Token student) {
		if (compare(teacher, student)) {
			ApiHolder.logger.println("ANALYZER :::: equal tokens: " + teacher.getText().getContent() + " + " + student.getText().getContent()); 
			Token t_father = teacher_ans.getAnalyzed_ans().getTokens(teacher.getDependencyEdge().getHeadTokenIndex());
			Token s_father = student_ans.getAnalyzed_ans().getTokens(student.getDependencyEdge().getHeadTokenIndex());
			if (t_father.equals(teacher) || s_father.equals(student)) return true;
			else return equalNodes(t_father, s_father);
		}else {
			int index1 = teacher_ans.getAnalyzed_ans().getTokensList().indexOf(teacher);
			for(Token tt : teacher_ans.getAnalyzed_ans().getTokensList()) {
				if(tt.getDependencyEdge().getHeadTokenIndex()==index1 && tt.getDependencyEdge().getLabel()!=Label.ROOT) {
					return equalNodes(tt, student);
				}
			}
			ApiHolder.logger.println("ANALYZER :::: not equal tokens: " + teacher.getText().getContent() + " + " + student.getText().getContent()); 
			return false;
		}
	}
	
	private boolean compare (Token teacher, Token student)  {
		try {
			if (checkTokens(teacher,student) && checkParts(teacher,student) && checkRelationToParent(teacher,student) ) return true;
			else if (specialCases1(teacher, student)) return true;
			else return false;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean specialCases1(Token teacher, Token student) {
		if(teacher.getPartOfSpeech().getNumber().equals(Number.PLURAL) && student.getPartOfSpeech().getNumber().equals(Number.PLURAL) && student.getPartOfSpeech().getCase().equals(Case.NOMINATIVE) ) 
			return checkRelationToParent(teacher,student);
		else if(teacher.getPartOfSpeech().getNumber().equals(Number.SINGULAR) && student.getPartOfSpeech().getNumber().equals(Number.SINGULAR) && student.getPartOfSpeech().getCase().equals(Case.NOMINATIVE) ) 
			return checkRelationToParent(teacher,student);		
		return false;
	}

	private boolean checkParts(Token teacher, Token student) {
		if (teacher.getPartOfSpeech().getTag().equals(student.getPartOfSpeech().getTag())) return true;
		return false;
	}

	
	private boolean checkRelationToParent(Token teacher, Token student) {
		if(teacher.getDependencyEdge().getLabel()==Label.ROOT || student.getDependencyEdge().getLabel()==Label.ROOT) return true;
		return teacher.getDependencyEdge().getLabel().equals(student.getDependencyEdge().getLabel());
	}

	private boolean checkTokens(Token teacher, Token student) throws IOException {
		if (teacher.getText().getContent().equals(student.getText().getContent())) return true;
		else {
			for(String sgg : ApiHolder.getSpelling(student.getText().getContent())) {
				if(sgg.toLowerCase().equals(student.getText().getContent().toLowerCase())) continue;
				if (teacher.getText().getContent().equals(sgg)) {
					String[] new_s = student_ans.getContent().split(" ");
					new_s[student_ans.getAnalyzed_ans().getTokensList().indexOf(student)] = sgg;
					String new_s1 = StringUtils.join(new_s, " ");
					
					if (!teacher_ans.getMap().containsKey(new_s1)) {
						ApiHolder.logger.println("ANALYZER :::: spelling fixed: " + student.getText().getContent() + " >> " + sgg);
						CheckAnswerCase newCheck = new CheckAnswerCase(new Answer(new ObjectId(), new_s1, "COMPUTER", new Integer(-1), new Integer(-1), false, false).build(), teacher_ans);
						ApiHolder.logger.println("----starting new check-----");
						finishedGrade = newCheck.getGrade();
						ApiHolder.logger.println("----finished new check-----");
					}else finishedGrade = teacher_ans.getMap().get(new_s1);
				}
			}
			for(Entailment ent : ApiHolder.getEntailmentList(student_ans.getContent())) {
				if (ent.getMatchedWords().get(0).getToken().equals(student.getText().getContent()) && teacher.getText().getContent().equals(ent.getEntailedWords().get(0))) {	
					String[] new_s = student_ans.getContent().split(" ");
					new_s[student_ans.getAnalyzed_ans().getTokensList().indexOf(student)] = ent.getEntailedWords().get(0);
					String new_s1 = StringUtils.join(new_s, " ");
					
					if (!teacher_ans.getMap().containsKey(new_s1)) {
						ApiHolder.logger.println("ANALYZER :::: spelling fixed: " + student.getText().getContent() + " >> " + ent.getEntailedWords().get(0));
						CheckAnswerCase newCheck = new CheckAnswerCase(new Answer(new ObjectId(), new_s1, "COMPUTER", new Integer(-1), new Integer(-1), false, false).build(), teacher_ans);
						ApiHolder.logger.println("----starting new check-----");
						finishedGrade = newCheck.getGrade();
						ApiHolder.logger.println("----finished new check-----");
					}else finishedGrade = teacher_ans.getMap().get(new_s1);
				}
			}
		}
		return false;
	}	
}
