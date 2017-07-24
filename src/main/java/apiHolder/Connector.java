//package apiHolder;
//
//import java.util.Date;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//
//import teacherConsoleApi.ConsoleProgram;
//
//class Connector implements Callable<Boolean> {
//    public Boolean call() throws Exception {
//    	Thread.sleep(3000);
//		System.out.println("**   connecting to database...   **");
//        MongoClientURI uri  = new MongoClientURI("mongodb://ishaydah:nlpuser@ds161012.mlab.com:61012/csproject"); 
//        ConsoleProgram.holder.client = new MongoClient(uri);
//        ConsoleProgram.holder.db = ConsoleProgram.holder.client.getDatabase(uri.getDatabase());
//        return true;
//    }
//}
//   
//class login implements Callable<Boolean> {
//    public Boolean call() throws Exception {
//    	Thread.sleep(3000);
//		System.out.println("**   connecting to database...   **");
//        MongoClientURI uri  = new MongoClientURI("mongodb://ishaydah:nlpuser@ds161012.mlab.com:61012/csproject"); 
//        ConsoleProgram.holder.client = new MongoClient(uri);
//        ConsoleProgram.holder.db = ConsoleProgram.holder.client.getDatabase(uri.getDatabase());
//        return true;
//    }
//}
//
//    
//    
//    
//    public static void main(String args[]){
//        //Get ExecutorService from Executors utility class, thread pool size is 10
//        ExecutorService executor = Executors.newFixedThreadPool(1);
//        //Create MyCallable instance
//        Callable<Boolean> callable = new Connector();
//        
//        
//            //submit Callable tasks to be executed by thread pool
//            Future<Boolean> future = executor.submit(callable);
//
//            try {
//                //print the return value of Future, notice the output delay in console
//                // because Future.get() waits for task to get completed
//                System.out.println(new Date()+ "::"+future.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            
//            System.out.println("aaa");
//    
//    }
//
//}
