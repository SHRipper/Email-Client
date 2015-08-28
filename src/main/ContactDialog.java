package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ContactDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultListModel<String> listmodel;
	private JList<String> ContactList;
	private JDialog dialog;

	/**
	 * Create the dialog.
	 */
	public ContactDialog() {
		setBounds(100, 100, 474, 436);
		getContentPane().setLayout(new BorderLayout());
		setModalityType(ModalityType.APPLICATION_MODAL);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		ContactList = new JList();
		ContactList.setBorder(new LineBorder(new Color(0, 0, 0)));
		ContactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ContactList.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		ContactList.setBounds(10, 58, 438, 295);
		listmodel = new DefaultListModel<>();
		ContactList.setModel(listmodel);
		contentPanel.add(ContactList);
		fillListContacts();

		JLabel lblNewLabel = new JLabel("Send to...");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
		lblNewLabel.setBounds(10, 10, 217, 48);
		contentPanel.add(lblNewLabel);
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
						buttonAction();

					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						buttonAction();
					}
				});
			}
		}

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void buttonAction() {
		Window win = SwingUtilities.getWindowAncestor(this);
		win.dispose();
	}

	public String getSelectedAdress() {

		// cut the Information of the Recipiant to just the email address
		Integer currentIndex = ContactList.getSelectedIndex();
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

	private void fillListContacts() {

		// connect to database and extract the contacts inside
		ConnectDB connect = new ConnectDB();
		ArrayList<String> ContactList = connect.getListData();

		// fill the list with elements
		for (int i = 0; i < ContactList.size(); i++) {
			listmodel.addElement(ContactList.get(i));
		}

	}
}
