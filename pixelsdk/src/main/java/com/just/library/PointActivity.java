package com.ringleai.ringle.pixel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.ringleai.ringle.common.utils.Log;


public class PointActivity extends Activity {
	boolean isFirst = true;
	private static final String TAG = "PointActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(android.R.layout.simple_list_item_1);
		Log.INSTANCE.e("PointActivity", "  onCreate");
		PixelActivityManager.getInstance().addActivity(this);
		Window mWindow = this.getWindow();
		mWindow.setGravity(Gravity.START | Gravity.TOP);
		WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
		mLayoutParams.width = 1;
		mLayoutParams.height = 1;

		mLayoutParams.x = 0;
		mLayoutParams.y = 0;

		mWindow.setAttributes(mLayoutParams);
		isFirst = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.INSTANCE.e(TAG, " isScreenLock:" + ScreenUtils.isScreenLock());
		//windowAnimationStyle
		if (!isFirst) {
			finish();
		}
		isFirst = false;
		PixelActivityManager.isStarting = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PixelActivityManager.getInstance().removeActivity(this);
	}
}
