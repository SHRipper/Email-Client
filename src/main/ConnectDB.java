package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ConnectDB {

	private Connection con;
	private Statement st;
	private ResultSet rs;

	private ArrayList<String> list;

	private Integer linesTotal;

	public ConnectDB() {

		// Connect to local SQLite database
		try {
			Class.forName("org.sqlite.JDBC");

			con = DriverManager.getConnection("jdbc:sqlite:E:/SQLite/Contacts");
			st = con.createStatement();

			System.out.println("Connected to Database: Contacts");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ups, something went wrong", "", JOptionPane.ERROR_MESSAGE);
		}
	}

	public ArrayList<String> getListData() {
		// get the DB data (email, firstname and lastname) formated for lists

		try {
			list = new ArrayList<>();

			// select every row from table contacts
			String query = "SELECT * FROM contacts";
			rs = st.executeQuery(query);

			while (rs.next()) {
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String email = rs.getString("email");
				// list format
				list.add(firstname + " " + lastname + "    //    " + email);
			}
			con.close();
			return list;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ups, something went wrong", "", JOptionPane.ERROR_MESSAGE);
			return null;
		}

	}

	public ArrayList<String> getTableData() {
		// get the DB data formatted for tables

		try {
			// select every row from table contacts
			String query = "SELECT * FROM contacts";
			rs = st.executeQuery(query);

			while (rs.next()) {
				// get full name and email address and put it into the Arraylist
				String fullName = rs.getString("firstname") + " " + rs.getString("lastname");
				String email = rs.getString("email");
				list.add(fullName);
				list.add(email);
			}
			con.close();
			return list;
		} catch (SQLException e) {
			System.out.println("Error: " + e);
			return null;
		}

	}

}