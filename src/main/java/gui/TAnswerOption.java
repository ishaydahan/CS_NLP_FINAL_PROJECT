package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEditAnswer = new JButton("Edit answer");
		btnEditAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnEditAnswer.setBounds(44, 36, 136, 23);
		contentPane.add(btnEditAnswer);
		
		JButton btnDeleteAnswer = new JButton("Delete answer");
		btnDeleteAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSGui.q.removeAnswer(CSGui.a);
			}
		});
		btnDeleteAnswer.setBounds(44, 87, 136, 23);
		contentPane.add(btnDeleteAnswer);
	}
}
