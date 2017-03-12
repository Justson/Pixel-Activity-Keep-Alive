package com.just.library;

import android.app.Activity;



public interface IActivityManager {

    <T extends Activity> void addActivity(T t);

    <T extends Activity> void removeActivity(T t);

    void removeAcitivtyByClazz(Class<? extends Activity> clazz);

}
