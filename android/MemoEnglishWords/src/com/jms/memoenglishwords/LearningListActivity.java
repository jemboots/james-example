package com.jms.memoenglishwords;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jms.memoenglishwords.adapter.WordListAdapter;
import com.jms.memoenglishwords.vo.WordData;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LearningListActivity extends ActionBarActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		WordListFragment pFragment = new WordListFragment(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, pFragment).commit();
		}
		
		

	}
	
	public static class WordListFragment extends Fragment {
		private Context context;
		public ArrayList<WordData> englishWordList = null;
		public ListView wordListView = null;
		public WordListAdapter wordListAdapter = null;
		
		public WordListFragment(Context c) {
			context = c;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.wordlist, container,
					false);
			
			try {
				InputStream wordListSteam = this.getResources().openRawResource(R.raw.words);
				String wordListStr = Utils.readStringFromStream(wordListSteam);
				JSONObject wordList = new JSONObject(wordListStr);
				JSONArray keyArray = wordList.names();
				englishWordList = new ArrayList<WordData>();
				
				for (int i=0; i < keyArray.length(); i++) {
					WordData wd = new WordData();
					wd.word = keyArray.getString(i);
					wd.translation = wordList.getString(wd.word);
					englishWordList.add(wd);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			wordListView = (ListView) rootView.findViewById(R.id.wordlist);
			wordListAdapter = new WordListAdapter(context, R.layout.worditem, englishWordList);
			wordListView.setAdapter(wordListAdapter);
			
			return rootView;
		}
	}
}

