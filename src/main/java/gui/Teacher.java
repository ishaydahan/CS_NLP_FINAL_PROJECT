package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Teacher {

	private JFrame frame;
	private static String user;
	private JTable table;
	
	/**
	 * Launch the application.
	 * @param userName 
	 */
	public static void TeacherScreen(String userName) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					user = userName;
					Teacher window = new Teacher();
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
	public Teacher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 535, 269);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnCreateTest = new JButton("Create Test");
		btnCreateTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("creating a NEW test");
				CreateTest ct = new CreateTest();
				ct.createTest();
			}
		});
		btnCreateTest.setBounds(176, 189, 131, 23);
		frame.getContentPane().add(btnCreateTest);
		
		JLabel lblHelloTeacher = new JLabel("Hello," + user);
		lblHelloTeacher.setBounds(20, 11, 314, 29);
		frame.getContentPane().add(lblHelloTeacher);
		String col[] = {"ID","Subject"};		
		Object[][] objs = {
			
			{"1/1/58", "first a"},
			
			{"22/10/61", "second a"}
			
			};
		table = new JTable(objs, col);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(32, 61, 411, 102);
		frame.getContentPane().add(scrollPane);
		
		
		//scrollPane.setColumnHeaderView(table);
	}
}
