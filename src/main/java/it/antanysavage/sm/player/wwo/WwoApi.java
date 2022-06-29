package it.antanysavage.sm.player.wwo;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLEncoder;

public class WwoApi {
//	public static final boolean LOGD = true;

	//These keys are only for Demo purpose.
	//You should replace the key with your own key.
	//You can obtain your own key after registering on World Weather Online website.
	public static final String FREE_API_KEY = "xkq544hkar4m69qujdgujn7w";
	public static final String PREMIUM_API_KEY = "w9ve379xdu8etugm7e2ftxd6";
	
	String key;
	String apiEndPoint;
	
	WwoApi(boolean freeAPI) {
		if(freeAPI) {
			key = FREE_API_KEY;
		} else {
			key = PREMIUM_API_KEY;
		}
	}
	
	WwoApi setKey(String key) {
		this.key = key;
		return this;
	}
	
	WwoApi setApiEndPoint(String apiEndPoint) {
		this.apiEndPoint = apiEndPoint;
		return this;
	}
	
	class RootParams {
		String getQueryString(Class cls) {
			String query = null;
			
			Field[] fields = cls.getDeclaredFields();
			
			try {
			  for (Field field : fields) {
				  Object f = field.get(this);
				  if(f != null) {
					if(query == null)
						query = "?" + URLEncoder.encode(field.getName(), "UTF-8") + "="
							+ URLEncoder.encode((String)f, "UTF-8");
					else
						query += "&" + URLEncoder.encode(field.getName(), "UTF-8") + "="
								+ URLEncoder.encode((String)f, "UTF-8");
				  }
			  }
			} catch (Exception e) {
				
			}
			
			return query;
		}
	}
	
	static InputStream getInputStream(String url) {
		InputStream is = null;
		
		try {
			is = (new URL(url)).openConnection().getInputStream();
		} catch (Exception e) {
			
		}
		
		return is;
	}
}