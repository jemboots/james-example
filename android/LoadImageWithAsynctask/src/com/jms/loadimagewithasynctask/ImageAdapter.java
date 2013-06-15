package com.jms.loadimagewithasynctask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ImageAdapter extends ArrayAdapter<String> {
	private String[] imageURLArray;
	private LayoutInflater inflater;

	public ImageAdapter(Context context, int textViewResourceId,
			String[] imageArray) {
		super(context, textViewResourceId, imageArray);
		// TODO Auto-generated constructor stub

		inflater = ((Activity)context).getLayoutInflater();
		imageURLArray = imageArray;
	}

	private static class ViewHolder {
		ImageView imageView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.imageitem, null);

			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.testImage);
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder)convertView.getTag();
		
		//load image directly
		Bitmap imageBitmap = null;
		try {
			URL imageURL = new URL(imageURLArray[position]);
            imageBitmap = BitmapFactory.decodeStream(imageURL.openStream());
			viewHolder.imageView.setImageBitmap(imageBitmap);
			
			BufferedInputStream inputStream;
			BufferedOutputStream outputStream;
			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			inputStream = new BufferedInputStream(imageURL.openStream(), 16*1024);
			outputStream = new BufferedOutputStream(dataStream, 16*1024);

			//copy from input to output
			CopyStream(inputStream, outputStream);
			outputStream.flush();
			
			byte[] bitmapData = dataStream.toByteArray();

			/*
			//resize image to avoid out of memory problem for bitmapfactory decode
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length, o);
            
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale = 1;
            if(width_tmp > height_tmp && width_tmp > REQUIRED_SIZE) {
            	scale = width_tmp / REQUIRED_SIZE;
            } else if(width_tmp < height_tmp && height_tmp > REQUIRED_SIZE) {
				scale = height_tmp / REQUIRED_SIZE;
			}
            
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;*/
            //imageBitmap = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length, o2);
            imageBitmap = BitmapFactory.decodeStream(imageURL.openStream());
			viewHolder.imageView.setImageBitmap(imageBitmap);
			//viewHolder.imageView.setImageResource(R.drawable.postthumb_loading);
			
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			// TODO: handle exception
			Log.e("error", "Downloading Image Failed");
			viewHolder.imageView.setImageResource(R.drawable.postthumb_loading);
		}
		
		return convertView;
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
