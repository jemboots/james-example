package com.jms.drawimage;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class DrawCanvas extends SurfaceView implements Callback {
	private CanvasThread canvasThread;
	
	public DrawCanvas(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public DrawCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		this.getHolder().addCallback(this);
		this.canvasThread = new CanvasThread(getHolder());
		this.setFocusable(true);
	}
	
	public DrawCanvas(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub

	}

	public void startDrawImage() {
		canvasThread.setRunning(true);
		canvasThread.start();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		boolean retry = true;
		canvasThread.setRunning(false);
		while(retry) {
			try {
				canvasThread.join();
				retry = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Bitmap sweet = BitmapFactory.decodeResource(getResources(), R.drawable.sq);
		canvas.drawColor(color.black);
		canvas.drawBitmap(sweet, 0, 0, null);
	}

	private class CanvasThread extends Thread {
		private SurfaceHolder surfaceHolder;
		private boolean isRun = false;
		
		public CanvasThread(SurfaceHolder holder) {
			this.surfaceHolder = holder;
		}
		
		public void setRunning(boolean run) {
			this.isRun = run;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Canvas c;
			
			while(isRun) {
				c = null;
				try {
					c = this.surfaceHolder.lockCanvas(null);
					synchronized(this.surfaceHolder) {
						DrawCanvas.this.onDraw(c);
					}
				} finally {
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}
