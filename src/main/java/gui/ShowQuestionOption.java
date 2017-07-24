package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
			}
		});
		btnShowTeacherAnswers.setBounds(25, 25, 183, 23);
		contentPane.add(btnShowTeacherAnswers);
		
		JButton btnShowStudentsAnswers = new JButton("Show students answers");
		btnShowStudentsAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowSanswers s = new ShowSanswers();
				s.setVisible(true);
			}
		});
		btnShowStudentsAnswers.setBounds(25, 63, 183, 23);
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

			}
		});
		btnCreateAnswers.setBounds(239, 25, 154, 23);
		contentPane.add(btnCreateAnswers);
		
		JButton btnCheckQuestion = new JButton("Check question");
		btnCheckQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSGui.q.checkQuestion(CSGui.q.getStudentAnswers(), CSGui.q.getVerifiedAnswers(), CSGui.q.getVerifiedSyntaxableAnswers());
			}
		});
		btnCheckQuestion.setBounds(25, 99, 183, 23);
		contentPane.add(btnCheckQuestion);
		
		JButton btnDeleteQuestion = new JButton("Delete question");
		btnDeleteQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			CSGui.t.removeQuestion(CSGui.q);
			}
		});
		btnDeleteQuestion.setBounds(25, 133, 183, 23);
		contentPane.add(btnDeleteQuestion);
	}

}
