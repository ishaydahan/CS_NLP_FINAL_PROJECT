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
import javax.swing.JLabel;
import java.awt.Font;

public class ShowQuestionOption extends JFrame {

	private JPanel contentPane;

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
		setBounds(100, 100, 450, 242);
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
		btnShowTeacherAnswers.setBounds(25, 48, 183, 23);
		contentPane.add(btnShowTeacherAnswers);
		
		JButton btnShowStudentsAnswers = new JButton("Show students answers");
		btnShowStudentsAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AllStudentsAnswers s = new AllStudentsAnswers();
				s.setVisible(true);
				dispose();
			}
		});
		btnShowStudentsAnswers.setBounds(223, 49, 183, 23);
		contentPane.add(btnShowStudentsAnswers);
		
		JButton btnCreateAnswers = new JButton("Add answer");
		btnCreateAnswers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String answer = JOptionPane.showInputDialog("Please write your Answer: ");
					if(answer != null) {
						String grade= JOptionPane.showInputDialog("Please write the answer Grade: ");
						int point = Integer.parseInt(grade);
						CSGui.q.addTeacherAns(answer, point);
					}
			}
		});
		btnCreateAnswers.setBounds(25, 85, 181, 23);
		contentPane.add(btnCreateAnswers);
		
		JButton btnCheckQuestion = new JButton("Check question");
		btnCheckQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				List<Answer> sAnswers = CSGui.q.getStudentAnswers();
				List<Answer> vAnswers = CSGui.q.getVerifiedAnswers();
				List<Answer> vSntaxAnswers = CSGui.q.getVerifiedSyntaxableAnswers();
				CSGui.q.checkQuestion(sAnswers, vAnswers,vSntaxAnswers );
				JOptionPane.showMessageDialog(null, "Question was checked");
				ShowQuestionOption s = new ShowQuestionOption();
				s.setVisible(true);
			}
		});
		btnCheckQuestion.setBounds(25, 122, 183, 23);
		contentPane.add(btnCheckQuestion);
		
		JButton btnDeleteQuestion = new JButton("Delete question");
		btnDeleteQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSGui.t.removeQuestion(CSGui.q);
				TeacherTest s = new TeacherTest();
				s.setVisible(true);
				dispose();
			}
		});
		btnDeleteQuestion.setBounds(222, 123, 183, 23);
		contentPane.add(btnDeleteQuestion);
		
		JButton button = new JButton("Edit question");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content= JOptionPane.showInputDialog("Please write the new Question: ");
				if(content != null)
					CSGui.q.renameQuestion(content);
			}
		});
		button.setBounds(221, 85, 183, 23);
		contentPane.add(button);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		JButton button_1 = new JButton(home);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSGui.main(null);
			}
		});
		button_1.setBounds(0, 157, 46, 46);
		contentPane.add(button_1);
		ImageIcon back = new ImageIcon("IMG/back.png");	

		JButton button_2 = new JButton(back);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				TeacherTest s = new TeacherTest();
				s.setVisible(true);
			}
		});
		button_2.setBounds(58, 157, 46, 46);
		contentPane.add(button_2);
		
		JLabel lblThatWhatYou = new JLabel("This is your options for:");
		lblThatWhatYou.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblThatWhatYou.setBounds(25, 23, 160, 14);
		contentPane.add(lblThatWhatYou);
		
		JLabel lblNewLabel = new JLabel(CSGui.q.getContent());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel.setBounds(204, 24, 46, 14);
		contentPane.add(lblNewLabel);
	}

}
