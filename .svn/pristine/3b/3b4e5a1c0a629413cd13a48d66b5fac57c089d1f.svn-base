package com.ghg.tobacco;

import android.app.Activity;
import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Created by wzzhu on 2016/5/4.
 */
public class MyApplication extends Application {
    private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        SDKInitializer.initialize(this);

    }

    public void pushTask(WeakReference<Activity> task) {
        activitys.push(task);
    }

    public void removeTask(WeakReference<Activity> task) {
        activitys.remove(task);
    }

    public void removeTask(int taskIndex) {
        if (activitys.size() > taskIndex)
            activitys.remove(taskIndex);
    }

    public void removeToTop() {
        int end = activitys.size();
        int start = 1;
        for (int i = end - 1; i >= start; i--) {
            if (!activitys.get(i).get().isFinishing()) {
                activitys.get(i).get().finish();
            }
        }
    }

    public void removeAll() {
        for (WeakReference<Activity> task : activitys) {
            if (!task.get().isFinishing()) {
                task.get().finish();
            }
        }
    }

}
