package main.model;

public class User {
	private String FullName;
	
	private String Email;
	
	public User(String FullName, String Email){
		this.FullName = FullName;
		this.Email = Email;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
	
}
