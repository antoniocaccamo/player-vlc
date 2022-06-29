package it.antanysavage.sm.player.actions;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.eclipse.jface.action.Action;

import it.antanysavage.sm.player.email.EmailSender;


public class SendTestEmailAction extends Action {
	
	private static final Logger logger = LoggerFactory.getLogger(SendTestEmailAction.class);

	public SendTestEmailAction() {
		super( "Send Test Email" , AS_PUSH_BUTTON);
	}
	
	public void run() {
		logger.warn("sending a test email...");
		EmailSender.getInstance().send("Test Email", "This is a test !!!", true);
	}
}
