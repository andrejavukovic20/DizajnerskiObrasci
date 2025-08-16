 package mvc;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import observer.Observer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingFrame extends JFrame implements Observer{
	
	private DrawingController controller;
	private JPanel contentPane;
	private DrawingView pnlDrawing = new DrawingView(); 
	
	private JToggleButton tglbtnPoint = new JToggleButton("Point");
	private JToggleButton tglbtnLine = new JToggleButton("Line");
	private JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
	private JToggleButton tglbtnCircle = new JToggleButton("Circle");
	private JToggleButton tglbtnDonut = new JToggleButton("Donut");
	private JToggleButton tglbtnSelect = new JToggleButton("Select");
	
	private final JPanel panelAction = new JPanel();
	public DefaultListModel<String> listModel = new DefaultListModel<String>();
	private final JList list = new JList(listModel);
	private final JButton btnInnerColor = new JButton("InnerColor");
	private final JButton btnOutlineColor = new JButton("OutlineColor");
	private final JButton btnBringToFront = new JButton("BringToFront");
	private final JButton btnBringToBack = new JButton("BringToBack");
	private final JButton btnToFront = new JButton("ToFront");
	private final JButton btnToBack = new JButton("ToBack");
	private final JButton btnModify = new JButton("Modify");
	private final JButton btnDelete = new JButton("Delete");
	private final JButton btnUndo = new JButton("Undo");
	private final JButton btnRedo = new JButton("Redo");
	private final JButton btnSaveTxt = new JButton("SaveTxt");
	private final JButton btnLoad = new JButton("Load");
	private final JButton btnNext = new JButton("Next");
	private final JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JButton btnSaveObject = new JButton("SaveObject");
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawingFrame frame = new DrawingFrame();
					frame.setVisible(true);
				}catch (Exception e){
					e.printStackTrace();
					
				}
			}
			
		});
	}
	public DrawingFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//koordinate gornjeg lijevog ugla, sirina, visina
		setBounds(99, 95, 1212, 599);
		setResizable(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pnlDrawing.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tglbtnSelect.isSelected()) {
					if(e.getModifiers() == 18)
						controller.selectShape(e.getX(), e.getY(), true);
					else
						controller.selectShape(e.getX(), e.getY(), false);
				} else {
					if(tglbtnPoint.isSelected()) {
						controller.drawShape("Point", e.getX(), e.getY());
					} else if(tglbtnLine.isSelected()) {
						controller.drawShape("Line", e.getX(), e.getY());
					} else if(tglbtnCircle.isSelected()) {
						controller.drawShape("Circle", e.getX(), e.getY());
					} else if(tglbtnDonut.isSelected()) {
						controller.drawShape("Donut", e.getX(), e.getY());
					}  else if(tglbtnRectangle.isSelected()) {
						controller.drawShape("Rectangle", e.getX(), e.getY());
					} else if(tglbtnHexagon.isSelected()) {
						controller.drawShape("Hexagon", e.getX(), e.getY());
					}
				
				}
				
			}
		});
		pnlDrawing.setBackground(Color.WHITE);
		contentPane.add(pnlDrawing, BorderLayout.CENTER);

		JPanel pnlNorth = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlNorth.getLayout();
		pnlNorth.setBackground(new Color(135, 206, 250));
		contentPane.add(pnlNorth, BorderLayout.NORTH);

		pnlNorth.add(tglbtnPoint);
		pnlNorth.add(tglbtnLine);
		pnlNorth.add(tglbtnRectangle);
		pnlNorth.add(tglbtnCircle);
		pnlNorth.add(tglbtnDonut);
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(tglbtnPoint);
		btnGroup.add(tglbtnLine);
		btnGroup.add(tglbtnCircle);
		btnGroup.add(tglbtnRectangle);
		btnGroup.add(tglbtnDonut);
		btnGroup.add(tglbtnHexagon);
		pnlNorth.add(tglbtnHexagon);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color innerColor = JColorChooser.showDialog(null, "Choose inner color", btnInnerColor.getBackground());
				if (innerColor != null)
					btnInnerColor.setBackground(innerColor);
			}
		});
		btnInnerColor.setBackground(Color.WHITE);
		
		pnlNorth.add(btnInnerColor);
		btnOutlineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color outlineColor = JColorChooser.showDialog(null, "Choose outline color", btnOutlineColor.getBackground());
				if (outlineColor != null)
					btnOutlineColor.setBackground(outlineColor);
			}
		});
		btnOutlineColor.setBackground(Color.BLACK);
		
		pnlNorth.add(btnOutlineColor);
		panelAction.setBorder(new LineBorder(Color.BLUE, 2));
		panelAction.setBackground(new Color(135, 206, 250));
		
		pnlNorth.add(panelAction);
		pnlNorth.setPreferredSize(new Dimension(100, 80));
		panelAction.add(tglbtnSelect);
		
			btnGroup.add(tglbtnSelect);
		
				//JButton btnModify = new JButton("Modify");
				btnModify.setEnabled(false);
				panelAction.add(btnModify);
				btnModify.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controller.modifyShape();
						tglbtnSelect.setSelected(false);
					}
				});
				btnGroup.add(btnModify);
		
					//JButton btnDelete = new JButton("Delete");
					btnDelete.setEnabled(false);
					panelAction.add(btnDelete);
					btnDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							controller.deleteShape();
							tglbtnSelect.setSelected(false);
						}
					});
					btnGroup.add(btnDelete);
					btnBringToFront.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							controller.bringToFront();
						}
					});
				
					panelAction.add(btnBringToFront);
					btnBringToBack.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							controller.bringToBack();
						}
					});
				
					panelAction.add(btnBringToBack);
					btnToFront.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							controller.toFront();
						}
					});
				
					panelAction.add(btnToFront);
					btnToBack.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							controller.toBack();
						}
					});
				
					panelAction.add(btnToBack);
					btnUndo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							controller.undo();
						}
					});
				
					panelAction.add(btnUndo);
					btnRedo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							controller.redo();
						}
					});
				
					panelAction.add(btnRedo);
					btnSaveTxt.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							controller.saveTxt();
						}
					});
				
					panelAction.add(btnSaveTxt);
					btnSaveObject.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							controller.saveObject();
						}
					});
				
					panelAction.add(btnSaveObject);
					btnLoad.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							controller.load();
						}
					});
				
					panelAction.add(btnLoad);
					btnNext.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							controller.next();
						}
					});
				
					panelAction.add(btnNext);
				
		JPanel pnlSouth = new JPanel();
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setBackground(new Color(135, 206, 250));
		pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		pnlSouth.add(scrollPane);
		scrollPane.setViewportView(list);
		scrollPane.setPreferredSize(new Dimension(690, 100));

		
	
	}



	public DrawingController getController() {
		return controller;
	}
	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public DrawingView getPnlDrawing() {
		return pnlDrawing;
	}
	
	public void setPnlDrawing(DrawingView pnlDrawing) {
		this.pnlDrawing = pnlDrawing;
	}
	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JButton getBtnOutlineColor() {
		return btnOutlineColor;
	}

	public DefaultListModel<String> getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel<String> listModel) {
		this.listModel = listModel;
	}

	@Override
	public void update(boolean deleteBtn, boolean modifyBtn) {
		btnDelete.setEnabled(deleteBtn);
		btnModify.setEnabled(modifyBtn);
	}
	

}
