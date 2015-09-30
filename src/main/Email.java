package main;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.pop3.POP3Store;

public class Email extends Authenticator {

	private static String Sender;
	private static String Recipiant;
	private static String Regard;
	private static String Body;
	private static String Font = "Tahoma";
	private static String sentDate;
	private static Integer Fontsize = 12;
	private static ArrayList<String> tableContent;

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		System.out.println("Sender is now: " + sender);
		Sender = sender;
	}

	public String getRecipiant() {
		return Recipiant;
	}

	public void setRecipiant(String recipiant) {
		System.out.println("Recipiant is now: " + recipiant);
		Recipiant = recipiant;
	}

	public String getRegard() {
		return Regard;
	}

	public void setRegard(String regard) {
		System.out.println("Regard is now: " + regard);
		Regard = regard;
	}

	public String getFont() {
		return Font;
	}

	public void setFont(String font) {
		System.out.println("Font is now: " + font);
		Font = font;
	}

	public Integer getFontsize() {
		return Fontsize;
	}

	public void setFontsize(Integer fontsize) {
		System.out.println("Fontsize is now: " + fontsize);
		Fontsize = fontsize;
	}

	public void setBody(String body) {
		Body = body;
	}

	public ArrayList<String> getTableContent() {
		return tableContent;
	}

	public void receiveEmail(Integer index) {

		// receives emails and generates an array with the information about the
		// email
		// the first email index is 0. A index of -1 generates an array with
		// information about every email.

		String port = "995";
		String host = "pop.gmx.net";

		final String user = "lukas-schaef@gmx.de";
		final String password = "schaefl07";

		tableContent = new ArrayList<>();

		try {
			Properties props = new Properties();
			props.put("mail.pop3.host", host);
			props.put("mail.pop3.port", port);
			props.put("mail.store.protocol", "pop3");
			props.put("mail.pop3.auth", "true");
			props.put("mail.pop3.starttls.enable", "true");

			// start session with properties set above
			Session emailSession = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});

			// connect to pop3 storage
			POP3Store emailStore = (POP3Store) emailSession.getStore("pop3s");
			emailStore.connect(host, user, password);

			// select the inbox folder
			Folder emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// create array
			Message[] messages = emailFolder.getMessages();

			if (index == -1) {
				// get information about all messages and add to Arraylist
				for (int i = 0; i < messages.length; i++) {
					Message msg = messages[i];
					Address[] fromAddress = msg.getFrom();
					String sender = fromAddress[0].toString();
					String regard = msg.getSubject();
					String sentDate = msg.getSentDate().toString();

					tableContent.add(regard);
					tableContent.add(sender);
					tableContent.add(sentDate);

				}

			} else {
				// get information about the message at the index and add its
				// information to an Arraylist
				Message msg = messages[index];
				Address[] fromAddress = msg.getFrom();
				String sender = fromAddress[0].toString();
				String regard = msg.getSubject();

				tableContent.add(regard);
				tableContent.add(sender);
				tableContent.add(""); // message

			}

			emailFolder.close(false);
			emailStore.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public void sendEmail() {

		String from = "lukas-schaef@gmx.de";
		final String username = "lukas-schaef@gmx.de";
		final String password = "schaefl07";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "mail.gmx.net");
		props.put("mail.smtp.port", "587");

		// create session with properties set above
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// set the subject, recipient and body for the message
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(Recipiant));
			message.setSubject(Regard);
			message.setText(Body);

			// send it
			Transport.send(message);

			System.out.println("Email was successfully sent!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
