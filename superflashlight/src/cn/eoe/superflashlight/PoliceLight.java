package cn.eoe.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.eoe.superflashlight.widget.HideTextView;

public class PoliceLight extends ColorLight {
	protected boolean mPoliceState;
	protected HideTextView mHideTextViewPolicelight;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mHideTextViewPolicelight = (HideTextView) findViewById(R.id.textview_police_light);
		
		
		
	}
	
	class PolicetThread extends Thread{
		public void run() {
			mPoliceState = true;
			while(mPoliceState){
				mHandler.sendEmptyMessage(Color.BLUE);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.BLACK);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.RED);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.BLACK);
				sleepExt(100);
			}
			
		}
		
		
	}
	
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message message) {
			int color = message.what;
			mUIPolicelight.setBackgroundColor(color);
			
			
		}
	};
	
	
	
	
}
