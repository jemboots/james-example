package com.jms.memoenglishwords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class Utils {
	
	public static String readStringFromStream(InputStream inputStream) {
		String resultString = null;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			resultString = sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultString;
	}
	
    public static void CopyStream(InputStream is, OutputStream os) throws IOException
    {
        final int buffer_size=1024;
        byte[] bytes=new byte[buffer_size];
        for(;;)
        {
          int count=is.read(bytes, 0, buffer_size);
          if(count==-1)
              break;
          os.write(bytes, 0, count);
        }
    }
}
