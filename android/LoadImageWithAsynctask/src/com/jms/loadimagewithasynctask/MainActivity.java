package com.jms.loadimagewithasynctask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	    Bitmap bitmap = null;
	    InputStream in = null;
	    BufferedOutputStream out = null;

	    String url = "http://ranpict.com/wp-content/uploads/2013/02/Light-Android-Wallpaper.jpg";
	    try {
	        in = new BufferedInputStream(new URL(url).openStream(), 16*1024);

	        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	        out = new BufferedOutputStream(dataStream, 16*1024);
	        CopyStream(in, out);
	        out.flush();

	        final byte[] data = dataStream.toByteArray();
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        //options.inSampleSize = 1;

	        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
	    } catch (IOException e) {
	        Log.e("error", "Could not load Bitmap from: " + url);
	    } finally {
	    	try {
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    ImageView imageView = (ImageView) this.findViewById(R.id.imageView);
	    imageView.setImageBitmap(bitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private void CopyStream(InputStream is, OutputStream os) throws IOException
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
