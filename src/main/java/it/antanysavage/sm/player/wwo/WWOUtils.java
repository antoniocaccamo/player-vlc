package it.antanysavage.sm.player.wwo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class WWOUtils {
    public static BufferedImage loadBitmap(String url) {
    	BufferedImage img = null;
    	try {
    		img = ImageIO.read(new URL(url));
    	} catch (Exception e) {
    		
    	}

        return img;
    }
    
    /**
     * Creates a transparent image.  These can be used for aligning menu items.
     *
     * @param width  the width.
     * @param height the height.
     * @return the created transparent image.
     */
    public static BufferedImage createTransparentImage (final int width, final int height)
    {
      return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Creates a transparent icon. The Icon can be used for aligning menu items.
     *
     * @param width  the width of the new icon
     * @param height the height of the new icon
     * @return the created transparent icon.
     */
    public static Icon createTransparentIcon (final int width, final int height)
    {
      return new ImageIcon(createTransparentImage(width, height));
    }
    
    public static String getIP() {
    	String ip = _getIP();
    	
    	if("ERROR".equals(ip) || "127.0.0.1".equals(ip) || ip.startsWith("192.168."))
    		return null;
    	else {
    		//sometimes the returned string is delimited by spaces
    		String[] tokens = ip.split(" ");
    		if(tokens.length == 1)
    			return ip;
    		else {
    			return join(tokens, "");
    		}
    	}
    }
    
    public static String join(String[] parts, String delim) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            result.append(part);
            if (delim != null && i < parts.length-1) {
                result.append(delim);
            }        
        }
        return result.toString();
    }
    
	// Method to get the IP Address of the Host.
	private static String _getIP()
	{
	    // This try will give the Public IP Address of the Host.
	    try
	    {
	        URL url = new URL("http://whatismyip.akamai.com/");
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        String ipAddress = new String();
	        ipAddress = (in.readLine()).trim();
	        /* IF not connected to internet, then
	         * the above code will return one empty
	         * String, we can check it's length and
	         * if length is not greater than zero, 
	         * then we can go for LAN IP or Local IP
	         * or PRIVATE IP
	         */
	        if (!(ipAddress.length() > 0))
	        {
	            try
	            {
	                InetAddress ip = InetAddress.getLocalHost();
	                System.out.println((ip.getHostAddress()).trim());
	                return ((ip.getHostAddress()).trim());
	            }
	            catch(Exception ex)
	            {
	                return "ERROR";
	            }
	        }
	        System.out.println("IP Address is : " + ipAddress);

	        return (ipAddress);
	    }
	    catch(Exception e)
	    {
	        // This try will give the Private IP of the Host.
	        try
	        {
	            InetAddress ip = InetAddress.getLocalHost();
	            System.out.println((ip.getHostAddress()).trim());
	            return ((ip.getHostAddress()).trim());
	        }
	        catch(Exception ex)
	        {
	            return "ERROR";
	        }
	    }
	}
}
