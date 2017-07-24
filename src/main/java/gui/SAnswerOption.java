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
		setBounds(100, 100, 450, 300);
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
		btnApproveAnswer.setBounds(61, 138, 134, 23);
		contentPane.add(btnApproveAnswer);
		
		JButton btnFixPoints = new JButton("Fix");
		btnFixPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					 point = Integer.parseInt(textField.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please enter a valid number of points");
				}
				CSGui.q.fixAns(point, CSGui.a);
			}
		});
		btnFixPoints.setBounds(243, 86, 134, 23);
		contentPane.add(btnFixPoints);
		
		textField = new JTextField();
		textField.setBounds(147, 87, 86, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblChangePoints = new JLabel("Change points:");
		lblChangePoints.setBounds(61, 90, 118, 14);
		contentPane.add(lblChangePoints);
	}
}
