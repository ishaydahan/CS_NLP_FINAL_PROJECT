package analyzer;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;

import com.google.cloud.language.v1.DependencyEdge.Label;
import com.google.cloud.language.v1.PartOfSpeech.Case;
import com.google.cloud.language.v1.PartOfSpeech.Number;
import com.google.cloud.language.v1.PartOfSpeech.Tag;
import com.google.cloud.language.v1.Token;
import com.textrazor.annotations.Entailment;

import apiHolder.ApiHolder;
import objects.Answer;
import objects.Writer;

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
	
	private Answer teacher_ans;
	private Answer student_ans;
	private int finishedGrade = 0;//max grade
	
	private HashSet <Token> set = new HashSet <Token>();//set to remember already visited nodes
	private HashSet <EqualTokens> equalSet = new HashSet <EqualTokens>();//set to remember equalnodes

	//Enemas for insignificant parts of sentence
	private Tag[] del = {Tag.PUNCT, Tag.UNKNOWN, 
			Tag.ADP, 
			Tag.X, Tag.AFFIX, Tag.DET};
	
	protected CheckAnswerCase(Answer student_ans, Answer teacher_ans)  {
		this.student_ans = student_ans;
		this.teacher_ans = teacher_ans;
	}
	
	/**
	 * @return final grade
	 * this is the only function that the user allowed to call.
	 * the function calls the equalSentences function two times - first we compare teacher to student and then student to teacher.
	 */
	protected int getGrade() {
		ApiHolder.getInstance().logger.println("getGrade :::: TEACHER :" + teacher_ans);
		ApiHolder.getInstance().logger.println("getGrade :::: STUDENT :" + student_ans);
		
		//Obvious case
		if (teacher_ans.getAnswerWords()==0) return 0;
		if (student_ans.getAnswerWords()==0) return 0;

		if (teacher_ans.getMap().containsKey(student_ans.getContent())) {
			ApiHolder.getInstance().logger.println("using map: " + student_ans.getContent());
			ApiHolder.getInstance().logger.println("grade: " + teacher_ans.getMap().get(student_ans.getContent()));
			return teacher_ans.getMap().get(student_ans.getContent());
		}
		
		//put the student answer to ensure we will check it one time only!
		teacher_ans.getMap().put(student_ans.getContent(), 0);
		
		//start calculating comparing student to teacher
		int firstGrade = this.equalSentences(teacher_ans, student_ans, false);
		
		//try to get max grade with teacher path only.
		if (firstGrade==teacher_ans.getGrade()) {
			teacher_ans.getMap().put(student_ans.getContent(), firstGrade);
			ApiHolder.getInstance().logger.println("finished grading: " + student_ans.getContent());
			ApiHolder.getInstance().logger.println("grade: " + firstGrade);
			return firstGrade;
		}
		
		int secondGrade = this.equalSentences(student_ans, teacher_ans, true);
		int grade = Math.max(firstGrade, secondGrade);
		ApiHolder.getInstance().logger.println("finished grading: " + student_ans.getContent());
		ApiHolder.getInstance().logger.println("grade: " + grade);
		teacher_ans.getMap().put(student_ans.getContent(), grade);
		return grade;
	}
	
	/**
	 * @return mid grade
	 * this function takes each element from teacher sentence and trys to search for match between nodes.
	 * if there is no match in one node at least(found boolean), the grade will be 0.
	 * the different between number of equal significant words will be reduced from grade.
	 */
	private int equalSentences(Answer constant, Answer compareTo, boolean OppMode) {
		if(OppMode) ApiHolder.getInstance().logger.println("ANALYZER :::: COMPARING TEACHER TO STUDENT");
		else ApiHolder.getInstance().logger.println("ANALYZER :::: COMPARING STUDENT TO TEACHER");
		
		boolean found = false;
		equalSet=new HashSet<EqualTokens>();
		set = new HashSet <Token>();

		for(Token consta : constant.getAnalyzed_ans().getTokensList()) {

			if (Arrays.asList(del).contains(consta.getPartOfSpeech().getTag())) {
				ApiHolder.getInstance().logger.println("equalSentences :::: we have insignificant word: " + consta.getText().getContent());
				continue;
			}
			
			for(Token comp : compareTo.getAnalyzed_ans().getTokensList()) {
				
				if (Arrays.asList(del).contains(comp.getPartOfSpeech().getTag())) {
					ApiHolder.getInstance().logger.println("equalSentences :::: we have insignificant word: " + comp.getText().getContent());
					continue;
				}
				
				Token teacher;
				Token student;
				if(OppMode) teacher = comp; else teacher = consta;
				if(OppMode) student = consta; else student = comp;
				
				ApiHolder.getInstance().logger.println("equalSentences :::: checking " + teacher.getText().getContent() + " >> COMPARE TO >> " + student.getText().getContent());
				
				if (!set.contains(comp)) {
					if (equalNodes(constant, compareTo, teacher, student, OppMode)) {
						set.add(comp);
						found=true;
						ApiHolder.getInstance().logger.println("equalSentences1 :::: finished path check, equal: " + teacher.getText().getContent() + " " + student.getText().getContent());
						break;
					}else {
						ApiHolder.getInstance().logger.println("equalSentences1 :::: One path check, NOT equal: " + teacher.getText().getContent() + " " + student.getText().getContent());
					}
				}else {
					ApiHolder.getInstance().logger.println("equalSentences1 :::: One path check, Set contains already: " + comp.getText().getContent());
				}
			}
			if (!found) {
				ApiHolder.getInstance().logger.println("equalSentences1 :::: finished path check WITH FOUND=FALSE! for constant word: " + consta.getText().getContent());
				ApiHolder.getInstance().logger.println("equalSentences1 :::: finishedGrade: " + finishedGrade);
				ApiHolder.getInstance().logger.println("equalSentences1 :::: grade: " + Math.max(finishedGrade, ApiHolder.getInstance().MINGRADE));
				return Math.max(finishedGrade, ApiHolder.getInstance().MINGRADE);
			}
			found=false;
		}
		//the grade is maxGrade-mistakes*10(can be changed)
		ApiHolder.getInstance().logger.println("equalSentences1 :::: finished path check WITH FOUND=TRUE: " + constant.getContent() + " " + compareTo.getContent());
		ApiHolder.getInstance().logger.println("equalSentences1 :::: equalset = : " + equalSet);
		ApiHolder.getInstance().logger.println("equalSentences1 :::: finishedGrade: " + finishedGrade);
		ApiHolder.getInstance().logger.println("equalSentences1 :::: teacher_ans.getAnswerWords(): " + teacher_ans.getAnswerWords());
		ApiHolder.getInstance().logger.println("equalSentences1 :::: equalSet.size(): " + equalSet.size());
		
//		int finalGrade = Math.max(finishedGrade, teacher_ans.getGrade()-(Math.abs(teacher_ans.getAnswerWords()-equalSet.size())*ApiHolder.getInstance().REDUCE));
		int finalGrade = (int) Math.max(finishedGrade, ((double)equalSet.size()/teacher_ans.getAnswerWords())*teacher_ans.getGrade().intValue());
		finalGrade = Math.min(finalGrade, ApiHolder.getInstance().MAXGRADE);
		if (student_ans.getWriter().equals(Writer.COMPUTER)) return finalGrade-ApiHolder.getInstance().COMP;
		else return finalGrade;
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
	private boolean equalNodes (Answer constant, Answer compareTo, Token teacher, Token student, boolean OppMode) {
		if (Arrays.asList(del).contains(teacher.getPartOfSpeech().getTag()) || Arrays.asList(del).contains(student.getPartOfSpeech().getTag())) {
			ApiHolder.getInstance().logger.println("equalNodes :::: not intresting: " + teacher.getText().getContent() + " + " + student.getText().getContent()); 
			return true;
		}

		if (compare(teacher, student)) {
			ApiHolder.getInstance().logger.println("equalNodes :::: equal tokens: " + teacher.getText().getContent() + " + " + student.getText().getContent()); 
			equalSet.add(new EqualTokens(teacher, student));
			ApiHolder.getInstance().logger.println(equalSet);
			Token t_father = teacher_ans.getAnalyzed_ans().getTokens(teacher.getDependencyEdge().getHeadTokenIndex());
			Token s_father = student_ans.getAnalyzed_ans().getTokens(student.getDependencyEdge().getHeadTokenIndex());

			//if there is no father, the path is equal
			if (t_father.equals(teacher) || s_father.equals(student)) {
				ApiHolder.getInstance().logger.println("equalNodes :::: no father... returning true. bye"); 
				return true;
			}else{
				ApiHolder.getInstance().logger.println("equalNodes :::: there are fathers: " + t_father.getText().getContent() + " + " + s_father.getText().getContent()); 
				return equalNodes(constant, compareTo, t_father, s_father, OppMode);
			}
		}else {
			
			Token var;
			if(OppMode) var = teacher; else var = student;

			//this is a little trick to pass extra information that inserted inside sentence and has relation to significant words.
			int index1 = compareTo.getAnalyzed_ans().getTokensList().indexOf(var);
			int i=0;
			for(Token trick : compareTo.getAnalyzed_ans().getTokensList()) {
				i++;
				if (i-2<index1) continue;
				ApiHolder.getInstance().logger.println("equalNodes :::: trick loop var: " + var.getText().getContent() + " +to tricked>>: " + trick.getText().getContent()); 
				if(trick.getDependencyEdge().getHeadTokenIndex()==index1 && trick.getDependencyEdge().getLabel()!=Label.ROOT) {
					ApiHolder.getInstance().logger.println("equalNodes :::: trick try var: " + var.getText().getContent() + " +to tricked>>: " + trick.getText().getContent()); 
					if (OppMode) {
						if (equalNodes(constant, compareTo, trick, student, OppMode)) return true;
					}else{
						if (equalNodes(constant, compareTo, teacher, trick, OppMode)) return true;
					}
				}
			}
			
			ApiHolder.getInstance().logger.println("equalNodes :::: not equal tokens: " + teacher.getText().getContent() + " + " + student.getText().getContent()); 
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
		if (checkTokens(teacher,student)) {
			ApiHolder.getInstance().logger.println("compare :::: passed checkTokens V "+ teacher.getText().getContent() + " + " + student.getText().getContent()); 
				if (checkParts(teacher,student)) {
					ApiHolder.getInstance().logger.println("compare :::: passed checkParts V "+ teacher.getText().getContent() + " + " + student.getText().getContent()); 
					if (checkRelationToParent(teacher,student)) {
						ApiHolder.getInstance().logger.println("compare :::: passed checkRelationToParent V "+ teacher.getText().getContent() + " + " + student.getText().getContent()); 
						return true;
					}
				}
		}else if (specialCases1(teacher, student)) {
			ApiHolder.getInstance().logger.println("compare :::: special case1 V "+ teacher.getText().getContent() + " + " + student.getText().getContent()); 
			return true;
		}
		else return false;
		return false;
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
		if (teacher.getText().getContent().equals(student.getText().getContent()) || teacher.getLemma().equals(student.getLemma())) return true;
		else {

			for(String sgg : ApiHolder.getInstance().getSpelling(student.getText().getContent())) {
				if(sgg.toLowerCase().equals(student.getText().getContent().toLowerCase())) continue;
				if (teacher.getText().getContent().equals(sgg) || LevenshteinDistance.computeLevenshteinDistance(teacher.getText().getContent(), sgg)<ApiHolder.getInstance().LEVENSHTEIN) {
//					System.out.println(teacher.getText().getContent() + "  " + student.getText().getContent());

					String[] new_s = student_ans.getContent().split(" ");
					new_s[student_ans.getAnalyzed_ans().getTokensList().indexOf(student)] = sgg;
					String new_s1 = StringUtils.join(new_s, " ");

					//this map helps to avoid infinite loop.
					if (!teacher_ans.getMap().containsKey(new_s1)) {
						ApiHolder.getInstance().logger.println("checkTokens :::: spelling fixed: " + student.getText().getContent() + " >> " + sgg);
						CheckAnswerCase newCheck = new CheckAnswerCase(ApiHolder.getInstance().factory.createAnswer(new_s1, 0, Writer.COMPUTER).build(), teacher_ans);
						ApiHolder.getInstance().logger.println("----starting new check-----");
						finishedGrade = Math.max(finishedGrade, newCheck.getGrade());
						ApiHolder.getInstance().logger.println("----finished new check-----");
					}else finishedGrade = teacher_ans.getMap().get(new_s1)-ApiHolder.getInstance().COMP;// we already computed this sentence
				}
			}
			for(Entailment ent : ApiHolder.getInstance().getEntailmentList(student_ans.getContent())) {
				if (ent.getMatchedWords().get(0).getToken().equals(student.getText().getContent()) && teacher.getText().getContent().equals(ent.getEntailedWords().get(0)) && ent.getContextScore()>ApiHolder.getInstance().MEANING){	
					String[] new_s = student_ans.getContent().split(" ");
					new_s[student_ans.getAnalyzed_ans().getTokensList().indexOf(student)] = ent.getEntailedWords().get(0);
					String new_s1 = StringUtils.join(new_s, " ");
					
					if (!teacher_ans.getMap().containsKey(new_s1)) {
						ApiHolder.getInstance().logger.println("checkTokens :::: meaning fixed: " + student.getText().getContent() + " >> " + ent.getEntailedWords().get(0));
						CheckAnswerCase newCheck = new CheckAnswerCase(ApiHolder.getInstance().factory.createAnswer(new_s1, 0, Writer.COMPUTER).build(), teacher_ans);
						ApiHolder.getInstance().logger.println("----starting new check-----");
						finishedGrade = Math.max(finishedGrade, newCheck.getGrade());
						ApiHolder.getInstance().logger.println("----finished new check-----");
					}else finishedGrade = teacher_ans.getMap().get(new_s1)-ApiHolder.getInstance().COMP;
				}
			}
		}
		return false;
	}	
}
