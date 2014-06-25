package com.jms.viewcanvasanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class FrameAnimationView extends SurfaceView implements Callback {
	private DrawThread drawThread;
	private int framerate = 33; // 30fps
	public int currentFrame = 0;
	public boolean isLoop = false;
	public boolean isPlaying = false;
	private Bitmap[] frames;

	public FrameAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.getHolder().addCallback(this);
	}

	public FrameAnimationView(Context context) {
		super(context);
	}

	public FrameAnimationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		drawThread = new DrawThread(this.getHolder());
		this.getHolder().setFormat(PixelFormat.TRANSPARENT);
		gotoFrame(0);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		this.stopAnimation();
	}

	public void prepareAnimation(Bitmap frameList[]) {
		frames = frameList;
		currentFrame = 0;
	}

	public void setFrameRate(int ft) {
		framerate = 1000 / ft;
	}

	public int totalFrames() {
		if (frames != null) {
			return frames.length;
		} else {
			return 0;
		}
	}

	public void startAnimation() {
		if (drawThread != null && !isPlaying) {
			if (drawThread.isWaiting) {
				isPlaying = true;
				drawThread.isWaiting = false;
				synchronized (getHolder()) {
					getHolder().notify();
				}
			} else {
				isPlaying = true;
				drawThread.start();
			}
		}
	}

	public synchronized void stopAnimation() {
		if (drawThread != null) {
			drawThread.isWaiting = true;
		}

		currentFrame = -1;
		Canvas canvas = getHolder().lockCanvas();
		drawFrame(canvas);
		getHolder().unlockCanvasAndPost(canvas);
		isPlaying = false;
	}

	public void pauseAnimation() {
		if (drawThread != null) {
			drawThread.isWaiting = true;
		}

		isPlaying = false;
	}

	public void gotoFrame(int frame) {
		if (!isPlaying && frames!= null && frame < frames.length) {
			currentFrame = frame - 1;
			Canvas canvas = getHolder().lockCanvas();
			drawFrame(canvas);
			getHolder().unlockCanvasAndPost(canvas);
		}
	}

	private synchronized void drawFrame(Canvas canvas) {
		currentFrame++;
		if (currentFrame < frames.length) {
			canvas.drawColor(0, Mode.CLEAR);
			canvas.drawBitmap(frames[currentFrame], 0, 0, null);
		} else if(isLoop){
			currentFrame = 0;
			canvas.drawColor(0, Mode.CLEAR);
			canvas.drawBitmap(frames[currentFrame], 0, 0, null);
		} else {
			drawThread.isWaiting = true;
		}
	}

	public class DrawThread extends Thread {
		private SurfaceHolder surfaceHolder;
		private boolean isRunning = true;
		private boolean isWaiting = false;

		public DrawThread(SurfaceHolder holder) {
			this.surfaceHolder = holder;
		}

		public void exitThread() {
			this.isRunning = false;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			Canvas canvas = null;

			while (isRunning) {
				synchronized (surfaceHolder) {
					// during the sleep, someone puased thread
					canvas = surfaceHolder.lockCanvas();
					drawFrame(canvas);
					surfaceHolder.unlockCanvasAndPost(canvas);
				}

				try {
					sleep(framerate);
					if (isWaiting) {
						synchronized (surfaceHolder) {
							surfaceHolder.wait();
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}