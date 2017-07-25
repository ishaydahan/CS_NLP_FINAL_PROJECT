package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import objects.Answer;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ShowQuestionOption extends JFrame {

	private JPanel contentPane;
	private int point;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowQuestionOption frame = new ShowQuestionOption();
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
	public ShowQuestionOption() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 218);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnShowTeacherAnswers = new JButton("Show teacher answers");
		btnShowTeacherAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowTanswers t = new ShowTanswers();
				t.setVisible(true);
				dispose();
			}
		});
		btnShowTeacherAnswers.setBounds(25, 25, 183, 23);
		contentPane.add(btnShowTeacherAnswers);
		
		JButton btnShowStudentsAnswers = new JButton("Show students answers");
		btnShowStudentsAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowSanswers s = new ShowSanswers();
				s.setVisible(true);
				dispose();
			}
		});
		btnShowStudentsAnswers.setBounds(223, 26, 183, 23);
		contentPane.add(btnShowStudentsAnswers);
		
		JButton btnCreateAnswers = new JButton("Add answer");
		btnCreateAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String answer= JOptionPane.showInputDialog("Please write your Answer: ");
				while(true) {
					try {
					String grade= JOptionPane.showInputDialog("Please write the answer Grade: ");
					point = Integer.parseInt(grade);
					break;
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Please enter a valid number of points");
					}
				}
				CSGui.q.addTeacherAns(answer, point);
				dispose();
				ShowTestGui s = new ShowTestGui();
				s.setVisible(true);
			}
		});
		btnCreateAnswers.setBounds(25, 62, 181, 23);
		contentPane.add(btnCreateAnswers);
		
		JButton btnCheckQuestion = new JButton("Check question");
		btnCheckQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				List<Answer> sAnswers = CSGui.q.getStudentAnswers();
				List<Answer> vAnswers = CSGui.q.getVerifiedAnswers();
				List<Answer> vSntaxAnswers = CSGui.q.getVerifiedSyntaxableAnswers();
				CSGui.q.checkQuestion(null, null,null );
				System.out.println("check2");
				JOptionPane.showMessageDialog(null, "Question was checked");
				ShowQuestionOption s = new ShowQuestionOption();
				s.setVisible(true);
			}
		});
		btnCheckQuestion.setBounds(25, 99, 183, 23);
		contentPane.add(btnCheckQuestion);
		
		JButton btnDeleteQuestion = new JButton("Delete question");
		btnDeleteQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSGui.t.removeQuestion(CSGui.q);
				ShowTestGui s = new ShowTestGui();
				s.setVisible(true);
				dispose();
			}
		});
		btnDeleteQuestion.setBounds(222, 100, 183, 23);
		contentPane.add(btnDeleteQuestion);
		
		JButton button = new JButton("Edit question");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content= JOptionPane.showInputDialog("Please write the new Question: ");
				CSGui.q.renameQuestion(content);
			}
		});
		button.setBounds(221, 62, 183, 23);
		contentPane.add(button);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		JButton button_1 = new JButton(home);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSGui.main(null);
			}
		});
		button_1.setBounds(0, 133, 46, 46);
		contentPane.add(button_1);
		ImageIcon back = new ImageIcon("IMG/back.png");	

		JButton button_2 = new JButton(back);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowTestGui s = new ShowTestGui();
				s.setVisible(true);
			}
		});
		button_2.setBounds(58, 133, 46, 46);
		contentPane.add(button_2);
	}

}
