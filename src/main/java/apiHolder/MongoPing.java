//package apiHolder;
//
//import org.bson.Document;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//public class MongoPing extends Thread {
//	
//	public MongoCollection<Document> collection;
//	MongoClient client;
//	
//    public MongoPing() {
//        MongoClientURI uri  = new MongoClientURI("mongodb://ishaydah:nlpuser@ds161012.mlab.com:61012/csproject"); 
//        client = new MongoClient(uri);
//		MongoDatabase db = client.getDatabase(uri.getDatabase());
//		collection = db.getCollection("tests"); 
//    }
//    
//    public void run() {
//    	try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			client.close();
//		}
//    	
//    	collection.count();
//    	
//    }
//
//}
