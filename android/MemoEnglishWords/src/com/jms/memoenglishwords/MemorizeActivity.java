package com.jms.memoenglishwords;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MemorizeActivity extends ActionBarActivity {
	private MemorizeFragment mFragment = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mFragment = new MemorizeFragment(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, mFragment).commit();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		mFragment.showWordPuzzle();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class MemorizeFragment extends Fragment {
		private MediaPlayer mediaPlayer;
		private Context context;
		private JSONObject wordList = null;
		private JSONArray englishWordList = null;

		private TextView explanationView = null;
		private EditText answerField = null;

		private String currentWord = null;

		public MemorizeFragment(Context c) {
			context = c;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			explanationView = (TextView) rootView
					.findViewById(R.id.translationTextView);
			answerField = (EditText) rootView.findViewById(R.id.answerBox);
			answerField.setInputType(answerField.getInputType()
					| EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS
					| EditorInfo.TYPE_TEXT_VARIATION_FILTER);
			answerField.addTextChangedListener(textWatcher);
			answerField.setOnKeyListener(onKeyListener);

			Button showAnswerButton = (Button) rootView
					.findViewById(R.id.showAnswerButton);
			showAnswerButton.setOnClickListener(showAnswerClickListener);

			Button nextWordButton = (Button) rootView
					.findViewById(R.id.nextWordButton);
			nextWordButton.setOnClickListener(showNextWordClickListener);

			Button voiceButton = (Button) rootView
					.findViewById(R.id.voiceButton);
			voiceButton.setOnClickListener(voiceButtonClickListener);

			try {
				InputStream wordListSteam = this.getResources()
						.openRawResource(R.raw.words);
				String wordListStr = Utils.readStringFromStream(wordListSteam);
				wordList = new JSONObject(wordListStr);
				englishWordList = wordList.names();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return rootView;
		}

		public void showWordPuzzle() {
			Random randomGen = new Random();
			int randomNumber = randomGen.nextInt(englishWordList.length());

			try {
				currentWord = englishWordList.getString(randomNumber);
				String explain = wordList.getString(currentWord);
				explanationView.setText(explain);

				answerField.setTextColor(Color.parseColor("#000000"));
				String firstCharacter = currentWord.substring(0, 1);
				answerField.setText(firstCharacter);
				answerField.setSelection(1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private OnKeyListener onKeyListener = new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					showWordPuzzle();
				}
				return false;
			}
		};

		private TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				Log.v("before text changle",
						s + " start: " + String.valueOf(start) + " count: "
								+ String.valueOf(count) + " after: "
								+ String.valueOf(after));
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				answerField.setTextColor(Color.parseColor("#000000"));

				if (answerField.length() == 0) {
					String firstCharacter = currentWord.substring(0, 1);
					answerField.setText(firstCharacter);
					answerField.setSelection(1);
				} else if (answerField.length() > 1) {
					String pattern = '^' + s.toString();
					if (s.length() < currentWord.length()) {
						pattern += "[a-z]{"
								+ (currentWord.length() - s.length()) + "}";
					}

					if (currentWord.matches(pattern)) {
						if (s.length() == currentWord.length()) {
							answerField.setTextColor(Color
									.parseColor("#007F00"));
						}
					} else {
						if (s.length() >= currentWord.length()) {
							answerField.setTextColor(Color
									.parseColor("#ff0000"));
						}
					}
				}
			}
		};

		private OnClickListener showAnswerClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				answerField.setText(currentWord);
				answerField.setTextColor(Color.parseColor("#ff0000"));
				answerField.setEnabled(false);
			}
		};

		private OnClickListener showNextWordClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				answerField.setEnabled(true);
				showWordPuzzle();
			}
		};

		private OnClickListener voiceButtonClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Uri mp3 = Uri.parse("android.resource://"
							+ context.getPackageName() + "/raw/" + currentWord);
					mediaPlayer = new MediaPlayer();
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
					mediaPlayer.setDataSource(context, mp3);
					mediaPlayer.prepare(); // might take long! (for buffering, // etc)
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
}
