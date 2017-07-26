package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentAnswer extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentAnswer frame = new StudentAnswer();
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
	public StudentAnswer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblYourAnswer = new JLabel("Your answer:");
		lblYourAnswer.setBounds(27, 46, 77, 14);
		contentPane.add(lblYourAnswer);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setBounds(114, 47, 310, 58);
		//TODO fill in the area with the student answer
		contentPane.add(textArea);
		
		JLabel lblYourGrade = new JLabel("Your grade:");
		lblYourGrade.setBounds(27, 121, 77, 14);
		//TODO add the f grade
		contentPane.add(lblYourGrade);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		JButton button = new JButton(home);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSGui.main(null);
			}
		});
		button.setBounds(0, 159, 46, 46);
		contentPane.add(button);
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		JButton button_1 = new JButton(back);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				StudentTest st = new StudentTest();
				st.setVisible(true);
			}
		});
		button_1.setBounds(58, 159, 46, 46);
		contentPane.add(button_1);
		
		JLabel lblNewLabel = new JLabel("Question:");
		lblNewLabel.setBounds(27, 11, 77, 14);
		contentPane.add(lblNewLabel);
	}
}
