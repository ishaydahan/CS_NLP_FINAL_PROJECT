package objects;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.FindIterable;

import apiHolder.ApiHolder;

/**
 * @author ishay
 * main class representing one entrance of teacher to system.
 * teacher can see all test.
 * in this point teacher will access only test names.
 * test questions and answers will be loaded on building {@link Test} object
 */
public class Teacher {
	
	// test name -> test Id
	private HashMap<String, String> tests = new HashMap<String, String>();

	public Teacher() {
		//when building we need to create list of tests.
		//will be downloaded from database.
		FindIterable<Document> list = ApiHolder.getCollection().find();
		for(Document doc : list) {
			this.tests.put(doc.getString("content"), doc.getObjectId("_id").toString());
		}
	}
	
	/**
	 * @param t - the Test string
	 * @return Test object
	 * creates Test object, update database.
	 * will return null if the database contains same Test string.
	 */
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
			
	/**
	 * @param id - can get it from {@link Test} getTid
	 * @return test
	 * creates test object. can take a while... will download all test data from DB.
	 * will return null if there is no such test.
	 */
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
	
	/**
	 * @param id - test id
	 * removes all test data!
	 */
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
	
	/**
	 * @param a
	 * @return
	 * database private function.
	 */
	private Document testToDoc(Test a) {
		return new Document().append("_id", a.getTid()).append("content", a.getContent()).append("questions", a.getQuestions());
	}

	public HashMap<String, String> getTests() {
		return tests;
	}

}