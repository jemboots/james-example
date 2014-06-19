package com.jms.flappybirdframeanimation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Main extends ActionBarActivity {

	private GameFragment gameStage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gameStage = new GameFragment();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, gameStage).commit();
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class GameFragment extends Fragment {
		
		private ImageView bird;
		private ImageView startButton;
		private AnimationDrawable flyAnimationDrawable;
		
		public GameFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			rootView.setOnClickListener(flyClickListener);
			
			bird = (ImageView) rootView.findViewById(R.id.birdimage);
			
			/*create frame animation by java code*/
			flyAnimationDrawable = new AnimationDrawable();
			flyAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.f1), 200);
			flyAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.f2), 200);
			flyAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.f3), 200);
			flyAnimationDrawable.setOneShot(false);
			bird.setBackground(flyAnimationDrawable);
			
			/*create frame animation from /res/drawable/fly.xml*/
			//bird.setBackgroundResource(R.drawable.fly);
			//flyAnimationDrawable = (AnimationDrawable) bird.getBackground();
			
			startButton = (ImageView) rootView.findViewById(R.id.startButton);
			return rootView;
		}
		
		private OnClickListener flyClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(startButton.getVisibility() == View.VISIBLE)
				{
					startButton.setVisibility(View.INVISIBLE);
				}
				
				flyAnimationDrawable.start();
			}
		};
	}

}
