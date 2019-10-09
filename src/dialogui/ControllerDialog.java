package dialogui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import tools.Controller;

@SuppressWarnings("serial")
public class ControllerDialog extends JDialog implements ActionListener, KeyListener {

	private JLabel psw_label = new JLabel("Password: ");
	private JPasswordField psw_field;
	private JButton validate_button = new JButton("Validate");
	private JLabel wrongPsw_label = new JLabel(" Retry");
	
	private JPanel content_pane = new JPanel();
	
	private String password;
	
	private Controller valid;
	
	public ControllerDialog (final Controller valid, String password, String name)
	{
		this.setTitle("Enter "+name+"'s password");
		this.setModal(true);
		this.setBackground(Color.WHITE);
		
		this.valid = valid;
		this.valid.False();
		this.password = password;
		
		this.content_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.content_pane.setBackground(Color.WHITE);
		
		this.psw_field = new JPasswordField(15);
		this.psw_field.addKeyListener(this);
		
		this.wrongPsw_label.setForeground(Color.WHITE);
		
		this.validate_button.addActionListener(this);
		
		GridBagLayout gridBag_l = new GridBagLayout();
		this.content_pane.setLayout(gridBag_l);
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;
		
		csr.gridx = 0;
		csr.gridy = 0;
		gridBag_l.setConstraints(psw_label, csr);
		this.content_pane.add(psw_label);
		
		csr.gridx = 1;
		gridBag_l.setConstraints(psw_field, csr);
		this.content_pane.add(psw_field);
		
		csr.weightx = 1;
		csr.gridx = 2;
		gridBag_l.setConstraints(wrongPsw_label, csr);
		this.content_pane.add(wrongPsw_label);
		
		csr.gridx = 1;
		csr.gridy = 1;
		csr.anchor = GridBagConstraints.CENTER;
		csr.insets = new Insets(10,45,0,45);
		gridBag_l.setConstraints(validate_button, csr);
		this.content_pane.add(validate_button);
		
		this.add(content_pane);
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == validate_button)
		{
			this.Validation();
		}
	}
	
	/**
	 * Verifica la password inserita
	 */
	private void Validation()
	{
		String text = new String(psw_field.getPassword());
		if(text.equals(password))
		{
			valid.True();
			this.dispose();
		}
		else
		{
			psw_field.setText("");
			psw_field.requestFocus();
			psw_field.setBorder(BorderFactory.createLineBorder(Color.RED));
			wrongPsw_label.setForeground(Color.RED);
		}
	}
	
	public void keyPressed(KeyEvent k) {
		int key;
		key = k.getKeyCode();
		if(key == KeyEvent.VK_ENTER)
		{
			this.Validation();
		}
	}

	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
		
	}
	
}
