package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.JTextField;
import java.awt.Font;

public class TeacherFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherFrame frame = new TeacherFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TeacherFrame() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String col[] = {"Tests"};
		Object[][] objs = CSGui.p.testsToArr(CSGui.p.getTests());
		//Object[][] objs = {{"design mode"}}; //only for design
		
		JTable table = new JTable(objs,col);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(58, 27, 340, 188);
		getContentPane().add(scrollPane);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if(i != -1) {
				String ID = (String) objs[i][1]; //ID of test
				CSGui.t = CSGui.p.getTest(ID).load(); //init the static test of GUI 
				TeacherTest s = new TeacherTest();
				s.setVisible(true);
				dispose();
				}
			}
		});
		
		JButton btnCreateTest = new JButton("Create new test");
		btnCreateTest.setBounds(152, 226, 139, 23);
		contentPane.add(btnCreateTest);
		JLabel lblHello = new JLabel("Hello , " + CSGui.p.getName());
		lblHello.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblHello.setBounds(10, 11, 103, 14);
		contentPane.add(lblHello);
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		JButton btnNewButton = new JButton(back);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSGui.main(null);
			}
		});
		btnNewButton.setBounds(58, 215, 46, 46);
		contentPane.add(btnNewButton);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		JButton button = new JButton(home);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSGui.main(null);
			}
		});
		button.setBounds(0, 215, 46, 46);
		contentPane.add(button);
		
		JLabel lblPleaseChooseA = new JLabel("Please choose a test:");
		lblPleaseChooseA.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPleaseChooseA.setBounds(152, 11, 246, 14);
		contentPane.add(lblPleaseChooseA);
				

		
		btnCreateTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateTestGui ct = new CreateTestGui();
				ct.createTest();
				dispose();
			}
		});
	}
}
