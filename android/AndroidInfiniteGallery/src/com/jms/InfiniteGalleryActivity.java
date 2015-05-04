/*
 * Copyright (C) 2007 The Infinite Gallery Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jms;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class InfiniteGalleryActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Gallery gallery = (Gallery) findViewById(R.id.mygallery);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setSpacing(10);
		gallery.setSelection((Integer.MAX_VALUE/2) - (Integer.MAX_VALUE/2) % 12 );

	    gallery.setOnItemLongClickListener(longClickListener);
	}
	
	private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
		public boolean onItemLongClick(AdapterView<?> parent, View v, int position,
				long id) {
			String temp = "Hello World!";
			Toast toast = Toast.makeText(getBaseContext(), temp, Toast.LENGTH_SHORT);
			toast.show();
			return true;
		}
	};

	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater inflater = null;

		private final Integer[] imageDataList = { R.drawable.a, R.drawable.b,
				R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
				R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
				R.drawable.k, R.drawable.l };

		public ImageAdapter(Context c) {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			if (imageDataList != null) {
				return Integer.MAX_VALUE;
			} else {
				return 0;
			}
		}

		public Object getItem(int position) {
			if(position >= imageDataList.length) {
				position = position % imageDataList.length;
			}
			
			return position;
		}

		public long getItemId(int position) {
			if(position >= imageDataList.length) {
				position = position % imageDataList.length;
			}
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;

			if (view == null) {
				view = inflater.inflate(R.layout.itemrender, parent, false);
			}

			if(position >= imageDataList.length) {
				position = position % imageDataList.length;
			}
			
			((ImageView) view).setImageResource(imageDataList[position]);
			return view;
		}
		
		public int checkPosition(int position) {
			if(position >= imageDataList.length) {
				position = position % imageDataList.length;
			}
			
			return position;
		}
	}
}