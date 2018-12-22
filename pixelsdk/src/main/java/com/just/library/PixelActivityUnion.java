package com.ringleai.ringle.pixel;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.ringleai.ringle.pixel.PixelActivityManager.isStarting;


public class PixelActivityUnion {


	private static final PixelActivityUnion mPixelActivityUnion = new PixelActivityUnion();
	private Class<? extends Activity> activity = null;
	private Bundle mBundle = null;
	private PixelActivityManager mActivityManager;

	private ScreenStateBroadcast mScreenStateBroadcast = null;
	private Context mContext;
	private static final String TAG = "PixelActivityUnion";
	private static volatile int index = 0;

	private PixelActivityUnion() {
		if (mPixelActivityUnion != null) {
			throw new UnsupportedOperationException("single instance has been created.");
		}
	}

	public static PixelActivityUnion with(Context context) {
		mPixelActivityUnion.mContext = context.getApplicationContext();
		return mPixelActivityUnion;
	}

	public PixelActivityUnion targetActivityClazz(Class<? extends Activity> activity) {
		mPixelActivityUnion.activity = activity;
		return mPixelActivityUnion;
	}

	public PixelActivityUnion args(@Nullable Bundle bundle) {
		mPixelActivityUnion.mBundle = bundle;
		return mPixelActivityUnion;
	}


	public PixelActivityUnion setActiviyManager(PixelActivityManager manager) {
		this.mActivityManager = manager;
		return mPixelActivityUnion;
	}

	public void start() {
		mPixelActivityUnion.doRegister();
	}

	public static void quit() {
		mPixelActivityUnion.exit();
	}

	public void exit() {
		if (mScreenStateBroadcast != null) {
			mContext.unregisterReceiver(mScreenStateBroadcast);
		}
		if (mActivityManager != null && activity != null) {
			mActivityManager.removeAcitivtyByClazz(activity);
		}
	}

	private void doRegister() {

		if (mContext == null)
			throw new NullPointerException("context is null");
		if (activity == null)
			throw new NullPointerException("target activity must nonnull");
		Log.i(TAG, "已经注册广播");
		mScreenStateBroadcast = new ScreenStateBroadcast();
		IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_USER_PRESENT);
		mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		mContext.registerReceiver(mScreenStateBroadcast, mIntentFilter);
	}


	class ScreenStateBroadcast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			Log.i(TAG, "action:" + intent.getAction());
			/*开锁*/
			if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
				onScreenOn();
			} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
				onScrenntOff();
			}
		}
	}

	private void onScreenOn() {
		mActivityManager.removeAcitivtyByClazz(activity);
	}

	private void onScrenntOff() {
		boolean exist = mActivityManager.exist(activity);
		if (exist) {
			com.ringleai.ringle.common.utils.Log.INSTANCE.e(TAG, " onScrenntOff exist:" + exist);
			return;
		}
		if (isStarting) {
			com.ringleai.ringle.common.utils.Log.INSTANCE.e(TAG, " onScrenntOff isStarting:" + isStarting);
			return;
		}
		isStarting = true;
		Intent mIntent = new Intent(mContext, activity);
		if (mBundle != null)
			mIntent.putExtras(mBundle);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(mIntent);
	}

}
