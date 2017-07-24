//package gui;
//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JButton;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//import java.awt.event.ActionEvent;
//import javax.swing.JLabel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//
//import objects.Person;
//
//public class TeacherGui {
//
//	private JFrame frame;
//	private static String user;
//	private JTable table;
//	
//	/**
//	 * Launch the application.
//	 * @param userName 
//	 * @param person 
//	 */
//	public static void TeacherScreen(String userName) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					user = userName;
//					TeacherGui window = new TeacherGui();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the application.
//	 * @param p 
//	 */
//	public TeacherGui() {
//		initialize();
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize() {
//		frame = new JFrame();
//		frame.setBounds(100, 100, 535, 269);
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
//		
//		JButton btnCreateTest = new JButton("Create Test");
//
//		btnCreateTest.setBounds(176, 189, 131, 23);
//		frame.getContentPane().add(btnCreateTest);
//		
//		JLabel lblHelloTeacher = new JLabel("Hello," + user);
//		lblHelloTeacher.setBounds(20, 11, 314, 29);
//		frame.getContentPane().add(lblHelloTeacher);
//		
//		
//		table = new JTable(objs, col);
//		table.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int i = table.getSelectedRow();
//				if(i != -1) {
//				String ID = (String) objs[i][1]; //ID of test
//				CSGui.t = CSGui.p.getTest(ID).load(); //init the static test of GUI 
//				ShowTestGui s = new ShowTestGui();
//				s.setVisible(true);
//				}
//			}
//		});
//		table.addPropertyChangeListener(new PropertyChangeListener() {
//			public void propertyChange(PropertyChangeEvent evt) {
////				int i = table.getSelectedRow();
////				if(i != -1) {
////					System.out.println(objs[i][0]); //test
////					String ID = (String) objs[i][1]; //ID
////					System.out.println(ID);
////					ShowTestGui s = new ShowTestGui();
////					s.setVisible(true);
////				}
//			}
//		});
//
//		
//		JScrollPane scrollPane = new JScrollPane(table);
//		scrollPane.setBounds(32, 61, 411, 102);
//		frame.getContentPane().add(scrollPane);
//	}
//}
