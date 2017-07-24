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

public class CreateTestGui {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_3;
	private JTextField textField_4;
	private int i;
	private Person person;
	
	/**
	 * Launch the application.
	 */
	public static void createTest() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateTestGui window = new CreateTestGui();
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
	public CreateTestGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 150);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnGenerateTest = new JButton("Generate Test");
		btnGenerateTest.setBounds(119, 77, 159, 23);
		frame.getContentPane().add(btnGenerateTest);
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(10, 34, 66, 14);
		frame.getContentPane().add(lblSubject);
		
		textField = new JTextField();
		textField.setBounds(86, 31, 288, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		btnGenerateTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//adding the details to the test
				person.createTest(textField.getText());
			}
		});
		


	}
}
