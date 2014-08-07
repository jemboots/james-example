package com.jms.memoenglishwords;

import java.io.InputStream;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private PlaceholderFragment pFragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		pFragment = new PlaceholderFragment();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, pFragment).commit();
		}
		
		
		//pFragment.showWordPuzzle();

	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		pFragment.showWordPuzzle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		private JSONObject wordList = null;
		private JSONArray englishWordList = null;
		
		private TextView explanationView = null;
		private EditText answerField = null;
		
		private String currentWord = null;
		
		public PlaceholderFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			explanationView = (TextView) rootView.findViewById(R.id.explanation);
			answerField = (EditText) rootView.findViewById(R.id.answerBox);
			answerField.setInputType(answerField.getInputType()
			    | EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS
			    | EditorInfo.TYPE_TEXT_VARIATION_FILTER);
			answerField.addTextChangedListener(textWatcher);
			answerField.setOnKeyListener(onKeyListener);
			
			try {
				InputStream wordListSteam = this.getResources().openRawResource(R.raw.words);
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
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
				    showWordPuzzle();
				}
				return false;
			}
		};
		
		private TextWatcher textWatcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				Log.v("before text changle", s + " start: " + String.valueOf(start) + " count: " + String.valueOf(count) + " after: " + String.valueOf(after));
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if(answerField.length() == 0) {
					String firstCharacter = currentWord.substring(0, 1);
					answerField.setText(firstCharacter);
					answerField.setSelection(1);
				} else if(answerField.length() >  1) {
					String pattern = '^' + s.toString();
					if(s.length() < currentWord.length()) {
						pattern += "[a-z]{"+(currentWord.length() - s.length())+"}";
					}
					
					if(currentWord.matches(pattern)) {
						if(s.length() == currentWord.length()) {
							answerField.setTextColor(Color.parseColor("#007F00"));
						} else {
							answerField.setTextColor(Color.parseColor("#000000"));
						}
					} else {
						if(s.length() >= currentWord.length()) {
							answerField.setTextColor(Color.parseColor("#ff0000"));
						}
					}
				}
			}
		};
	}

}
