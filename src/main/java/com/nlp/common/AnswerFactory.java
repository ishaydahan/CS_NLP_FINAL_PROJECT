package com.nlp.common;

import com.nlp.models.Answer;

public class AnswerFactory {

	public Answer createAnswer(String content, int grade, String writer) {
		if (writer.equals("TEACHER")) {
			Answer ans = new Answer();
			ans.setContent(content);
			ans.setWriter(writer);
			ans.setGrade(grade);
			ans.setAnswerWords(-1);
			ans.setSyntaxable(true);
			ans.setVerified(true);
			return ans;
		}else if (writer.equals("COMPUTER")) {
			Answer ans = new Answer();
			ans.setContent(content);
			ans.setWriter(writer);
			ans.setGrade(-2);
			ans.setAnswerWords(-1);
			ans.setSyntaxable(false);
			ans.setVerified(false);
			return ans;
		}else if (writer.equals("STUDENT")) {
			Answer ans = new Answer();
			ans.setContent(content);
			ans.setWriter(writer);
			ans.setGrade(-2);
			ans.setAnswerWords(-1);
			ans.setSyntaxable(false);
			ans.setVerified(false);
			return ans;
		} else {
			return null;
		}
	}
}
