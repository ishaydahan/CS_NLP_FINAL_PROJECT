package analyzer;

import com.google.api.client.repackaged.com.google.common.base.Objects;
import com.google.cloud.language.v1.Token;

public class EqualTokens {

	public Token student;
	public Token teacher;
	
	public EqualTokens(Token teacher, Token student) {
		this.student = student;
		this.teacher = teacher;
	}
	
	public boolean equals(Object other) {
		if (other instanceof EqualTokens) {
			EqualTokens o = (EqualTokens)other;
			return o.student.equals(student) && o.teacher.equals(teacher);
		}
		return false;
	}
	
	public String toString() {
		return student.getText().getContent().toString() +" - " + teacher.getText().getContent().toString();
	}
	
	public int hashCode() {
		return Objects.hashCode(student.hashCode(), teacher.hashCode());
	}
}
