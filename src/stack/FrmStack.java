package stack;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Point;
import geometry.Rectangle;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmStack extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	JList lstStack = new JList();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmStack frame = new FrmStack();
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
	public FrmStack() {
		setTitle("Andreja Vukovic IT64/2019");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlNorth = new JPanel();
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		
		JLabel lblStack = new JLabel("Stack");
		pnlNorth.add(lblStack);
		
		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		
		JScrollPane scrlPaneStack = new JScrollPane();
		pnlCenter.add(scrlPaneStack);
		
		JList lstStack = new JList();
		scrlPaneStack.setViewportView(lstStack);
		lstStack.setModel(dlm);
		
		JPanel pnlSouth = new JPanel();
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgStack dlgStack = new DlgStack();
				dlgStack.setVisible(true);
				
				if(dlgStack.isOk){
					String element = ("Upper left point=( " + dlgStack.getTxtX().getText() + " " + "," + " " + dlgStack.getTxtY().getText() + " " +"), height= " + dlgStack.getTxtWidth().getText() + " width= "+ dlgStack.getTxtHeight().getText());
					dlm.add(0, element);
					
				}
				
			}
			
		});
		pnlSouth.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dlm.isEmpty()) {
					JOptionPane.showMessageDialog(null, "List is empty!");
				}
				else if(lstStack.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please, select a row!");
				}
				else {
					DlgStack dlgDelete =new DlgStack();
					String[] split= dlm.getElementAt(lstStack.getSelectedIndex()).toString().split(" ");
					int index = lstStack.getSelectedIndex();
					dlgDelete.getTxtX().setText(split[3]);
					dlgDelete.getTxtY().setText(split[5]);
					dlgDelete.getTxtHeight().setText(split[8]);
					dlgDelete.getTxtWidth().setText(split[10]);
					dlgDelete.setVisible(true);
					
					if(dlgDelete.isOk) {
						dlm.remove(index);
					}			
				}
					
			}	
			
		});
		pnlSouth.add(btnDelete);
		
		
		
	}

}
