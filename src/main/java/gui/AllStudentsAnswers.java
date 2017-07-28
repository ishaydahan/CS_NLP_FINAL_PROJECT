package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;

public class AllStudentsAnswers extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton button;
	private JButton button_1;
	private JLabel lblPleaseChooseAn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherScreenTest frame = new TeacherScreenTest();
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
	public AllStudentsAnswers() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String col[] = {"Answer","Points"};
		System.out.println(CSMain.q.getStudentAnswers());
		Object[][] objs = CSMain.q.AnswersToArr(CSMain.q.getStudentAnswers()); 
		//Object[][] objs = {{"design mode","m","m"}}; // for design		
		table = new JTable(objs, col);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 37, 346, 170);
		contentPane.add(scrollPane);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		button = new JButton(home);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSMain.main(null);
			}
		});
		button.setBounds(0, 215, 46, 46);
		contentPane.add(button);
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		button_1 = new JButton(back);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				QuestionOption s = new QuestionOption();
				s.setVisible(true);
			}
		});
		button_1.setBounds(58, 215, 46, 46);
		contentPane.add(button_1);
		
		lblPleaseChooseAn = new JLabel("Please choose an answer:");
		lblPleaseChooseAn.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPleaseChooseAn.setBounds(50, 12, 348, 14);
		contentPane.add(lblPleaseChooseAn);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if(i != -1) {
				String ID = (String) objs[i][2]; //ID of answer
				CSMain.a = CSMain.q.getAnswer(ID);
				dispose();
				SAnswerOption sa = new SAnswerOption();
				sa.setVisible(true);
				}
			}
		});

		
		
	}
}
