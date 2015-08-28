package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class WindowMain {

	private JFrame frameMain;

	// ArrayList for tableMain
	ArrayList<String> rowData;

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
				String mailPop3Host = "pop.gmx.net";
				String mailStoreType = "pop3";
				String user = "lukas-schaef@gmx.de";
				String password = "schaefl07";

				email.receiveEmail(mailPop3Host, mailStoreType, user, password);
			}
		});
		btnReceive.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		btnReceive.setBounds(160, 60, 146, 50);
		panelOverview.add(btnReceive);

		JButton btnNewButton = new JButton("New...");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// "New.." Button clicked
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
				// create Contacts Window

				ContactDialog cd = new ContactDialog();
				txtRecipiant.setText(cd.getSelectedAdress());
				System.out.println("closed");
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

		JTextPane txtNewEmail = new JTextPane();
		txtNewEmail.setBounds(100, 155, 1000, 688);
		panelEdit.add(txtNewEmail);
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
		rowData = new ArrayList<>();
		rowData.add("test");
		rowData.add("lukas");
		rowData.add("26.08.2015");
		tableMain.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		scrollPaneTable.setViewportView(tableMain);
		showInboxTable();

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
				WindowSettings ws = new WindowSettings();
				ws.createSettings();
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
		tableModelInbox = new DefaultTableModel(new Object[][] {}, new String[] { "Regard", "Sender", "Date" });
		// rowData is just an example array
		tableModelInbox.addRow(rowData.toArray());
		tableMain.setModel(tableModelInbox);
	}

	private void showContactsTable() {
		tableModelContacts = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Email address" });

		// connect to db and extract table data
		ConnectDB db = new ConnectDB();
		tableModelContacts.addRow(db.getTableData().toArray());
		tableMain.setModel(tableModelContacts);
	}
}
