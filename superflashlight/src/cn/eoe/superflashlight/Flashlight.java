package cn.eoe.superflashlight;

import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class Flashlight extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageViewFlashlight.setTag(false);
		Point point = new Point();
 
		getWindowManager().getDefaultDisplay().getSize(point);

		LayoutParams laParams = (LayoutParams) mImageViewFlashlightController
				.getLayoutParams();
		laParams.height = point.y * 3 / 4;
		laParams.width = point.x / 3;

		mImageViewFlashlightController.setLayoutParams(laParams);

	}

	public void onClick_Flashlight(View view) {
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_FLASH)) {
			Toast.makeText(this, "��ǰ�豸û�������", Toast.LENGTH_LONG).show();
			return;
		}
		if (((Boolean) mImageViewFlashlight.getTag()) == false) {
			openFlashlight();
   
		} else {
			closeFlashlight();
		}
	}

	// �������
	protected void openFlashlight() {
		TransitionDrawable drawable = (TransitionDrawable) mImageViewFlashlight
				.getDrawable();
		drawable.startTransition(200);
		mImageViewFlashlight.setTag(true);

		try {
			mCamera = Camera.open();
			int textureId = 0;
			mCamera.setPreviewTexture(new SurfaceTexture(textureId));
			mCamera.startPreview();

			mParameters = mCamera.getParameters();

			mParameters.setFlashMode(mParameters.FLASH_MODE_TORCH);
			mCamera.setParameters(mParameters);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// �ر������
	protected void closeFlashlight() {
		TransitionDrawable drawable = (TransitionDrawable) mImageViewFlashlight
				.getDrawable();
		if (((Boolean) mImageViewFlashlight.getTag())) {
			drawable.reverseTransition(200);
			mImageViewFlashlight.setTag(false);
			if (mCamera != null) {
				mParameters = mCamera.getParameters();
				mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(mParameters);
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;

			}
		}
	}
	@Override
	public void onPause()
	{
		super.onPause();
		closeFlashlight();
	}
}
