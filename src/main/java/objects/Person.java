package objects;

import java.util.ArrayList;
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
public class Person {
	
	// test name -> test Id
	private ArrayList<Test> tests = new ArrayList<Test>();
	
	public Person load() {
		tests = new ArrayList<Test>();
		
		FindIterable<Document> docTests = ApiHolder.getCollection().find();
		
		for(Document d : docTests) {
			this.tests.add(new Test(d.getObjectId("_id"), d.getString("content")));
		}
		return this;
	}
	
	public Person save() {		
		tests.forEach((t)->{
			ApiHolder.getCollection().updateOne(new Document().append("_id", t.getTid()),
					new Document("$set", new Document().append("content", new Document("content", t.getContent()))));	
		});
		return this;
	}

	/**
	 * @param t - the Test string
	 * @return Test object
	 * creates Test object, update database.
	 * will return null if the database contains same Test string.
	 */
	public Test createTest(String t) {        
        Test toAdd = new Test(new ObjectId(), t);
        if (tests.contains(toAdd)) {
        	System.err.println("Already has that test!");
        	return null;
        }else {
        	tests.add(toAdd);
    		ApiHolder.getCollection().insertOne(testToDoc(toAdd));
    		return toAdd;
    	}
	}
		
	/**
	 * @param id - test id
	 * removes all test data!
	 * @return 
	 */
	public void removeTest(Test toRemove) {
		tests.remove(toRemove);
		ApiHolder.getCollection().deleteOne(new Document("_id", toRemove.getTid()));
	}	

	/**
	 * @param id - can get it from {@link Test} getTid
	 * @return test
	 * creates test object. can take a while... will download all test data from DB.
	 * will return null if there is no such test.
	 */
	public Test getTest(String id) {
		for(Test t : tests) {
			if (id.equals(t.getTid().toString())) {
				return t;
			}
		}
		return null;
	}	
	
	
	/**
	 * @param a
	 * @return
	 * database private function.
	 */
	private Document testToDoc(Test a) {
		return new Document().append("_id", a.getTid()).append("content", a.getContent()).append("questions", a.getQuestions());
	}

	public ArrayList<Test> getTests() {
		return tests;
	}

}