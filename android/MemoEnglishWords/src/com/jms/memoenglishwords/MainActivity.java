package com.jms.memoenglishwords;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	private PlaceholderFragment pFragment = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
        	View decorView = getWindow().getDecorView();
        	// Hide the status bar.
        	int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        	decorView.setSystemUiVisibility(uiOptions);
        }
		
		ActionBar actionBar = getSupportActionBar(); //or getSupportActionBar();
		actionBar.hide();
		
		setContentView(R.layout.main);
		
		pFragment = new PlaceholderFragment(this);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, pFragment).commit();
		}
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

	
	public static class PlaceholderFragment extends Fragment {
		private Context context;
		
		public PlaceholderFragment(Context c) {
			context = c;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.launchview, container,
					false);
			
			Button learningButton = (Button) rootView.findViewById(R.id.learingButton);
			learningButton.setOnClickListener(onLearningButtonClickListener);
			
			Button memorizeButton = (Button) rootView.findViewById(R.id.memoButton);
			memorizeButton.setOnClickListener(onMemoButtonClickListener);
			
			return rootView;
		}
		
		private OnClickListener onLearningButtonClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent postviewIntent = new Intent(context, LearningListActivity.class);
				startActivity(postviewIntent);
			}
		};
		
		private OnClickListener onMemoButtonClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent postviewIntent = new Intent(context, MemorizeActivity.class);
				startActivity(postviewIntent);
			}
		};
	}
}
