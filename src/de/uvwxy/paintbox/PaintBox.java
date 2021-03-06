package de.uvwxy.paintbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * A class managing the creation of a canvas to draw on. This is abstract. Create a class which overrides onDraw. The
 * result is a clean class where the drawing-only code will reside.
 * 
 * @author Paul
 * 
 */
public abstract class PaintBox extends SurfaceView implements SurfaceHolder.Callback {

	PaintThread pThread;
	protected boolean oldMode = true;


	public PaintBox(Context context) {
		super(context);
		getHolder().addCallback(this);
	}

	public PaintBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
	}

	public PaintBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		pThread = new PaintThread(getHolder(), this);
		pThread.setRunning(true);
		pThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		boolean retry = true;
		pThread.setRunning(false);
		while (retry) {
			try {
				pThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	public void setTransparentTop(){
		setZOrderOnTop(true);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}
	

	public void setNewMode(){
		oldMode = false;
	}
	
	@Override
	protected abstract void onDraw(Canvas canvas);
}
