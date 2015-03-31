package main.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.controller.LoginController;
import main.model.Session;
import main.model.User;

public class LoginView extends JFrame {

	private JLabel loginLabel = new JLabel("Login: ");
	
	private JComboBox<User> loginCombo;
	
	private JPanel labelsPanel;
	private JPanel fieldsPanel;
	
	private JPanel controls;
	
	private JPanel inputs;
	
	private JPanel gui;
	
	private JButton login;
	
	private JButton cancel;
	
	private Session session;
	
	private ArrayList<User> users;
	
	boolean validLogin = false;
	
	public LoginView(ArrayList<User> users) {
		this.users = users;
		this.inputs = new JPanel(new BorderLayout(5, 5));
		
		this.labelsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		this.fieldsPanel = new JPanel(new GridLayout(0, 1, 3, 3));
		
		this.inputs.add(this.labelsPanel);
		this.inputs.add(this.fieldsPanel);
		
		this.labelsPanel.add(this.loginLabel);
		
		this.loginCombo = new JComboBox<User>(this.users.toArray(
				new User[this.users.size()]));
		this.fieldsPanel.add(this.loginCombo);
		
		this.controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
		
		this.login = new JButton("Login");
		this.login.setActionCommand("login");
		
		this.cancel = new JButton("Cancel");
		this.cancel.setActionCommand("cancel");
		
		this.controls.add(this.login);
		this.controls.add(this.cancel);
		
		this.gui = new JPanel(new BorderLayout(10, 10));
		this.gui.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.gui.add(this.inputs, BorderLayout.CENTER);
		this.gui.add(this.controls, BorderLayout.SOUTH);
		
		add(this.gui);
		
		pack();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Part Detail");
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void closeView(){
		this.dispose();
	}
	
	public boolean getValidLogin(){
		return this.validLogin;
	}
	
	public void setValidLogin(boolean isValid){
		this.validLogin = isValid;
	}
	
	public Session getSession(){
		return this.session;
	}
	
	public void setSession(Session session){
		this.session = session;
	}
	
	public User getUser(){
		return (User)this.loginCombo.getSelectedItem();
	}

	public void registerListener(LoginController loginController){
		// add listener for buttons
		Component[] components = this.controls.getComponents();
		for (Component component : components) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.addActionListener(loginController);
			}
		}
	}
}
