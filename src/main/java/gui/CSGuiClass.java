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

import objects.Person;

public class CSGuiClass {

	private JFrame frame;
	private JTextField textFieldUser;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CSGuiClass window = new CSGuiClass();
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
	public CSGuiClass() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
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
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(82, 79, 174, 20);
		panel.add(comboBox);
		comboBox.addItem("Student");
		comboBox.addItem("Teacher");
		
		JButton btnLogin = new JButton("ok");
		btnLogin.setBounds(124, 125, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					int mode = comboBox.getSelectedIndex(); //0 - student , 1 - teacher
					String userName = textFieldUser.getText();
					String password = passwordField.getText();
					
					Person p = new Person().load();
//					boolean login = p.checkUser(mode,userName,password);
	
//				if(login){
						if (mode == 1){
							Teacher t = new Teacher(p);
							t.TeacherScreen(userName);
						} 
	//					else {
	//						Student s = new Student();
	//						s.setVisible(true);
	//					}
	//				} else{
	//					JOptionPane.showMessageDialog(null, "Login failed, check you user name and password and try again");
	//				}
			}
		});

	}
}