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
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import apiHolder.ApiHolder;
import objects.Answer;
import objects.Person;
import objects.Question;
import objects.Test;
import javax.swing.JProgressBar;

public class CSGui {

	private JFrame frame;
	private JTextField textFieldUser;
	private JPasswordField passwordField;
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
					String userName = textFieldUser.getText();
					String password = passwordField.getText();
					p = new Person();
					boolean login = p.login(userName, password);
				if(login){
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