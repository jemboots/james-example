/**
 * PostItemAdapter.java
 * 
 * Adapter Class which configs and returns the View for ListView
 * 
 */
package com.jms.dragtorefresh;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostItemAdapter extends ArrayAdapter<PostData> {
	private LayoutInflater inflater;
	private ArrayList<PostData> datas;
	private Activity mainActivity;

	public PostItemAdapter(Context context, int textViewResourceId,
			ArrayList<PostData> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mainActivity = (Activity) context;
		inflater = mainActivity.getLayoutInflater();
		datas = objects;
	}

	static class ViewHolder {
		TextView postTitleView;
		TextView postDateView;
		ImageView postThumbView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.postitem, null);

			viewHolder = new ViewHolder();
			viewHolder.postThumbView = (ImageView) convertView
					.findViewById(R.id.postThumb);
			viewHolder.postTitleView = (TextView) convertView
					.findViewById(R.id.postTitleLabel);
			viewHolder.postDateView = (TextView) convertView
					.findViewById(R.id.postDateLabel);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (datas.get(position).postThumbUrl == null) {
			viewHolder.postThumbView
					.setImageResource(R.drawable.postthumb_loading);
		}

		viewHolder.postTitleView.setText(datas.get(position).postTitle);
		viewHolder.postDateView.setText(datas.get(position).postDate);

		return convertView;
	}
}
