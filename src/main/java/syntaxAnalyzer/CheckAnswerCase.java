package syntaxAnalyzer;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.google.cloud.language.v1.DependencyEdge.Label;
import com.google.cloud.language.v1.PartOfSpeech.Case;
import com.google.cloud.language.v1.PartOfSpeech.Number;
import com.google.cloud.language.v1.PartOfSpeech.Tag;
import com.google.cloud.language.v1.Token;
import com.textrazor.annotations.Entailment;

import apiHolder.ApiHolder;
import objects.Answer;

/**
 * @author ishay
 * this is the main algorithm.
 * the idea is to check the syntax tree from every direction and try to match:
 * equal tokens(words),
 * equal parts of speech(verb, noun..)
 * equal relation to parent.
 * and I help the matching by searching:
 * double meaning.
 * spelling mistakes.
 * extra information.
 * a bit less information(will cause reduce of grade)
 */
public class CheckAnswerCase {
	
	private Answer student_ans;
	private Answer teacher_ans;
	private Integer finishedGrade = 0;//max grade
	
	//Enemas for insignificant parts of sentence
	private Tag[] del = {Tag.PUNCT, Tag.UNKNOWN, Tag.ADP, Tag.X, Tag.AFFIX, Tag.DET};
	
	protected CheckAnswerCase(Answer student_ans, Answer teacher_ans)  {
		this.student_ans = student_ans;
		this.teacher_ans = teacher_ans;
	}
	
	/**
	 * @return final grade
	 * this is the only function that the user allowed to call.
	 * the function calls the equalSentences function two times - first we compare teacher to student and then student to teacher.
	 */
	protected Integer getGrade() {
		if (teacher_ans.getMap().containsKey(student_ans.getContent())) {
			ApiHolder.logger.println("using map: " + student_ans.getContent());
			return teacher_ans.getMap().get(student_ans.getContent());
		}
		//put the student answer to ensure we will check it one time only!
		teacher_ans.getMap().put(student_ans.getContent(), new Integer(0));
		Integer firstGrade = this.equalSentences1();
		
		//try to get max grade with teacher path only.
		if (firstGrade==teacher_ans.getGrade()) {
			teacher_ans.getMap().put(student_ans.getContent(), firstGrade);
			return firstGrade;
		}else {
			Integer grade = Math.max(firstGrade, this.equalSentences2());
			teacher_ans.getMap().put(student_ans.getContent(), grade);
			return grade;
		}
	}
	
	/**
	 * @return mid grade
	 * this function takes each element from teacher sentence and trys to search for match between nodes.
	 * if there is no match in one node at least(found boolean), the grade will be 0.
	 * the different between number of equal significant words will be reduced from grade.
	 */
	private Integer equalSentences1() {
		//teacher side
		boolean found = false;
		Integer grade = 0;
		for(Token teacher : teacher_ans.getAnalyzed_ans().getTokensList()) {
			//pass insignificant words
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
			if (!found) return Math.max(finishedGrade, ApiHolder.MINGRADE);
			found=false;
		}
		//the grade is maxGrade-mistakes*10(can be changed)
		return Math.max(finishedGrade, teacher_ans.getGrade()-((teacher_ans.getAnswerWords()-grade)*ApiHolder.REDUCE));
	}
	
	/**
	 * @return mid grade
	 * same as before just starting from student
	 */
	private Integer equalSentences2() {
		//student side
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
			if (!found) return Math.max(finishedGrade, ApiHolder.MINGRADE);
			found=false;
		}
		return Math.max(finishedGrade, teacher_ans.getGrade()-((teacher_ans.getAnswerWords()-grade)*ApiHolder.REDUCE));
	}

	/**
	 * @param teacher word token
	 * @param student word token
	 * @return equal or not
	 * I define equal nodes as nodes that has:
	 * 1. equal words
	 * 2. equal parents(recursively)
	 * 3. equal part of speech
	 * 4. equal relation to parent(recursively)
	 */
	private boolean equalNodes (Token teacher, Token student) {
		if (compare(teacher, student)) {
			ApiHolder.logger.println("ANALYZER :::: equal tokens: " + teacher.getText().getContent() + " + " + student.getText().getContent()); 
			Token t_father = teacher_ans.getAnalyzed_ans().getTokens(teacher.getDependencyEdge().getHeadTokenIndex());
			Token s_father = student_ans.getAnalyzed_ans().getTokens(student.getDependencyEdge().getHeadTokenIndex());
			//if there is no father, the path is equal
			if (t_father.equals(teacher) || s_father.equals(student)) return true;
			else return equalNodes(t_father, s_father);
		}else {
			//this is a little trick to pass extra information that inserted inside sentence and has relation to significant words.
			//pay intention we do it only to teacher side. doing this to student side causes unexpected result.
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
	
	/**
	 * @param teacher word token
	 * @param student word token
	 * @return equal or not
	 * checking the conditions mentioned before.
	 */
	private boolean compare (Token teacher, Token student)  {
		if (checkTokens(teacher,student) && checkParts(teacher,student) && checkRelationToParent(teacher,student) ) return true;
		else if (specialCases1(teacher, student)) return true;
		else return false;
	}
	
	/**
	 * @param teacher word token
	 * @param student word token
	 * @return equal or not
	 * there is special case when teacher wrote "people" and student wrote "they".
	 * this code handle the situation. include singular variables.
	 * pay attention this not handle the opposite situation.
	 * advise the teacher to use as "people" nouns.
	 */
	private boolean specialCases1(Token teacher, Token student) {
		if(teacher.getPartOfSpeech().getNumber().equals(Number.PLURAL) && student.getPartOfSpeech().getNumber().equals(Number.PLURAL) && student.getPartOfSpeech().getCase().equals(Case.NOMINATIVE) ) 
			return checkRelationToParent(teacher,student);
		else if(teacher.getPartOfSpeech().getNumber().equals(Number.SINGULAR) && student.getPartOfSpeech().getNumber().equals(Number.SINGULAR) && student.getPartOfSpeech().getCase().equals(Case.NOMINATIVE) ) 
			return checkRelationToParent(teacher,student);		
		return false;
	}

	/**
	 * @param teacher word token
	 * @param student word token
	 * @return equal or not
	 * check equal part of speech
	 */
	private boolean checkParts(Token teacher, Token student) {
		if (teacher.getPartOfSpeech().getTag().equals(student.getPartOfSpeech().getTag())) return true;
		return false;
	}

	
	/**
	 * @param teacher word token
	 * @param student word token
	 * @return equal or not
	 * check equal relation to parent.
	 * if one of the nodes is root, the answer will be true.
	 */
	private boolean checkRelationToParent(Token teacher, Token student) {
		if(teacher.getDependencyEdge().getLabel()==Label.ROOT || student.getDependencyEdge().getLabel()==Label.ROOT) return true;
		return teacher.getDependencyEdge().getLabel().equals(student.getDependencyEdge().getLabel());
	}

	/**
	 * @param teacher word token
	 * @param student word token
	 * @return equal or not
	 * this is a bit complex function.
	 * at first it tries to compare the two tokens.
	 * if the tokens different, it will try to calculate spelling mistakes or 
	 * switch with same contextual meaning words.
	 * the switch will create new {@link Answer} written by COMPUTER.
	 * then the new sentence will be analyzed with {@link CheckAnswerCase}.
	 * in the future - use promises.
	 */
	private boolean checkTokens(Token teacher, Token student) {
		if (teacher.getText().getContent().equals(student.getText().getContent())) return true;
		else {
			for(String sgg : ApiHolder.getSpelling(student.getText().getContent())) {
				if(sgg.toLowerCase().equals(student.getText().getContent().toLowerCase())) continue;
				if (teacher.getText().getContent().equals(sgg)) {
					String[] new_s = student_ans.getContent().split(" ");
					new_s[student_ans.getAnalyzed_ans().getTokensList().indexOf(student)] = sgg;
					String new_s1 = StringUtils.join(new_s, " ");
					
					//this map helps to avoid infinite loop.
					if (!teacher_ans.getMap().containsKey(new_s1)) {
						ApiHolder.logger.println("ANALYZER :::: spelling fixed: " + student.getText().getContent() + " >> " + sgg);
						CheckAnswerCase newCheck = new CheckAnswerCase(new Answer(new ObjectId(), new_s1, "COMPUTER", new Integer(-1), new Integer(-1), false, false).build(), teacher_ans);
						ApiHolder.logger.println("----starting new check-----");
						finishedGrade = newCheck.getGrade();
						ApiHolder.logger.println("----finished new check-----");
					}else finishedGrade = teacher_ans.getMap().get(new_s1);// we already computed this sentence
				}
			}
			for(Entailment ent : ApiHolder.getEntailmentList(student_ans.getContent())) {
				if (ent.getMatchedWords().get(0).getToken().equals(student.getText().getContent()) && teacher.getText().getContent().equals(ent.getEntailedWords().get(0)) && ent.getContextScore()>ApiHolder.MEANING){	
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
