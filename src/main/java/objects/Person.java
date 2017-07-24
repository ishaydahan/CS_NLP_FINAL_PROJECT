package objects;

import java.util.ArrayList;
import java.util.List;
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
	
	private String name;
	private List<Test> tests = new ArrayList<Test>();
	
	public Person() {
	}
	
	public boolean login(String username, String password) {
		ApiHolder.getInstance().getCollection();
		long users = ApiHolder.getInstance().getUsers().count(
				new Document().append("username", username).append("password", password));
		
		if (users>0) {
			String name = ApiHolder.getInstance().users.find(
					new Document().append("username", username).append("password", password)).first().getString("name");
			String mode = ApiHolder.getInstance().users.find(
					new Document().append("username", username).append("password", password)).first().getString("mode");
			
			this.name = name;
			
			if (mode.equals("TEACHER")) ApiHolder.getInstance().teacher = true;
			return true;
		}else {
			return false;
		}
	}
	
	public Person load() {
		tests = new ArrayList<Test>();
		
		FindIterable<Document> docTests = ApiHolder.getInstance().getCollection().find();
		
		for(Document d : docTests) {
			this.tests.add(new Test(d.getObjectId("_id"), d.getString("content")));
		}
		testsToArr(tests);

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
    		ApiHolder.getInstance().getCollection().insertOne(testToDoc(toAdd));
    		return toAdd;
    	}
	}
		
	/**
	 * @param id - test id
	 * removes all test data!
	 * @return 
	 */
	public boolean removeTest(Test toRemove) {
		tests.remove(toRemove);
		ApiHolder.getInstance().getCollection().deleteOne(new Document("_id", toRemove.getTid()));
		return true;
	}	

	/**
	 * @param id - can get it from {@link Test} getTid
	 * @return test
	 * creates test object. can take a while... will download all test data from DB.
	 * will return null if there is no such test.
	 */
	public Test getTest(String id) {
		return tests.stream().filter(x->x.getTid().toString().equals(id)).findFirst().orElse(null);
	}	
	
	public List<Test> getTests() {
		return tests;
	}

	/**
	 * @param a
	 * @return
	 * database private function.
	 */
	private Document testToDoc(Test a) {
		return new Document().append("_id", a.getTid()).append("content", a.getContent()).append("questions", a.getQuestions());
	}
	
	public Object[][] testsToArr(List<Test> lst){
		Object[][] arr = new Object[lst.size()][2];
		for(int i=0; i<lst.size(); i++) {
			arr[i][0] = lst.get(i).getContent();
			arr[i][1] = lst.get(i).getTid().toString();
		}
		return arr;
	}

	public String getName() {
		return name;
	}

}