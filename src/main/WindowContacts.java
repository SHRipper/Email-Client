package main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WindowContacts {

	private DefaultListModel<String> listmodel;
	private Recipiant Recipiant;
	private JFrame frameContacts;
	private Boolean isDisposed;

	/**
	 * Launch the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void createWindowContacts() {

		try {
			Recipiant = new Recipiant();
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frameContacts = new JFrame();
		frameContacts.setTitle("Choose Recipiant");
		frameContacts.setResizable(false);
		frameContacts.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		frameContacts.setBounds(100, 100, 508, 560);
		frameContacts.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameContacts.getContentPane().setLayout(null);
		frameContacts.setVisible(true);

		/*
		 * JLIST
		 */

		JList<String> listContacts = new JList();
		listContacts.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				Recipiant.setIndex(listContacts.getSelectedIndex());
			}
		});
		listmodel = new DefaultListModel<String>();
		listContacts.setModel(listmodel);
		listContacts.setBounds(10, 65, 482, 359);
		frameContacts.getContentPane().add(listContacts);

		fillListContacts();

		/*
		 * LABEL
		 */

		JLabel lbl1 = new JLabel("Send to... ");
		lbl1.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		lbl1.setBounds(10, 11, 181, 43);
		frameContacts.getContentPane().add(lbl1);

		/*
		 * JBUTTON
		 */

		JButton btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		btnInsert.setBounds(206, 462, 89, 43);
		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				btnInsert_onClick();
			}
		});
		frameContacts.getContentPane().add(btnInsert);
	}

	private void fillListContacts() {

		// connect to database and extract the contacts inside
		ConnectDB connect = new ConnectDB();
		ArrayList<String> ContactList = connect.getData();

		// fill the list with elements
		for (int i = 0; i < ContactList.size(); i++) {
			listmodel.addElement(ContactList.get(i));
		}

	}

	private void btnInsert_onClick() {

		String address = getSelectedAdress();

		// set recipiants adress in email
		Email email = new Email();
		email.setRecipiant(address);

		frameContacts.dispose();

	}

	public String getSelectedAdress() {

		// cut the Information of the Recipiant to just the email address
		Integer currentIndex = Recipiant.getIndex();
		String selectedContact = listmodel.get(currentIndex);
		System.out.println(selectedContact);
		String selectedAdress = null;

		for (int i = 0; i < selectedContact.length(); i++) {
			if (selectedContact.charAt(i) == '/') {

				selectedAdress = selectedContact.substring(i + 5);
			}
		}
		System.out.println("You selected this Address: " + selectedAdress);
		return selectedAdress;
	}

}
