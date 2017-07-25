package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class SAnswerOption extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	int point;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SAnswerOption frame = new SAnswerOption();
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
	public SAnswerOption() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 202);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnApproveAnswer = new JButton("Approve answer");
		btnApproveAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSGui.q.approveAnswer(CSGui.a);
			}
		});
		btnApproveAnswer.setBounds(103, 82, 134, 23);
		contentPane.add(btnApproveAnswer);
		
		JButton btnFixPoints = new JButton("Fix");
		btnFixPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(true) {
				try {
					 point = Integer.parseInt(textField.getText());
					 break;
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please enter a valid number of points");
				}
				CSGui.q.fixAns(point, CSGui.a);
				}
			}
		});
		btnFixPoints.setBounds(218, 48, 134, 23);
		contentPane.add(btnFixPoints);
		
		textField = new JTextField();
		textField.setBounds(122, 49, 86, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblChangePoints = new JLabel("Change points:");
		lblChangePoints.setBounds(36, 52, 118, 14);
		contentPane.add(lblChangePoints);
		ImageIcon home = new ImageIcon("IMG/home.png");	

		JButton button = new JButton(home);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(0, 117, 46, 46);
		contentPane.add(button);
		ImageIcon back = new ImageIcon("IMG/back.png");	

		JButton button_1 = new JButton(back);
		button_1.setBounds(58, 117, 46, 46);
		contentPane.add(button_1);
	}
}
