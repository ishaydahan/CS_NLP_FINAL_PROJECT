package objects;

import org.bson.types.ObjectId;

import apiHolder.ApiHolder;

public class Factory {

	public Answer createAnswer(String content, int grade, Writer writer) {
		if (writer.equals(Writer.TEACHER)) {
			return new Answer(new ObjectId(), content, writer, ApiHolder.getInstance().userId, grade, new Integer(-1), true, true);
		}else if (writer.equals(Writer.COMPUTER)) {
			return new Answer(new ObjectId(), content, writer, ApiHolder.getInstance().userId, new Integer(-2), new Integer(-1), false, false);
		}else if (writer.equals(Writer.STUDENT)) {
			return new Answer(new ObjectId(), content, writer, ApiHolder.getInstance().userId, new Integer(-2), new Integer(-1), false, false);
		} else {
			return null;
		}
	}
}
