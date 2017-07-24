package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class QuestionGui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuestionGui frame = new QuestionGui();
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
	public QuestionGui() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 445, 293);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textField.setBorder(new LineBorder(new Color(171, 173, 179)));
		textField.setBounds(10, 22, 414, 42);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblQuestion = new JLabel("Question:");
		lblQuestion.setBounds(10, 4, 98, 14);
		contentPane.add(lblQuestion);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setBounds(10, 91, 414, 91);
		contentPane.add(textArea);
		textArea.setLineWrap(true);
		
		JLabel lblAnswer = new JLabel("Answer:");
		lblAnswer.setBounds(10, 75, 98, 14);
		contentPane.add(lblAnswer);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//saving the parms
					int p = Integer.parseInt(textField_1.getText());
					String q = textField.getText();
					String a =  textArea.getText();
					//call the func to save
					System.out.println("q:"+ q + " a:" + a + " points:" + p);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please Enter Valid number for Point");
				}
			}
		});
		btnSave.setBounds(19, 221, 89, 23);
		contentPane.add(btnSave);
		
		JLabel lblPoints = new JLabel("Points:");
		lblPoints.setBounds(10, 196, 98, 14);
		contentPane.add(lblPoints);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBorder(new LineBorder(new Color(171, 173, 179)));
		textField_1.setBounds(46, 193, 42, 20);
		contentPane.add(textField_1);
		
		JButton button = new JButton("Save and Close");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//saving the parms
					int p = Integer.parseInt(textField_1.getText());
					String q = textField.getText();
					String a =  textArea.getText();
					//call the func to save
					System.out.println("q:"+ q + " a:" + a + " points:" + p);
					dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please Enter Valid number for Point");
				}
			}
		});
		button.setBounds(118, 221, 137, 23);
		contentPane.add(button);
	}
}
