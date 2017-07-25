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

public class ShowSanswers extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton button;
	private JButton button_1;

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
	public ShowSanswers() {
		super("Exams Checker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String col[] = {"Answer","Owner","Points"};
		Object[][] objs = CSGui.q.AnswersToArr(CSGui.q.getStudentAnswers()); 
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
				CSGui.main(null);
			}
		});
		button.setBounds(0, 218, 46, 46);
		contentPane.add(button);
		
		ImageIcon back = new ImageIcon("IMG/back.png");	
		button_1 = new JButton(back);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowQuestionOption s = new ShowQuestionOption();
				s.setVisible(true);
			}
		});
		button_1.setBounds(58, 218, 46, 46);
		contentPane.add(button_1);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if(i != -1) {
				String ID = (String) objs[i][1]; //ID of question
				CSGui.q = CSGui.t.getQuestion(ID).load();
				ShowQuestionOption sq = new ShowQuestionOption();
				sq.setVisible(true);
				}
			}
		});

		
		
	}
}
