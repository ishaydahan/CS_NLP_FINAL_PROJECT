package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class StudentTest extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentTest frame = new StudentTest();
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
	public StudentTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel(CSGui.t.getContent() + "-");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		label.setBounds(48, 1, 46, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Please choose a question:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setBounds(120, 0, 354, 14);
		contentPane.add(label_1);
		
		String col[] = {"Question"};
		//Object[][] objs = CSGui.t.questionsToArr(CSGui.t.getQuestions());
		Object[][] objs = {{"design mode"}}; // for design		
		JTable table = new JTable(objs, col);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(48, 35, 346, 141);
		contentPane.add(scrollPane);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if(i != -1) {
					String ID = (String) objs[i][1]; //ID of question
					CSGui.q = CSGui.t.getQuestion(ID).load();
					StudentAnswer sa = new StudentAnswer();
					sa.setVisible(true);
					dispose();
				}
			}
		});
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		JButton button_back = new JButton(back);
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				StudentFrame s = new StudentFrame();
				s.setVisible(true);
			}
		});
		button_back.setBounds(58, 216, 46, 46);
		contentPane.add(button_back);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		JButton button_home = new JButton(home);
		button_home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSGui.main(null);
			}
		});
		button_home.setBounds(0, 216, 46, 46);
		contentPane.add(button_home);
		
		JLabel lblYourGradeIs = new JLabel("Your grade is:");
		lblYourGradeIs.setBounds(147, 187, 140, 14);
		contentPane.add(lblYourGradeIs);
		
		JButton btnNewButton = new JButton("Submit test");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//submit the test to the DB
			}
		});
		btnNewButton.setBounds(48, 180, 89, 23);
		contentPane.add(btnNewButton);
	}

}
