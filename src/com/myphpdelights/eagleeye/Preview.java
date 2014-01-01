package com.myphpdelights.eagleeye;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.util.*;
import android.view.*;

@SuppressLint("ViewConstructor")
public class Preview extends SurfaceView implements SurfaceHolder.Callback, Camera.OnZoomChangeListener  {
	private static final String TAG = "Preview Class";

	
	
	private SurfaceHolder mHolder;
    private Camera mCamera;
    

    @SuppressWarnings("deprecation")
	public Preview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        
       
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
            
            Log.v(TAG, "stopPreview");
            
            googleGlassXE10WorkAround(mCamera);
            
   
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
            Log.d(TAG, "Tried to stop a non-existent preview: " + e.getMessage());

        }

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            Log.v(TAG, "startPreview");


        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
    

    
  //https://code.google.com/p/google-glass-api/issues/detail?id=232
    public void googleGlassXE10WorkAround(Camera mCamera) {
    	Camera.Parameters params = mCamera.getParameters();
        params.setPreviewFpsRange(30000, 30000);
        params.setPreviewSize(640,360);
        mCamera.setParameters(params);
        
        mCamera.setZoomChangeListener(this);
  
    }

	@Override
	public void onZoomChange(int zoomValue, boolean stopped, Camera camera) {
		// TODO Auto-generated method stub

	}

}
