package cn.eoe.superflashlight;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class ColorPickerDialog extends Dialog{
	private final int COLOR_DIALOG_WIDTH = 300;
	private final int COLOR_DIALOG_HIGHT = 300;
	private final int CENTER_X = COLOR_DIALOG_WIDTH/2;
	private final int CENTER_Y = COLOR_DIALOG_HIGHT/2;
	private final int CENTER_RADIUS = 32;
	
	
	public interface OnColorChangedListener{
		void ColorChanged(int color);
	}
	private OnColorChangedListener mListener;
	private int mInitiaColor;
	
	
	public ColorPickerDialog(Context context,OnColorChangedListener listener, int initialColor) {
		super(context);
		mListener = listener;
		mInitiaColor = initialColor;
	}
	
	private class ColorPickerView extends View{
		private Paint mPaint;
		private Paint mCentPaint;
		private final int[] mColors; //颜色带的起始颜色 
		private OnColorChangedListener mListener;
		private boolean mTrackingCenter;
		private boolean mHightlightCenter;
		private static final float PI = 3.1415926F;
		
		public ColorPickerView(Context c, OnColorChangedListener listener,int color) {
			super(c);
			mListener = listener;
			mColors = new int[]{0xFFFF0000,0xFFFF00FF,0xFF0000FF,0xFF00FFFF,0xFF00FF00,0xFFFFFF00,0xFFFF0000};
			Shader s = new SweepGradient(0, 0, mColors, null);
			mPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setShader(s);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(32);
			
			mCentPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
			mCentPaint.setColor(color);
			mCentPaint.setStrokeWidth(5);
		}
		@Override
		protected void onDraw(Canvas canvas) {
				float r = CENTER_X-mPaint.getStrokeWidth()*0.5f-20;
				
				canvas.translate(CENTER_X, CENTER_Y);
				
				canvas.drawCircle(0, 0, r, mPaint);
				canvas.drawCircle(0, 0, CENTER_RADIUS, mCentPaint);
				if(mTrackingCenter){
					int c = mCentPaint.getColor();
					mCentPaint.setStyle(Paint.Style.STROKE);
					if(mHightlightCenter){
						mCentPaint.setAlpha(0xFF);
					}else{
						mCentPaint.setAlpha(0x00);
					}
					canvas.drawCircle(0, 0, CENTER_RADIUS + mCentPaint.getStrokeWidth(), mCentPaint);
					mCentPaint.setStyle(Paint.Style.FILL);
					mCentPaint.setColor(c);
				}
		}
		private int ave(int s,int d,float p){
			return s +Math.round(p*(d-s));
		}
		
		
		private int interpColor(int Colors[],float unit){
			if(unit<=0){
				return Colors[0];
			}	
			if(unit>=1){
				return Colors[Colors.length-1];
			}
			float p = unit*(Colors.length-1);	
			int i = (int) p;
			p-=i;
			
			int c0 = Colors[i];
			int c1 = Colors[i+1];
			int a  = ave(Color.alpha(c0), Color.alpha(c1), p);
			int r  = ave(Color.red(c0), Color.red(c1), p);
			int g  = ave(Color.green(c0), Color.green(c1), p);
			int b  = ave(Color.blue(c0), Color.blue(c1), p);
			return Color.argb(a, r, g, b);
			
		}
		@Override
		public boolean onTouchEvent(MotionEvent event){
			float x = event.getX()-CENTER_X;
			float y = event.getY()-CENTER_Y;
			boolean inCenter = Math.sqrt(x*x+y*y)<=CENTER_RADIUS;
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mTrackingCenter = inCenter;
				if(inCenter){
					mHightlightCenter = true;
					invalidate();
					break;
				}
			case MotionEvent.ACTION_MOVE:
				float angle = (float)Math.atan2(y, x);
				float unit = angle/(2*PI);
				if(unit<0){
					unit+=1;
				}
				mCentPaint.setColor(interpColor(mColors, unit));
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				if(mTrackingCenter){
					if(inCenter){
						mListener.ColorChanged(mCentPaint.getColor());
					}
					mTrackingCenter = false;
					invalidate();	 
				}
				break;
			default:
				break;
			}
			return true;
			
		}
		
	}
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		OnColorChangedListener listener = new  OnColorChangedListener() {
			
			@Override
			public void ColorChanged(int color) {
				// TODO Auto-generated method stub
				mListener.ColorChanged(color);
				dismiss();
			}
		};
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new ColorPickerView(getContext(), listener, mInitiaColor));
		ColorDrawable colorDrawable  = new ColorDrawable();
		colorDrawable.setColor(Color.BLACK);
		getWindow().setBackgroundDrawable(colorDrawable);
		getWindow().setAttributes(new LayoutParams(COLOR_DIALOG_WIDTH,COLOR_DIALOG_HIGHT,0,0,0));
		
		
	}
}
