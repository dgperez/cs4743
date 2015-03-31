package main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.dao.ConnectionGateway;
import main.model.Authenticator;
import main.model.User;
import main.view.LoginView;

public class LoginController implements ActionListener {

	private ConnectionGateway connGateway;
	
	private LoginView loginView;
	
	public LoginController(LoginView loginView, 
			ConnectionGateway connGateway) {
		this.connGateway = connGateway;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("login")){
			User user = this.loginView.getUser();
			try{
				Authenticator auth = new Authenticator(user, this.connGateway);
				this.loginView.setSession(auth.Authenticate());
				this.loginView.setValidLogin(true);
			} catch(Exception e1){
				e1.printStackTrace();
				this.loginView.setValidLogin(false);
				JOptionPane.showMessageDialog(null, "Error: " + 
						e1.getMessage());
			}
		} else if(e.getActionCommand().equals("cancel")) {
			System.exit(0);
		}
	}
}
