package main;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;

import com.sun.mail.pop3.POP3Store;

public class Email extends Authenticator {

	private static String Sender;
	private static String Recipiant;
	private static String Regard;
	private static String Font = "Tahoma";
	private static Integer Fontsize = 12;

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

	public void receiveEmail(String pop3Host, String storeType, String user, String password) {
		String port = "995";
		String protocol = "pop3";
		String host = "pop.gmx.net";

		try {
			Properties properties = new Properties();
			properties.put(String.format("mail.%s.host", protocol), host);
			properties.put(String.format("mail.%s.port", protocol), port);

			Session emailSession = Session.getDefaultInstance(properties);

			POP3Store emailStore = (POP3Store) emailSession.getStore(storeType);
			emailStore.connect(user, password);

			Folder emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			for (int i = 0; i < messages.length; i++) {
				Message msg = messages[i];
				Address[] fromAddress = msg.getFrom();
				String from = fromAddress[0].toString();
				String subject = msg.getSubject();
				String sentDate = msg.getSentDate().toString();

				String contentType = msg.getContentType();
				String messageContent = "";

				if (contentType.contains("text/plain") || contentType.contains("text/html")) {
					try {
						Object content = msg.getContent();
						if (content != null) {
							messageContent = content.toString();
						}
					} catch (Exception ex) {
						messageContent = "[Error downloading content]";
						ex.printStackTrace();
					}
				}

				// print out details of each message
				System.out.println("Message #" + (i + 1) + ":");
				System.out.println("\t From: " + from);
				System.out.println("\t To: ");
				System.out.println("\t CC: ");
				System.out.println("\t Subject: " + subject);
				System.out.println("\t Sent Date: " + sentDate);
				System.out.println("\t Message: " + messageContent);
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
		System.out.println("Email sending is not possible!");
	}
}
