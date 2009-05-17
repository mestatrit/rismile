package com.risetek;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class riseye {

	URL url = null;
	String home = "http://riscada.appspot.com/scada/take";
//	String home = "http://127.0.0.1:8080/scada/take";
	static int fileNum = 2;
	
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

			try {
				OutputStream out  = connector.getOutputStream();
				if(++fileNum > 5 ) fileNum = 2;
				String filename = "image/p"+fileNum+".jpg";
				
				System.out.println("POST: "+filename);
				
				FileInputStream imgfile = new FileInputStream(filename);

				try {
					byte[] buf = new byte[10240];
					int len;
					while ((len = imgfile.read(buf)) > 0) {
						out.write(buf,0, len);
					}				
					
					imgfile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int code = connector.getResponseCode();
			System.out.println("Response is: "+code);
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
		int loop = 0;
		while( loop++ < 100 )
		{
			(new riseye()).PostIt();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
