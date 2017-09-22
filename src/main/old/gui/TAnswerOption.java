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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;

public class TAnswerOption extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TAnswerOption frame = new TAnswerOption();
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
	public TAnswerOption() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 198);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEditAnswer = new JButton("Edit answer");
		btnEditAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newAnswer = JOptionPane.showInputDialog("Please write your new Answer: ");
				CSMain.a.setContent(newAnswer);
			}
		});
		btnEditAnswer.setBounds(59, 67, 136, 23);
		contentPane.add(btnEditAnswer);
		
		JButton btnDeleteAnswer = new JButton("Delete answer");
		btnDeleteAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSMain.q.removeAnswer(CSMain.a);
				TeacherAnswers s = new TeacherAnswers();
				s.setVisible(true);
			}
		});
		btnDeleteAnswer.setBounds(218, 67, 136, 23);
		contentPane.add(btnDeleteAnswer);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		JButton button = new JButton(home);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSMain.main(null);
			}
		});
		button.setBounds(0, 113, 46, 46);
		contentPane.add(button);
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		JButton button_1 = new JButton(back);
		button_1.setBounds(58, 113, 46, 46);
		contentPane.add(button_1);
		
		JLabel label = new JLabel("This is your options:");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(59, 25, 348, 14);
		contentPane.add(label);
	}
}
