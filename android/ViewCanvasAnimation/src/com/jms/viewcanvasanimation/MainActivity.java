package com.jms.viewcanvasanimation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.viewcanvasanimation.R;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new GameFragment()).commit();
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
		private FrameAnimationView bird;
		private TextView frameRateLabel;
		private int frameRate = 30;
		
		public GameFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			bird = (FrameAnimationView) rootView.findViewById(R.id.birdimage);
			bird.setZOrderOnTop(true);
			Bitmap frames[] = new Bitmap[25];
			frames[0] = BitmapFactory.decodeResource(getResources(), R.drawable.f0);
			frames[1] = BitmapFactory.decodeResource(getResources(), R.drawable.f1);
			frames[2] = BitmapFactory.decodeResource(getResources(), R.drawable.f2);
			frames[3] = BitmapFactory.decodeResource(getResources(), R.drawable.f3);
			frames[4] = BitmapFactory.decodeResource(getResources(), R.drawable.f4);
			frames[5] = BitmapFactory.decodeResource(getResources(), R.drawable.f5);
			frames[6] = BitmapFactory.decodeResource(getResources(), R.drawable.f6);
			frames[7] = BitmapFactory.decodeResource(getResources(), R.drawable.f7);
			frames[8] = BitmapFactory.decodeResource(getResources(), R.drawable.f8);
			frames[9] = BitmapFactory.decodeResource(getResources(), R.drawable.f9);
			frames[10] = BitmapFactory.decodeResource(getResources(), R.drawable.f10);
			frames[11] = BitmapFactory.decodeResource(getResources(), R.drawable.f11);
			frames[12] = BitmapFactory.decodeResource(getResources(), R.drawable.f12);
			frames[13] = BitmapFactory.decodeResource(getResources(), R.drawable.f13);
			frames[14] = BitmapFactory.decodeResource(getResources(), R.drawable.f14);
			frames[15] = BitmapFactory.decodeResource(getResources(), R.drawable.f15);
			frames[16] = BitmapFactory.decodeResource(getResources(), R.drawable.f16);
			frames[17] = BitmapFactory.decodeResource(getResources(), R.drawable.f17);
			frames[18] = BitmapFactory.decodeResource(getResources(), R.drawable.f18);
			frames[19] = BitmapFactory.decodeResource(getResources(), R.drawable.f19);
			frames[20] = BitmapFactory.decodeResource(getResources(), R.drawable.f20);
			frames[21] = BitmapFactory.decodeResource(getResources(), R.drawable.f21);
			frames[22] = BitmapFactory.decodeResource(getResources(), R.drawable.f22);
			frames[23] = BitmapFactory.decodeResource(getResources(), R.drawable.f23);
			frames[24] = BitmapFactory.decodeResource(getResources(), R.drawable.f24);
			bird.prepareAnimation(frames);
			bird.setFrameRate(frameRate);
			bird.isLoop = true;
			
			Button btn1 = (Button) rootView.findViewById(R.id.button1);
			btn1.setOnClickListener(onClickStart);
			
			Button btn2 = (Button) rootView.findViewById(R.id.button2);
			btn2.setOnClickListener(onClickStop);
			
			Button btn3 = (Button) rootView.findViewById(R.id.button3);
			btn3.setOnClickListener(onClickPause);
			
			SeekBar seekbar = (SeekBar) rootView.findViewById(R.id.seekBar1);
			seekbar.setProgress(frameRate - 1);
			seekbar.setOnSeekBarChangeListener(onSeekBarChange);
			
			frameRateLabel = (TextView) rootView.findViewById(R.id.textView1);
			frameRateLabel.setText("Frame Rate: " + String.valueOf(frameRate));
			
			return rootView;
		}
		
		private OnClickListener onClickStart = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bird.startAnimation();
			}
		};
		
		private OnClickListener onClickStop = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bird.stopAnimation();
			}
		};
		
		private OnClickListener onClickPause = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bird.pauseAnimation();
			}
		};
		
		private OnSeekBarChangeListener onSeekBarChange = new OnSeekBarChangeListener() {
			private final int startFrom = 1;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				int ft = startFrom + progress;
				frameRateLabel.setText("Frame Rate: " + String.valueOf(ft));
				bird.setFrameRate(ft);
			}
		};
	}

}
