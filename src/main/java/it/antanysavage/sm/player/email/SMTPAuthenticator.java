package it.antanysavage.sm.player.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator
{
	 String username = null;
     String password = null;
     
     public SMTPAuthenticator( String username, String password) {
		super();
		this.username = username;
		this.password = password;
		
	}

    public PasswordAuthentication getPasswordAuthentication( )
    {       
        return new PasswordAuthentication(username, password);
    }
    
    
    
    
    
    
}
