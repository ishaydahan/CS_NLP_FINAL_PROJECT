package gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import objects.Person;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;
import javax.swing.JTextPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Panel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Font;

public class CreateTest {

	private JFrame frame;
	private JTextField textField;

	
	/**
	 * Launch the application.
	 */
	public static void createTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateTest window = new CreateTest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param p 
	 */
	public CreateTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Exams Checker");
		frame.setBounds(100, 100, 400, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnGenerateTest = new JButton("Generate Test");
		btnGenerateTest.setBounds(119, 77, 159, 23);
		frame.getContentPane().add(btnGenerateTest);
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(10, 39, 66, 14);
		frame.getContentPane().add(lblSubject);
		
		textField = new JTextField();
		textField.setBounds(86, 36, 288, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	

		JButton button = new JButton(home);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				CSMain.main(null);
			}
		});
		button.setBounds(0, 65, 46, 46);
		frame.getContentPane().add(button);
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		JButton button_1 = new JButton(back);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				TeacherFrame t = new TeacherFrame();
				t.setVisible(true);
			}
		});
		button_1.setBounds(58, 65, 46, 46);
		frame.getContentPane().add(button_1);
		
		JLabel lblPleaseNameYour = new JLabel("Please name your test:");
		lblPleaseNameYour.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPleaseNameYour.setBounds(86, 11, 253, 14);
		frame.getContentPane().add(lblPleaseNameYour);
		btnGenerateTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//adding the details to the test
				CSMain.p.createTest(textField.getText());
				TeacherFrame t = new TeacherFrame();
				t.setVisible(true);
				frame.dispose();
			}
		});
		


	}
}
