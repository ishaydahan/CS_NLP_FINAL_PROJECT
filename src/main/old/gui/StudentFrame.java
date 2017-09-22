package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class StudentFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentFrame frame = new StudentFrame();
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
	public StudentFrame() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Hello , " + CSMain.p.getName());
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		label.setBounds(10, 11, 103, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Please choose a test:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setBounds(152, 11, 246, 14);
		contentPane.add(label_1);
		
		String col[] = {"Tests"};
		Object[][] objs = CSMain.p.testsToArr(CSMain.p.getTests());
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
				CSMain.t = CSMain.p.getTest(ID).load(); //init the static test of GUI 
				StudentScreenTest s = new StudentScreenTest();
				s.setVisible(true);
				dispose();
				}
			}
		});
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		JButton button = new JButton(home);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSMain.main(null);
			}
		});
		button.setBounds(0, 215, 46, 46);
		contentPane.add(button);
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		JButton button_1 = new JButton(back);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSMain.main(null);
			}
		});
		button_1.setBounds(58, 215, 46, 46);
		contentPane.add(button_1);
	}
}
