package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import apiHolder.ApiHolder;
import objects.Answer;
import objects.Person;
import objects.Question;
import objects.Test;
import teacherConsoleApi.ConsoleProgram;

import javax.swing.JProgressBar;

class Connector implements Callable<Boolean> {
    public Boolean call() throws Exception {
        MongoClientURI uri  = new MongoClientURI("mongodb://ishaydah:nlpuser@ds161012.mlab.com:61012/csproject"); 
        ApiHolder.getInstance() .client = new MongoClient(uri);
        ApiHolder.getInstance().db = ConsoleProgram.holder.client.getDatabase(uri.getDatabase());
        ApiHolder.getInstance().collection = ConsoleProgram.holder.db.getCollection("tests"); 
        ApiHolder.getInstance().users = ConsoleProgram.holder.db.getCollection("users"); 
        return true;
    }
}
   
class login implements Callable<Boolean> {
    public Boolean call() throws Exception {
    	CSGui.userName = CSGui.textFieldUser.getText();
		CSGui.password = CSGui.passwordField.getText();
		return true;
    }
}

public class CSGui {

	private JFrame frame;
	static JTextField textFieldUser;
	static JPasswordField passwordField;
	static String userName;
	static String password;
	static Person p;
	static Test t;
	static Question q;
	static Answer a;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CSGui window = new CSGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CSGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Exams Checker");
		frame.setBounds(100, 100, 350, 198);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(42, 11, 262, 106);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(6, 20, 66, 14);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(6, 49, 66, 14);
		panel.add(lblPassword);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(82, 16, 174, 23);
		panel.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(82, 45, 174, 23);
		panel.add(passwordField);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(58, 81, 146, 14);
		panel.add(progressBar);
		//TODO progress bar when click ok
		
		JButton btnLogin = new JButton("ok");
		btnLogin.setBounds(124, 125, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        //Get ExecutorService from Executors utility class, thread pool size is 10
		        ExecutorService executor = Executors.newFixedThreadPool(2);
		        //Create MyCallable instance
		        Callable<Boolean> Connector = new Connector();
		        Callable<Boolean> login = new login();
		        //submit Callable tasks to be executed by thread pool
		        Future<Boolean> future1 = executor.submit(Connector);
		        Future<Boolean> future2 = executor.submit(login);
		
		        try {
					future1.get();
					future2.get();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ExecutionException e2) {
					e2.printStackTrace();
				}

				p = new Person();
				boolean userLogin = p.login(userName, password);		
					
				if(userLogin){
					p.load();
						if (ApiHolder.getInstance().teacher){
							TeacherFrame t = new TeacherFrame();
							t.setVisible(true);;
						} 
	//					else {
	//						Student s = new Student(p);
	//						s.setVisible(true);
	//					}
	//				} 
						frame.dispose();
;
			}	else {					
				JOptionPane.showMessageDialog(null, "Login failed, check you user name and password and try again");
			}
			}
		});
	}
}