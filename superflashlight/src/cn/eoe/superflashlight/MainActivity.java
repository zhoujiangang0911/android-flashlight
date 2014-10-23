package cn.eoe.superflashlight;

import android.graphics.Color;
import android.view.View;

public class MainActivity extends Settings {

	public void onClick_ToFlashlight(View view) {
		hideAllUI();
		mUIFlashlight.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_FLASHLIGHT;
		mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
	}

	public void onClick_ToWarningLight(View view) {
		hideAllUI();
		mUIWarningLight.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_WARNINGLIGHT;
		mCurrentUIType = mLastUIType;
		screenBrightness(1f);
		new WarningLightThread().start();
	}

	public void onClick_ToMorse(View view) {
		hideAllUI();
		mUIMorse.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_MORSE;
		mCurrentUIType = mLastUIType;
	}

	public void onClick_ToBulb(View view) {
		hideAllUI();
		mUIBulb.setVisibility(View.VISIBLE);
		mHideTextViewBulb.hide();
		mHideTextViewBulb.setTextColor(Color.BLACK);
		mLastUIType = UIType.UI_TYPE_BLUB;
		mCurrentUIType = mLastUIType;

	}

	public void onClick_ToColor(View view) {
		hideAllUI();
		mUIColorLight.setVisibility(View.VISIBLE);
		screenBrightness(1f);
		mLastUIType = UIType.UI_TYPE_COLOR;
		mCurrentUIType = mLastUIType;
		mHideTextViewColorLight.setTextColor(Color.rgb(
				255 - Color.red(mCurrentColorLight),
				255 - Color.green(mCurrentColorLight),
				255 - Color.blue(mCurrentColorLight)));

	}

	public void onClick_ToPolice(View view) {
		hideAllUI();
		mUIPolicelight.setVisibility(View.VISIBLE);
		screenBrightness(1f);
		mLastUIType = UIType.UI_TYPE_POLICE;
		mCurrentUIType = mLastUIType;
		mHideTextViewPolicelight.hide();
		new PolicetThread().start();
	}

	public void onClick_ToSettings(View view) {
		hideAllUI();
		mUISettings.setVisibility(View.VISIBLE);
		mLastUIType = UIType.UI_TYPE_SETTINGS;
		mCurrentUIType = mLastUIType;

	}

	public void onClick_Controller(View view) {
		hideAllUI();
		if (mCurrentUIType != UIType.UI_TYPE_MAIN) {
			mUIMain.setVisibility(View.VISIBLE);
			mCurrentUIType = UIType.UI_TYPE_MAIN;
			mWarningLightFlicker = false;
			screenBrightness(mDefaultScreenBrightness / 255f);
			if (mBulbCrossFadeFlag) {
				mDrawable.reverseTransition(0);
				mBulbCrossFadeFlag = false;
				mPoliceState = false;
				mSharedPreferences
						.edit()
						.putInt("warning_light_interval",
								mCurrentWarningLightInterval)
						.putInt("police_light_interval",
								mCurrentPoliceLightInterval).commit();
			}

		} else {
			switch (mLastUIType) {
			case UI_TYPE_FLASHLIGHT:
				mUIFlashlight.setVisibility(View.VISIBLE);
				screenBrightness(1f);
				mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
				break;
			case UI_TYPE_WARNINGLIGHT:
				mUIWarningLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
				new WarningLightThread().start();
				break;
			case UI_TYPE_MORSE:
				mUIMorse.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_MORSE;
				break;
			case UI_TYPE_BLUB:
				mUIBulb.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_BLUB;
				break;
			case UI_TYPE_COLOR:
				mUIColorLight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_COLOR;
				break;
			case UI_TYPE_POLICE:
				mUIPolicelight.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_POLICE;
				new PolicetThread().start();
				break;
			case UI_TYPE_SETTINGS:
				mUISettings.setVisibility(View.VISIBLE);
				mCurrentUIType = UIType.UI_TYPE_SETTINGS;
						
				
				break;
			default:
				break;
			}
		}
	}
}
