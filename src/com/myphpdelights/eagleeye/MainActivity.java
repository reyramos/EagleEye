package com.myphpdelights.eagleeye;

import android.os.Bundle;
import android.app.Activity;
import android.hardware.Camera;
import android.util.*;
import android.view.*;
import android.widget.FrameLayout;

public class MainActivity extends Activity implements
		GestureDetector.OnGestureListener{

	private static final String TAG = "MainActivity Class";

	private Camera mCamera;
	private Preview mPreview;

	private GestureDetector mGestureDetector;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Create an instance of Camera
		mCamera = getCameraInstance();

		setPreview(mCamera);

		

	}

	@Override
	public void onResume() {
		super.onResume();
		setPreview(mCamera);

	}

	@Override
	public void onPause() {

		mCamera.release();
		mCamera = null;
		super.onPause();
	}

	private void setPreview(Camera mCamera) {
		// Create our Preview view and set it as the content of our activity.
		mPreview = new Preview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
				
		mGestureDetector = new GestureDetector(this, this);

	}

	public static Camera getCameraInstance() {
		Camera c = null;

		try {
			c = Camera.open();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return c;
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		
        Log.d(TAG, "onGenericMotionEvent" + event);
		
		mGestureDetector.onTouchEvent(event);
		return true;
	}



	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Camera.Parameters parameters = mCamera.getParameters();
		int zoom = parameters.getZoom();
		
		if ( velocityX < 0.0f )
		{
			zoom -= 10;
			if ( zoom < 0 )
				zoom = 0;
		}
		else if ( velocityX > 0.0f )
		{
			zoom += 10;
			if ( zoom > parameters.getMaxZoom() )
				zoom = parameters.getMaxZoom();
		}

		mCamera.startSmoothZoom(zoom);
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
