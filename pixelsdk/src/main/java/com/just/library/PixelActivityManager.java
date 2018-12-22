package com.ringleai.ringle.pixel;

import android.app.Activity;
import android.support.v4.util.ArrayMap;

import com.ringleai.ringle.common.base.BaseApplication;
import com.ringleai.ringle.common.utils.Log;
import com.ringleai.ringle.common.utils.ProcessUtil;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;


public class PixelActivityManager implements IActivityManager {

	private static final ArrayMap<Class<? extends Activity>, WeakReference<Activity>> mActivities = new ArrayMap<>(1);

	private static final String TAG = "PixelActivityManager";
	private static final AtomicReference<PixelActivityManager> atomic = new AtomicReference<>();


	public static boolean isStarting = false;

	private PixelActivityManager() {

	}

	public static PixelActivityManager getInstance() {

		for (; ; ) {
			PixelActivityManager mPixelActivityManager = atomic.get();
			if (mPixelActivityManager != null)
				return mPixelActivityManager;
			if (atomic.compareAndSet(null, new PixelActivityManager())) {
				return atomic.get();
			}

		}
	}

	private boolean findTargetAndRemove(Class<? extends Activity> activityClazz) {
		boolean tag = false;

		WeakReference<Activity> activityWeakReference = mActivities.remove(activityClazz);
		if (activityWeakReference != null && activityWeakReference.get() != null) {
			tag = true;
			activityWeakReference.get().finish();
		}

		Log.INSTANCE.e(TAG, "findTargetAndRemove remove:" + tag + "  activityClazz:" + activityClazz + "  activityWeakReference:" + activityWeakReference + "  process:" + ProcessUtil.INSTANCE.getProcessName(BaseApplication.getContext()));
		return tag;
	}

	public void addActivity(Activity activity) {
		Log.INSTANCE.e(TAG, " addActivity:" + activity.getClass());
		if (activity != null) {
			findTargetAndRemove(activity.getClass());
			mActivities.put(activity.getClass(), new WeakReference<Activity>(activity));

			Log.INSTANCE.e(TAG, "mActivities addActivity:" + mActivities.get(activity.getClass()) + "  process:" + ProcessUtil.INSTANCE.getProcessName(BaseApplication.getContext()));
		}
	}

	public void removeActivity(Activity activity) {
		findTargetAndRemove(activity.getClass());
	}


	public boolean exist(Class<? extends Activity> clazz) {
		return mActivities.get(clazz) != null;
	}

	@Override
	public void removeAcitivtyByClazz(Class<? extends Activity> clazz) {
		findTargetAndRemove(clazz);
	}
}
