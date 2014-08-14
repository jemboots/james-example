package com.jms.memoenglishwords.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jms.memoenglishwords.R;
import com.jms.memoenglishwords.vo.WordData;

public class WordListAdapter extends ArrayAdapter<WordData> {
	private LayoutInflater inflater;
	private ArrayList<WordData> datas;
	
	public WordListAdapter(Context context, int textViewResourceId,
			ArrayList<WordData> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		inflater = ((Activity) context).getLayoutInflater();
		datas = objects;
	}
	
	static class ViewHolder {
		TextView wordTextView;
		TextView translationTextView;
		Button voiceButton;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.worditem, null);

			viewHolder = new ViewHolder();
			viewHolder.wordTextView = (TextView) convertView
					.findViewById(R.id.wordTextView);
			viewHolder.translationTextView = (TextView) convertView
					.findViewById(R.id.translationTextView);
			viewHolder.voiceButton = (Button) convertView.findViewById(R.id.voiceButton);
			convertView.setTag(viewHolder);
			viewHolder.voiceButton.setOnClickListener(voiceButtonClickListener);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.wordTextView.setText(datas.get(position).word);
		viewHolder.translationTextView.setText(datas.get(position).translation);

		return convertView;
	}
	
	private OnClickListener voiceButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			View parentRow = (View) v.getParent();
			ListView listView = (ListView) parentRow.getParent();
			final int position = listView.getPositionForView(parentRow);
			Log.v("fuck", datas.get(position).word);
		}
	};
}
