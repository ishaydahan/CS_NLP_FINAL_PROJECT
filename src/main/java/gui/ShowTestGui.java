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

public class ShowTestGui extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowTestGui frame = new ShowTestGui();
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
	public ShowTestGui() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String col[] = {"Question"};
		Object[][] objs = CSGui.t.questionsToArr(CSGui.t.getQuestions());
		//Object[][] objs = {{"design mode"}}; // for design		
		table = new JTable(objs, col);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 37, 346, 170);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("Add question");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				String test1= JOptionPane.showInputDialog("Please write your Question: ");
				CSGui.t.createQuestion(test1);
				ShowTestGui s = new ShowTestGui();
				s.setVisible(true);
			}
		});
		btnNewButton.setBounds(32, 227, 123, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCheckTest = new JButton("Check test");
		btnCheckTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSGui.t.checkTest();
				JOptionPane.showMessageDialog(null, "Test was checked");
				TeacherFrame t = new TeacherFrame();
				t.setVisible(true);
			}
		});
		btnCheckTest.setBounds(165, 227, 115, 23);
		contentPane.add(btnCheckTest);
		
		JButton btnDeleteTest = new JButton("Delete test");
		btnDeleteTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CSGui.p.removeTest(CSGui.t);
				TeacherFrame t = new TeacherFrame();
				t.setVisible(true);
			}
		});
		btnDeleteTest.setBounds(290, 227, 117, 23);
		contentPane.add(btnDeleteTest);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if(i != -1) {
					String ID = (String) objs[i][1]; //ID of question
					CSGui.q = CSGui.t.getQuestion(ID).load();
					System.out.println(ID);
					ShowQuestionOption sq = new ShowQuestionOption();
					sq.setVisible(true);
					dispose();
				}
			}
		});

		
		
	}
}
