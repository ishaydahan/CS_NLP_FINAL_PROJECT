package objects;

import org.bson.types.ObjectId;

public class QuestionFactory {

	public Answer create(String content, int grade, Writer writer) {
		if (writer.equals(Writer.TEACHER)) {
			return new Answer(new ObjectId(), content, writer, grade, new Integer(-1), true, true);
		}else if (writer.equals(Writer.COMPUTER)) {
			return new Answer(new ObjectId(), content, writer, new Integer(-1), new Integer(-1), false, false);
		}else if (writer.equals(Writer.STUDENT)) {
			return new Answer(new ObjectId(), content, writer, new Integer(-1), new Integer(-1), false, false);
		} else {
			return null;
		}
	}
}
