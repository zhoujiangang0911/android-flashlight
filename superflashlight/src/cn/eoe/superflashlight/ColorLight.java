package cn.eoe.superflashlight;

import cn.eoe.superflashlight.ColorPickerDialog.OnColorChangedListener;
import cn.eoe.superflashlight.widget.HideTextView;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class ColorLight extends Bulb implements OnColorChangedListener{
	protected int mCurrentColorLight = Color.RED;
	
	protected HideTextView mHideTextViewColorLight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mHideTextViewColorLight = (HideTextView) findViewById(R.id.textview_color_light);
		
	}
	
	public void onClick_ShowColorPicker(View view){
			new ColorPickerDialog(this, this , Color.RED).show();
	}

	@Override
	public void ColorChanged(int color) {
		// TODO Auto-generated method stub
		mUIColorLight.setBackgroundColor(color);
		mCurrentColorLight = color;
	}
}
