package csproject.google8;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.FindIterable;

public class Teacher {
		
	private HashMap<String, String> tests = new HashMap<String, String>();

	public Teacher() {
		FindIterable<Document> list = ApiHolder.getCollection().find();
		for(Document doc : list) {
			this.tests.put(doc.getString("content"), doc.getObjectId("_id").toString());
		}
	}
	
	public Test createTest(String t) {        
        Test toAdd = new Test(new ObjectId(), t, new ArrayList<Document>());
        if (tests.containsKey(t)) {
        	System.out.println("Already has that answer!");
        	return null;
        }else {
        	tests.put(t, toAdd.getTid().toString());
    		ApiHolder.getCollection().insertOne(testToDoc(toAdd));
    		return toAdd;
    	}
	}
			
	@SuppressWarnings("unchecked")
	public Test getTest(String id) {
		FindIterable<Document> list = ApiHolder.getCollection().find();
		for(Document doc : list) {
			if (id.equals(doc.getObjectId("_id").toString())) {
				return new Test(doc.getObjectId("_id"), doc.getString("content"), (ArrayList<Document>) doc.get("questions"));
			}
		}
		return null;
	}	
	
	public void removeTest(String id) {
		ApiHolder.getCollection().deleteOne(new Document("_id", new ObjectId(id)));
		String[] kk = {""};
		tests.forEach((k,v)->{
			if(id.equals(v)) {
				kk[0]=k;
			}
		});
		tests.remove(kk[0]);
	}	
	
	private Document testToDoc(Test a) {
		return new Document().append("_id", a.getTid()).append("content", a.getContent()).append("questions", a.getQuestions());
	}

	public HashMap<String, String> getTests() {
		return tests;
	}

}