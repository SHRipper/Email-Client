package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ContactDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultListModel<String> listmodel;
	private Integer selectedIndex = null;
	private ContactDialog dialog;
	private Integer i;

	/**
	 * Launch the application.
	 */
	public void createDialog() {
		try {
			dialog = new ContactDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ContactDialog() {
		setModal(true);
		setBounds(100, 100, 463, 433);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JList ContactList = new JList();
		ContactList.setBounds(10, 45, 427, 303);
		listmodel = new DefaultListModel<>();
		ContactList.setModel(listmodel);
		contentPanel.add(ContactList);
		ContactList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedIndex = ContactList.getSelectedIndex();
			}
		});

		fillContactList();

		JLabel lbl1 = new JLabel("Send to...");
		lbl1.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		lbl1.setBounds(10, 11, 223, 24);
		contentPanel.add(lbl1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
						i = 0;
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
						i = 0;
					}
				});
			}
		}
	}

	private void fillContactList() {

		// connect to database and extract the contacts inside
		ConnectDB connect = new ConnectDB();
		ArrayList<String> ContactList = connect.getData();

		// fill the list with elements
		for (int i = 0; i < ContactList.size(); i++) {
			listmodel.addElement(ContactList.get(i));
		}

	}

	public String getSelectedAdress() {

		// cut the Information of the Recipiant to just the email address
		String selectedContact = listmodel.get(selectedIndex);
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
