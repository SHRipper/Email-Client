package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectDB {

	private Connection con;
	private Statement st;
	private ResultSet rs;

	public ConnectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/email-client", "root", "Schaefl07");
			st = con.createStatement();

			System.out.println("Connected to Database: localhost/email-client");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<String> getData() {

		try {
			ArrayList<String> list = new ArrayList<>();

			String query = "SELECT * FROM contacts";
			rs = st.executeQuery(query);

			while (rs.next()) {
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String email = rs.getString("email");
				list.add(firstname + " " + lastname + "    //    " + email);
			}
			con.close();
			return list;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
}