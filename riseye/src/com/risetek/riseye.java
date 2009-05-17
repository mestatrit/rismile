package com.risetek;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class riseye {

	URL url = null;
	String home = "http://riscada.appspot.com/scada/take";
	
	public void PostIt()
	{
		try {
			url = new URL(home);
			HttpURLConnection connector = (HttpURLConnection)url.openConnection();
			connector.setRequestMethod("POST");
			connector.setRequestProperty("Accept", "*/*");
			connector.setRequestProperty("Accept-Language", "zh-cn");
			connector.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
			connector.setDoOutput(true);
			connector.setDoInput(true);			
			connector.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] argv)
	{
		System.out.println("Hello world!");
	}
}
