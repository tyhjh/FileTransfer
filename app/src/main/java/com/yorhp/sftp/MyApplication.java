package com.yorhp.sftp;

import android.app.Application;

import com.yorhp.sftp.threadpool.AppExecutors;

import toast.ToastUtil;

/**
 * @author Tyhj
 * @date 2019/7/30
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppExecutors.getInstance();
        ToastUtil.init(this);
    }
}
