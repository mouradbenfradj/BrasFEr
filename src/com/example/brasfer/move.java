package com.example.brasfer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class move extends Activity implements SensorEventListener {

	SensorManager sensorMgr;
	Bitmap barre;
	Draw bar;
	float x, y, sensorX, sensorY;

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		try{
			Thread.sleep(20);
		}catch(InterruptedException e){
			e.printStackTrace();
			
		}
		sensorX= event.values[0];
		sensorY= event.values[1];
	}	

	
	public class Draw extends SurfaceView implements Runnable {
		SurfaceHolder hol;
		Thread ourthread = null;
		boolean marche = false ;
		
	public Draw(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		hol = getHolder();
		
	}
	
	public void pause(){
			marche = false;
			while(true){
				try{
					ourthread.join();
				}catch (InterruptedException e){
					e.printStackTrace();
				}break;
			}
			ourthread = null;
		}
		public void resume(){
			marche = true;
			ourthread = new Thread(this);
			ourthread.start();
		}
		public void run(){
			while (marche){
				if(!hol.getSurface().isValid())continue;
				Canvas canvas = hol.lockCanvas();
				canvas.drawColor(Color.WHITE);
				canvas.rotate(sensorX+180, (canvas.getWidth()/2), 0);
				canvas.drawBitmap(barre, (canvas.getWidth()/2), 0, null);
				hol.unlockCanvasAndPost(canvas);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if(sensorMgr.getSensorList(Sensor.TYPE_ORIENTATION).size() != 0){
			Sensor s = sensorMgr.getSensorList(Sensor.TYPE_ORIENTATION).get(0);
			sensorMgr.registerListener(this , s, SensorManager.SENSOR_DELAY_NORMAL);
		}
	barre =BitmapFactory.decodeResource(getResources(), R.drawable.barre);
	x= y=sensorX= sensorY=0;
	bar = new Draw(this);
	bar.resume();
	setContentView(bar);
	}
	
}