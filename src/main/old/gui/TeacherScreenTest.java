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
import javax.swing.JProgressBar;

public class TeacherScreenTest extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;

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
	public TeacherScreenTest() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String col[] = {"Question"};
		Object[][] objs = CSMain.t.questionsToArr(CSMain.t.getQuestions());
		//Object[][] objs = {{"design mode"}}; // for design		
		JTable table = new JTable(objs, col);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(48, 38, 346, 154);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("Add question");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				String question= JOptionPane.showInputDialog("Please write your Question: ");
				CSMain.t.createQuestion(question);
				TeacherScreenTest s = new TeacherScreenTest();
				s.setVisible(true);
			}
		});
		btnNewButton.setBounds(30, 203, 123, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCheckTest = new JButton("Check test");
		btnCheckTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CSMain.t.load().checkTest();
				JOptionPane.showMessageDialog(null, "Test was checked");
			}
		});
		btnCheckTest.setBounds(163, 203, 115, 23);
		contentPane.add(btnCheckTest);
		
		JButton btnDeleteTest = new JButton("Delete test");
		btnDeleteTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSMain.p.removeTest(CSMain.t);
				TeacherFrame t = new TeacherFrame();
				t.setVisible(true);
			}
		});
		btnDeleteTest.setBounds(288, 203, 117, 23);
		contentPane.add(btnDeleteTest);
		
		ImageIcon home = new ImageIcon("IMG/home.png");	
		JButton button = new JButton(home);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSMain.main(null);
			}
		});
		button.setBounds(0, 229, 46, 46);
		contentPane.add(button);
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		JButton button_1 = new JButton(back);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				TeacherFrame t = new TeacherFrame();
				t.setVisible(true);
			}
		});
		button_1.setBounds(58, 229, 46, 46);
		contentPane.add(button_1);
		
		JLabel lblPleaseChooseA = new JLabel("Please choose a question:");
		lblPleaseChooseA.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPleaseChooseA.setBounds(120, 13, 354, 14);
		contentPane.add(lblPleaseChooseA);
		
		JLabel lblNewLabel = new JLabel(CSMain.t.getContent() + "-");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel.setBounds(48, 14, 46, 14);
		contentPane.add(lblNewLabel);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(147, 237, 146, 14);
		contentPane.add(progressBar);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if(i != -1) {
					String ID = (String) objs[i][1]; //ID of question
					CSMain.q = CSMain.t.getQuestion(ID).load();
					QuestionOption sq = new QuestionOption();
					sq.setVisible(true);
					dispose();
				}
			}
		});

		
		
	}
}
