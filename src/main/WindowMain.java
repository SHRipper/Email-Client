package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class WindowMain {

	private JFrame frameMain;

	// Table Models
	private DefaultTableModel tableModelContacts;
	private DefaultTableModel tableModelInbox;
	private DefaultTableModel tableModelDrafts;
	private DefaultTableModel tableModelSent;

	// Buttons
	private JButton btnSendTo;

	// Panels
	private JPanel panelOverview;
	private JPanel panelEdit;

	// TextFields
	private JTextField txtSender;
	private JTextField txtRecipiant;
	private JTextField txtRegard;
	private JTextPane txtMessage;

	private static Email email;

	private JScrollPane scrollPaneTable;
	private JTable tableMain;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					WindowMain window = new WindowMain();
					window.createWindowMain();
					window.frameMain.setVisible(true);
					email = new Email();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public void createWindowMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameMain = new JFrame();
		frameMain.setBounds(100, 100, 1200, 1000);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.getContentPane().setLayout(new CardLayout(0, 0));

		frameMain.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {

				// initial email fetch
				fillEmailTable();
			}
		});
		/*
		 * PANELS
		 */

		panelOverview = new JPanel();
		panelOverview.setBackground(new Color(224, 255, 255));
		frameMain.getContentPane().add(panelOverview, "name_6289251837624");
		panelOverview.setLayout(null);
		panelOverview.setVisible(true);

		panelEdit = new JPanel();
		panelEdit.setBackground(new Color(224, 255, 255));
		frameMain.getContentPane().add(panelEdit, "name_6290427306589");
		panelEdit.setLayout(null);

		/*
		 * LABELS
		 */

		JLabel lblSender = new JLabel("From:");
		lblSender.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSender.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblSender.setBounds(175, 30, 81, 20);
		panelEdit.add(lblSender);

		JLabel lblRegard = new JLabel("Regard:");
		lblRegard.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRegard.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblRegard.setBounds(175, 80, 81, 20);
		panelEdit.add(lblRegard);

		/*
		 * BUTTONS
		 */

		JButton btnReceive = new JButton("Receive Emails");
		btnReceive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// receive emails and fill the table with data
				fillEmailTable();
			}
		});
		btnReceive.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		btnReceive.setBounds(160, 60, 146, 50);
		panelOverview.add(btnReceive);

		JButton btnNewButton = new JButton("New...");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// hide "From-Label and Textfield"
				lblSender.setVisible(false);
				txtSender.setVisible(false);

				// reveal To-Button and textfield
				btnSendTo.setVisible(true);
				txtRecipiant.setVisible(true);

				// change to edit panel
				panelOverview.setVisible(false);
				panelEdit.setVisible(true);
				System.out.println("Changed to Edit Panel");
			}
		});
		btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		btnNewButton.setBounds(50, 60, 100, 50);
		panelOverview.add(btnNewButton);

		JButton btnSend = new JButton("Send...");
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Sending E-mail...");
				// set email data from textfields and send the email
				email.setRecipiant(txtRecipiant.getText());
				email.setRegard(txtRegard.getText());
				email.setBody(txtMessage.getText());
				email.sendEmail();
			}
		});
		btnSend.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		btnSend.setBounds(1017, 38, 100, 50);
		panelEdit.add(btnSend);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// go back to overview panel
				panelOverview.setVisible(true);
				panelEdit.setVisible(false);
				System.out.println("Changed to Overview Panel");
			}
		});
		btnBack.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		btnBack.setBounds(48, 31, 66, 23);
		panelEdit.add(btnBack);

		JButton btnRecipiant = new JButton("To:");
		btnRecipiant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// create modal Contacts Window

				ContactDialog cd = new ContactDialog();

				// set textbox value to selected contact
				txtRecipiant.setText(cd.getSelectedAdress());

			}
		});
		btnRecipiant.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		btnRecipiant.setBounds(201, 54, 55, 23);
		panelEdit.add(btnRecipiant);

		btnSendTo = new JButton("Send Email...");
		btnSendTo.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		btnSendTo.setBounds(316, 60, 146, 50);
		btnSendTo.setVisible(false);
		panelOverview.add(btnSendTo);
		btnSendTo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelOverview.setVisible(false);
				panelEdit.setVisible(true);
				txtRecipiant.setText("");
			}
		});

		/*
		 * TEXT FIELDS
		 */

		txtMessage = new JTextPane();
		txtMessage.setBounds(100, 155, 1000, 688);
		panelEdit.add(txtMessage);
		panelEdit.setVisible(false);

		txtSender = new JTextField();
		txtSender.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		txtSender.setBounds(266, 32, 671, 20);
		panelEdit.add(txtSender);
		txtSender.setColumns(10);

		txtRecipiant = new JTextField();
		txtRecipiant.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		txtRecipiant.setBounds(266, 57, 671, 20);
		panelEdit.add(txtRecipiant);
		txtRecipiant.setColumns(10);

		txtRegard = new JTextField();
		txtRegard.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		txtRegard.setBounds(266, 82, 671, 20);
		panelEdit.add(txtRegard);
		txtRegard.setColumns(10);

		/*
		 * TREE VIEW
		 */

		JTree treeMain = new JTree();
		treeMain.setBorder(new LineBorder(new Color(0, 0, 0)));
		treeMain.setBackground(new Color(204, 204, 204));
		treeMain.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		treeMain.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("User") {
			{
				add(new DefaultMutableTreeNode("Inbox"));
				add(new DefaultMutableTreeNode("Sent"));
				add(new DefaultMutableTreeNode("Drafts"));
				add(new DefaultMutableTreeNode("Contacts"));
			}
		}));
		treeMain.setBounds(10, 149, 159, 745);
		panelOverview.add(treeMain);
		treeMain.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				switch (e.getPath().toString()) {
				case "[User, Inbox]":
					showInbox();
					break;
				case "[User, Sent]":
					showSent();
					break;
				case "[User, Drafts]":
					showDrafts();
					break;
				case "[User, Contacts]":
					showContacts();
					break;
				}
			}
		});

		/*
		 * TABLE
		 */

		JScrollPane scrollPaneTable = new JScrollPane();
		scrollPaneTable.setBounds(217, 149, 930, 745);
		panelOverview.add(scrollPaneTable);

		tableMain = new JTable();
		tableMain.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		scrollPaneTable.setViewportView(tableMain);
		showInboxTable();

		tableMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// reveal "From-Label and Textfield"
					lblSender.setVisible(true);
					txtSender.setVisible(true);

					// hide To-Button and textfield
					btnSendTo.setVisible(false);
					btnSendTo.setEnabled(false);
					txtRecipiant.setVisible(false);

					// change into edit window
					panelOverview.setVisible(false);
					panelEdit.setVisible(true);

					// fill edit window with information from the message
					txtSender.setText("");
				}
			}
		});

		tableMain.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				tableMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "remove row");
				tableMain.getActionMap().put("remove row", new AbstractAction() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Integer selectedRow = tableMain.getSelectedRow();
						if (selectedRow >= 0) {

							int[] rows = tableMain.getSelectedRows();
							// sort the indices of selected Rows because if you
							// delete 4, 5 is now 4
							Arrays.sort(rows);

							// remove from last indices to first
							for (int i = tableMain.getSelectedRowCount() - 1; i >= 0; i--) {
								tableModelInbox.removeRow(rows[i]);
							}

							Integer currentRows = tableModelInbox.getRowCount();

							// after row deletion action
							if (currentRows - 1 >= selectedRow && currentRows != 0) {
								// select the row which is in the same place
								tableMain.setRowSelectionInterval(selectedRow, selectedRow);

							} else if (currentRows == selectedRow && currentRows != 0) {
								// select the last row in the list
								tableMain.setRowSelectionInterval(currentRows - 1, currentRows - 1);

							}
						}
					}
				});
			}
		});

		/*
		 * MENU
		 */

		JMenuBar menuBar = new JMenuBar();
		frameMain.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmReceive = new JMenuItem("Receive Emails");
		mntmReceive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		mnFile.add(mntmReceive);

		JMenuItem mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ContactDialog contactDialog = new ContactDialog();

			}
		});
		mnFile.add(mntmSettings);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmExit = new JMenuItem("Exit...");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frameMain.dispatchEvent(new WindowEvent(frameMain, WindowEvent.WINDOW_CLOSING));
			}
		});
		mnFile.add(mntmExit);

		System.out.println("WindowMain created successfully");

	}

	private void showInbox() {
		System.out.println("Inbox Folder");
		showInboxTable();
	}

	private void showSent() {
		System.out.println("Sent Folder");
	}

	private void showDrafts() {
		System.out.println("Drafts Folder");
	}

	private void showContacts() {
		System.out.println("Contacts Folder");
		showContactsTable();

		if (tableMain.getSelectedRow() != -1) {
			btnSendTo.setVisible(true);
		}

	}

	private void showInboxTable() {
		tableModelInbox = new DefaultTableModel(new Object[][] {}, new String[] { "Regard", "Sender", "Date" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableMain.setModel(tableModelInbox);

	}

	private void showContactsTable() {
		tableModelContacts = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Email address" });

		// connect to db and extract table data
		ConnectDB db = new ConnectDB();
		tableModelContacts.addRow(db.getTableData().toArray());
		tableMain.setModel(tableModelContacts);
	}

	private void fillEmailTable() {

		// get the email from the server (-1 == get every email)
		email.receiveEmail(-1);

		// get the list of data and crop it to the table format
		ArrayList<String> emailList = email.getTableContent();
		for (int i = 0; i < emailList.size() - 3; i = i + 3) {
			tableModelInbox.addRow(emailList.subList(i, i + 3).toArray());
		}
		tableMain.addRowSelectionInterval(0, 0);
	}
}