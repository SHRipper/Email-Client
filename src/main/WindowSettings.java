package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WindowSettings {

	private JFrame frameSettings;
	private JComboBox cbFont;
	private JLabel lblFontExample;

	private static Email email;

	/**
	 * Launch the application.
	 */
	public void createSettings() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					WindowSettings window = new WindowSettings();
					window.frameSettings.setVisible(true);
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
	public WindowSettings() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameSettings = new JFrame();
		frameSettings.setTitle("Settings");
		frameSettings.setBounds(100, 100, 522, 485);
		frameSettings.setDefaultCloseOperation(frameSettings.DISPOSE_ON_CLOSE);
		frameSettings.getContentPane().setLayout(new CardLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frameSettings.getContentPane().add(tabbedPane, "name_4591055611414");

		/*
		 * COMBOBOX
		 */
		ArrayList<String> Fonts = new ArrayList<>();
		Fonts.add("Trebuchet MS");
		Fonts.add("Comic Sans MS");
		Fonts.add("Tahoma");
		Fonts.add("Courier New");

		JPanel panelGeneral = new JPanel();
		tabbedPane.addTab("General", null, panelGeneral, null);
		panelGeneral.setLayout(null);

		JPanel panelFont = new JPanel();
		tabbedPane.addTab("Font", null, panelFont, null);
		panelFont.setLayout(null);

		JLabel lblFontsize = new JLabel("Fontsize:");
		lblFontsize.setBounds(10, 40, 70, 25);
		lblFontsize.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFontsize.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		panelFont.add(lblFontsize);

		JSpinner spinnerFontsize = new JSpinner();
		spinnerFontsize.setBounds(91, 44, 71, 20);
		spinnerFontsize.setModel(new SpinnerNumberModel(new Integer(12), new Integer(4), null, new Integer(1)));
		panelFont.add(spinnerFontsize);
		spinnerFontsize.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				email.setFontsize((Integer) spinnerFontsize.getValue());
				lblFontExample.setFont(new Font(email.getFont(), Font.PLAIN, email.getFontsize()));
			}
		});

		JLabel lblFont = new JLabel("Font:");
		lblFont.setBounds(10, 98, 70, 25);
		lblFont.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFont.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		panelFont.add(lblFont);

		cbFont = new JComboBox(Fonts.toArray());
		cbFont.setBounds(91, 101, 130, 20);
		cbFont.setToolTipText("");
		cbFont.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		panelFont.add(cbFont);
		cbFont.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				switch (e.getItem().toString()) {

				case "Trebuchet MS":
					email.setFont("Trebuchet MS");
					break;
				case "Comic Sans MS":
					email.setFont("Comic Sans MS");
					break;
				case "Tahoma":
					email.setFont("Tahoma");
					break;
				case "Courier New":
					email.setFont("Courier New");
					break;
				}
				lblFontExample.setFont(new Font(email.getFont(), Font.PLAIN, email.getFontsize()));
			}
		});

		JPanel panelFontExample = new JPanel();
		panelFontExample.setBounds(38, 212, 424, 99);
		panelFont.add(panelFontExample);
		panelFontExample.setLayout(null);

		JLabel lblNewLabel = new JLabel("Example:");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 38, 89, 26);
		panelFontExample.add(lblNewLabel);

		lblFontExample = new JLabel("ABCDEFG abcdefg 12345");
		lblFontExample.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
		lblFontExample.setHorizontalAlignment(SwingConstants.CENTER);
		lblFontExample.setBounds(87, 11, 327, 77);
		lblFontExample.setBorder(new LineBorder(Color.BLACK));
		panelFontExample.add(lblFontExample);

	}

	private void changeExample() {

	}
}
