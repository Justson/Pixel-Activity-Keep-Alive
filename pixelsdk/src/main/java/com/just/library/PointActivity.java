package com.just.library;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;



public class PointActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point);
        ActivityManager.getInstance().addActivity(this);
        Window mWindow = this.getWindow();
        mWindow.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
        mLayoutParams.width = 1;
        mLayoutParams.height = 1;

        mLayoutParams.x = 0;
        mLayoutParams.y = 0;

        mWindow.setAttributes(mLayoutParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }
}
