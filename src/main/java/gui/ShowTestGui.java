package gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JButton;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String col[] = {"Question"};
		System.out.println(CSGui.t.getQuestions());
		Object[][] objs = CSGui.t.questionsToArr(CSGui.t.getQuestions());
				
		table = new JTable(objs, col);
		table.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				int i = table.getSelectedRow();
				if(i != -1)
					System.out.println(table.getValueAt(i, 0));
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 37, 346, 170);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(49, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("New button");
		button.setBounds(182, 227, 89, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(307, 227, 89, 23);
		contentPane.add(button_1);
		

		
		
	}
}
