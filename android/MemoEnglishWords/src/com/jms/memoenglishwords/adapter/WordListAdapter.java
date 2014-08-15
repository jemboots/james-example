package com.jms.memoenglishwords.adapter;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
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
	private Context context;
	private MediaPlayer mediaPlayer;

	public WordListAdapter(Context c, int textViewResourceId,
			ArrayList<WordData> objects) {
		super(c, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		inflater = ((Activity) c).getLayoutInflater();
		datas = objects;
		context = c;
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
			viewHolder.voiceButton = (Button) convertView
					.findViewById(R.id.voiceButton);
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
			if (mediaPlayer == null) {
				View parentRow = (View) v.getParent();
				ListView listView = (ListView) parentRow.getParent();
				final int position = listView.getPositionForView(parentRow);

				// int resID =
				// context.getResources().getIdentifier(datas.get(position).word,
				// "raw", context.getPackageName());
				// MediaPlayer player = MediaPlayer.create(context, resID);

				try {
					Uri mp3 = Uri.parse("android.resource://"
							+ context.getPackageName() + "/raw/"
							+ datas.get(position).word);
					mediaPlayer = new MediaPlayer();
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
					mediaPlayer.setDataSource(context, mp3);
					mediaPlayer.prepare(); // might take long! (for buffering, etc)
					mediaPlayer.start();
					mediaPlayer.setOnCompletionListener(onCompletionListener);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	private OnCompletionListener onCompletionListener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			mediaPlayer.release();
			mediaPlayer = null;
		}
	};
}
