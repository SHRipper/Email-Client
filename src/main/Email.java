package main;

import java.io.IOException;
import java.util.Properties;

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
		Sender = sender;
	}

	public String getRecipiant() {
		return Recipiant;
	}

	public void setRecipiant(String recipiant) {
		Recipiant = recipiant;
	}

	public String getRegard() {
		return Regard;
	}

	public void setRegard(String regard) {
		Regard = regard;
	}

	public String getFont() {
		return Font;
	}

	public void setFont(String font) {
		Font = font;
	}

	public Integer getFontsize() {
		return Fontsize;
	}

	public void setFontsize(Integer fontsize) {
		Fontsize = fontsize;
	}

	public void receiveEmail(String pop3Host, String storeType, String user, String password) {

		try {
			Properties properties = new Properties();
			properties.put("mail.pop3.host", pop3Host);
			Session emailSession = Session.getDefaultInstance(properties);

			POP3Store emailStore = (POP3Store) emailSession.getStore(storeType);
			emailStore.connect(user, password);

			Folder emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				System.out.println("==============================");
				System.out.println("Email #" + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + message.getContent().toString());
			}

			emailFolder.close(false);
			emailStore.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendEmail() {

	}
}
