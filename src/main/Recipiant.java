package main;

public class Recipiant {

	private String firstname;
	private String lastname;
	private String email;
	private Integer index;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		System.out.println("Firstname is now: " + firstname);
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		System.out.println("Lastname is now: " + lastname);
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		System.out.println("Email-Adress is now: " + email);
		this.email = email;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		System.out.println("Index is now: " + index);
		this.index = index;
	}

}
