package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import objects.Person;

public class TeacherGui {

	private JFrame frame;
	private static String user;
	private JTable table;
	private Person p;
	
	/**
	 * Launch the application.
	 * @param userName 
	 * @param person 
	 */
	public static void TeacherScreen(String userName, Person person) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					user = userName;
					TeacherGui window = new TeacherGui(person);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param p 
	 */
	public TeacherGui(Person person) {
		p = person;
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
				CreateTestGui ct = new CreateTestGui();
				ct.createTest();
			}
		});
		btnCreateTest.setBounds(176, 189, 131, 23);
		frame.getContentPane().add(btnCreateTest);
		
		JLabel lblHelloTeacher = new JLabel("Hello," + user);
		lblHelloTeacher.setBounds(20, 11, 314, 29);
		frame.getContentPane().add(lblHelloTeacher);
		String col[] = {"Subject"};
		Object[][] objs = p.testsToArr(p.getTests());
		table = new JTable(objs, col);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(32, 61, 411, 102);
		frame.getContentPane().add(scrollPane);
		
		
		//scrollPane.setColumnHeaderView(table);
	}
}
