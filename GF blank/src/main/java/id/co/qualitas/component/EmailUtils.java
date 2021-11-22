package id.co.qualitas.component;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtils {
	public static boolean sendEmail(Map<String, Object> request) {

		String from = Cons.BASE_EMAIL;
		MimeMessage message = null;

		MimeMultipart mimeMultipart = new MimeMultipart();
		MimeBodyPart messageBodyPart = new MimeBodyPart();

		try {
			message = new MimeMessage(getSession());
//			message.setFrom(new InternetAddress(from, request.get("recipientName").toString()));
			message.setFrom(new InternetAddress(from, "Tester"));
			
			
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(request.get("recipientEmail").toString()));
			
//			String recipient= "natalia@qualitas.co.id";
			String recipient = request.get("recipientEmail").toString();
//			String[] recipientList = recipients.split(",");
			InternetAddress[] recipientAddress = new InternetAddress[1];
			
			//multiple recipient
//			int counter = 0;
//			for (String recipient : recipientList) {
//			    recipientAddress[counter] = new InternetAddress(recipient.trim());
//			    counter++;
//			}
			
			recipientAddress[0] = new InternetAddress(recipient.trim());
			message.setRecipients(Message.RecipientType.TO, recipientAddress);
			message.setSubject(request.get("subject").toString());

			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(request.get("body").toString(), "text/html");
			mimeMultipart.addBodyPart(messageBodyPart);

			if (request.get("attachment") != null) { // attachFile((File)
				messageBodyPart = new MimeBodyPart();
				attachFile((File) request.get("attachment"), mimeMultipart, messageBodyPart);
			}

			message.setContent(mimeMultipart);

			// Set configs for sending email
			Transport transport = getSession().getTransport("smtp");
			transport.connect(Cons.EMAIL_HOST, from, Cons.BASE_EMAIL_PASSWORD);
			// Send email
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void attachFile(File file, MimeMultipart multipart, MimeBodyPart messageBodyPart)
			throws MessagingException {
		DataSource source = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(file.getName());
		multipart.addBodyPart(messageBodyPart);
	}

	public static Session getSession() {

		// Set system properties
		Properties properties = System.getProperties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", true);
		properties.setProperty("mail.smtp.host", Cons.EMAIL_HOST);
		properties.setProperty("mail.smtp.port", Cons.EMAIL_PORT);
		properties.setProperty("mail.smtp.user", Cons.BASE_EMAIL);
		properties.setProperty("mail.smtp.password", Cons.BASE_EMAIL_PASSWORD);
		properties.setProperty("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(properties);

		return session;
	}

}
