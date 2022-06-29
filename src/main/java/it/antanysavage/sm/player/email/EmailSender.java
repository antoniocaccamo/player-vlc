package it.antanysavage.sm.player.email;

import java.io.File;
import java.text.MessageFormat;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

import it.antanysavage.sm.player.Player;
import it.antanysavage.sm.player.sequences.model.Media;

public class EmailSender {

	private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

	private static final MessageFormat MEDIA_ERROR = new MessageFormat(
			"Error occured at :\n"
					+ " - Computer : {0} \n"
					+ " - Media    : {1}"
			);

	private static final EmailSender instance = new EmailSender();

	// private String computer;

	private EmailSender() {
		//		try {
		//		computer =  InetAddress.getLocalHost().getHostName();
		//		} catch (Exception e ) {
		//			e.printStackTrace(System.err);
		//			computer = System.getenv("COMPUTERNAME");
		//		}
	}

	public static EmailSender getInstance() {
		return instance;
	}

	public void sendMediaError(Media media) {

		if ( Player.APP_SEND_ALL_MAIL ||  ! media.isErrorEmailSent() ) {

			String subject = "AT ADV Player : media error";
			String message =
					MEDIA_ERROR.format(
							new String[]{
									Player.COMPUTER,
									( media == null ? "" : media.toString() )
							});
			send(subject, message);

			media.setErrorEmailSent(true);
		}
	}

	public void send(String subject, String message) {
		send(subject, message, true);	
	}

	public void send(final String subject, String message, boolean insertComputerName) {
		final StringBuffer sb = new StringBuffer();
		try {
			final MultiPartEmail  email = prepareEmail();
			email.setSubject(subject);
			if ( insertComputerName ) {
				sb.append("Computer  [")
				.append(Player.COMPUTER).append("]\n")
				.append(message)
				.toString();								 
			} else {
				sb.append(message.toString());
			}								
			email.setMsg(sb.toString());

			File zos = Player.getZipLogFile();

			if ( zos != null ) {

				EmailAttachment attachment = new EmailAttachment();

				attachment.setPath(zos.getAbsolutePath());
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				attachment.setName("log.zip");

				email.attach(attachment);
			}

			new Thread(){
				@Override
				public void run() {
					try {
						email.send();
						logger.info("email sent : subject ["+subject+"] message ["+sb.toString()+"]");
					} catch (EmailException e) {						
						logger.error("error on send email : subject ["+subject+"] message ["+sb.toString()+"]", e);
					}

				}}.start();					

		} catch (Exception e) {			
			logger.error("error on send email : subject ["+subject+"] message ["+sb.toString()+"]", e);
		}						
	}


	private MultiPartEmail  prepareEmail() throws EmailException {
		MultiPartEmail  email = new MultiPartEmail ();

		email.setHostName("smtps.aruba.it");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("support@arttechonline.com", "Software.1"));		
		email.setFrom("noreply@arttechonline.com");
		//		email.setSubject("AT ADV Player TestMail");
		//		email.setMsg("This is a test mail ... :-)");

		email.addCc("assistenza@maxischermiled.it");
		email.addTo("support@arttechonline.com");

		email.setTLS(true);
		email.setSSL(true);

		email.setDebug(true);

		return email;

	}

//	public static void main(String[] args) {
//		Init.init();
//		try {
//
//			Email email = new SimpleEmail();
//
//
//			/*
//			email.setHostName("smtps.aruba.it");
//			email.setSmtpPort(465);
//			email.setAuthenticator(new DefaultAuthenticator("support@arttechonline.com", "Software.1"));
//			email.setTLS(true);
//
//
//			email.setFrom("noreply@arttechonline.com");
//			email.setSubject("AT ADV Player TestMail");
//			email.setMsg("This is a test mail ... :-)");
//
//			email.addTo("assistenza@maxischermiled.it");
//			email.addTo("support@arttechonline.com");
//			email.addTo("simone.lisa@arttechonline.com");
//
//
//			email.setSSL(true);
//			email.setDebug(true);
//
//			email.send();
//			 */
//
//			EmailSender.getInstance().send("test", "test", true);
//
//			System.out.println("email sent !!!");
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	
}


