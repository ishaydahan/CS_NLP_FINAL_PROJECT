package gui;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import apiHolder.ApiHolder;
import objects.Answer;
import objects.Person;
import objects.Question;
import objects.Test;
import javax.swing.JProgressBar;
import java.awt.Font;

public class CSMain {

	private JFrame frame;
	static JTextField textFieldUser;
	static JPasswordField passwordField;
	static String userName;
	static String password;
	static Person p;
	static Test t;
	static Question q;
	static Answer a;
	static JProgressBar progressBar;
	static boolean conected = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CSMain window = new CSMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
		CSMain window = new CSMain();
		window.frame.setVisible(true);
		ApiHolder.getInstance(); 
		
        MongoClientURI uri  = new MongoClientURI("mongodb://ishaydah:nlpuser@ds161012.mlab.com:61012/csproject"); 
        ApiHolder.getInstance() .client = new MongoClient(uri);
        ApiHolder.getInstance().db = ApiHolder.getInstance().client.getDatabase(uri.getDatabase());
        ApiHolder.getInstance().collection = ApiHolder.getInstance().db.getCollection("tests"); 
        ApiHolder.getInstance().users = ApiHolder.getInstance().db.getCollection("users"); 
		conected=true;
	}

	/**
	 * Create the application.
	 */
	public CSMain() {
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
		panel.setFont(new Font("Tahoma", Font.BOLD, 13));
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
		
		progressBar = new JProgressBar();
		progressBar.setBounds(58, 81, 146, 14);
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setVisible(true);
		panel.add(progressBar);	
		        
		JButton btnLogin = new JButton("ok");
		btnLogin.setBounds(124, 125, 89, 23);
		frame.getContentPane().add(btnLogin);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

		    	CSMain.userName = CSMain.textFieldUser.getText();
				CSMain.password = CSMain.passwordField.getText();
				
				p = new Person();
				System.out.println(userName + password);
				progressBar.setValue((100/6)*1);
				progressBar.setValue((100/6)*2);
				progressBar.setValue((100/6)*3);
				progressBar.setValue((100/6)*4);
				progressBar.setValue((100/6)*5);
				boolean userLogin = p.login(userName, password);		
				progressBar.setValue(100);					
				if(userLogin){
					p.load();
						if (ApiHolder.getInstance().teacher){
							TeacherFrame t = new TeacherFrame();
							t.setVisible(true);;
						} 
						else {
							StudentFrame s = new StudentFrame();
							s.setVisible(true);
						}
						frame.dispose();

			}	else {					
				JOptionPane.showMessageDialog(null, "Login failed, check you user name and password and try again");
			}
			}
		});
	}
}