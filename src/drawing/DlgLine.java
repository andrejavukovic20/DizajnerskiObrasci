package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Line;
import geometry.Point;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgLine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean confirm;
	private JTextField txtSPX;
	private JTextField txtSPY;
	private JTextField txtEPX;
	private JTextField txtEPY;
	public Line line;
	private Color color = Color.BLACK;
	private JButton btnOutlineColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine() {
		setModal(true);
		setTitle("Andreja Vukovic IT64/2019");
		setBackground(Color.WHITE);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("START POINT ");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 2;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			JLabel lblSPXCoordinate = new JLabel("X coordinate ");
			GridBagConstraints gbc_lblSPXCoordinate = new GridBagConstraints();
			gbc_lblSPXCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblSPXCoordinate.gridx = 2;
			gbc_lblSPXCoordinate.gridy = 1;
			contentPanel.add(lblSPXCoordinate, gbc_lblSPXCoordinate);
		}
		{
			txtSPX = new JTextField();
			GridBagConstraints gbc_txtSPX = new GridBagConstraints();
			gbc_txtSPX.gridwidth = 3;
			gbc_txtSPX.insets = new Insets(0, 0, 5, 5);
			gbc_txtSPX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSPX.gridx = 3;
			gbc_txtSPX.gridy = 1;
			contentPanel.add(txtSPX, gbc_txtSPX);
			txtSPX.setColumns(10);
		}
		{
			JLabel lblSPYCoordinate = new JLabel("Y coordinate ");
			GridBagConstraints gbc_lblSPYCoordinate = new GridBagConstraints();
			gbc_lblSPYCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblSPYCoordinate.gridx = 2;
			gbc_lblSPYCoordinate.gridy = 2;
			contentPanel.add(lblSPYCoordinate, gbc_lblSPYCoordinate);
		}
		{
			txtSPY = new JTextField();
			txtSPY.setText("");
			GridBagConstraints gbc_txtSPY = new GridBagConstraints();
			gbc_txtSPY.gridwidth = 3;
			gbc_txtSPY.insets = new Insets(0, 0, 5, 0);
			gbc_txtSPY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSPY.gridx = 3;
			gbc_txtSPY.gridy = 2;
			contentPanel.add(txtSPY, gbc_txtSPY);
			txtSPY.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("END POINT");
			GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
			gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_3.gridx = 2;
			gbc_lblNewLabel_3.gridy = 4;
			contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		}
		{
			JLabel lblEPXCoordinate = new JLabel("X coordinate ");
			GridBagConstraints gbc_lblEPXCoordinate = new GridBagConstraints();
			gbc_lblEPXCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblEPXCoordinate.gridx = 2;
			gbc_lblEPXCoordinate.gridy = 5;
			contentPanel.add(lblEPXCoordinate, gbc_lblEPXCoordinate);
		}
		{
			txtEPX = new JTextField();
			GridBagConstraints gbc_txtEPX = new GridBagConstraints();
			gbc_txtEPX.gridwidth = 3;
			gbc_txtEPX.insets = new Insets(0, 0, 5, 0);
			gbc_txtEPX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEPX.gridx = 3;
			gbc_txtEPX.gridy = 5;
			contentPanel.add(txtEPX, gbc_txtEPX);
			txtEPX.setColumns(10);
		}
		{
			JLabel lblEPYCoordinate = new JLabel("Y coordinate ");
			GridBagConstraints gbc_lblEPYCoordinate = new GridBagConstraints();
			gbc_lblEPYCoordinate.insets = new Insets(0, 0, 5, 5);
			gbc_lblEPYCoordinate.gridx = 2;
			gbc_lblEPYCoordinate.gridy = 6;
			contentPanel.add(lblEPYCoordinate, gbc_lblEPYCoordinate);
		}
		{
			txtEPY = new JTextField();
			txtEPY.setText("");
			GridBagConstraints gbc_txtEPY = new GridBagConstraints();
			gbc_txtEPY.gridwidth = 3;
			gbc_txtEPY.insets = new Insets(0, 0, 5, 0);
			gbc_txtEPY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEPY.gridx = 3;
			gbc_txtEPY.gridy = 6;
			contentPanel.add(txtEPY, gbc_txtEPY);
			txtEPY.setColumns(10);
		}

		btnOutlineColor = new JButton("OUTLINE COLOR");
		btnOutlineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color outlineColor = JColorChooser.showDialog(null, "Choose outline color",
						btnOutlineColor.getBackground());
				if (outlineColor != null)
					btnOutlineColor.setBackground(outlineColor);

			}
		});
		btnOutlineColor.setBackground(Color.BLACK);
		GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
		gbc_btnOutlineColor.insets = new Insets(0, 0, 0, 5);
		gbc_btnOutlineColor.gridx = 2;
		gbc_btnOutlineColor.gridy = 8;
		contentPanel.add(btnOutlineColor, gbc_btnOutlineColor);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtSPX.getText().trim().isEmpty() || txtSPY.getText().trim().isEmpty()
								|| txtEPX.getText().trim().isEmpty() || txtEPY.getText().trim().isEmpty()) {
							confirm = false;
							JOptionPane.showMessageDialog(null, "You have to fill all fields!");
						} else {
							try {
								if (Integer.parseInt(txtSPX.getText().toString()) < 0
										|| Integer.parseInt(txtSPY.getText().toString()) < 0
										|| Integer.parseInt(txtEPX.getText().toString()) < 0
										|| Integer.parseInt(txtEPY.getText().toString()) < 0) {
									JOptionPane.showMessageDialog(null, "Enter values greater than 0!");

								} else {
									line = new Line(
											new Point(Integer.parseInt(txtSPX.getText().toString()),
													Integer.parseInt(txtSPY.getText().toString())),
											new Point(Integer.parseInt(txtEPX.getText().toString()),
													Integer.parseInt(txtEPY.getText().toString())),
											false, btnOutlineColor.getBackground());

									confirm = true;
									setVisible(false);

								}

							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, "Enter numbers only!");
							}

						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public JTextField getTxtSPX() {
		return txtSPX;
	}

	public void setTxtSPX(JTextField txtSPX) {
		this.txtSPX = txtSPX;
	}

	public JTextField getTxtSPY() {
		return txtSPY;
	}

	public void setTxtSPY(JTextField txtSPY) {
		this.txtSPY = txtSPY;
	}

	public JTextField getTxtEPX() {
		return txtEPX;
	}

	public void setTxtEPX(JTextField txtEPX) {
		this.txtEPX = txtEPX;
	}

	public JTextField getTxtEPY() {
		return txtEPY;
	}

	public void setTxtEPY(JTextField txtEPY) {
		this.txtEPY = txtEPY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public JButton getBtnOutlineColor() {
		return btnOutlineColor;
	}

	public void setBtnOutlineColor(JButton btnOutlineColor) {
		this.btnOutlineColor = btnOutlineColor;
	}

}
