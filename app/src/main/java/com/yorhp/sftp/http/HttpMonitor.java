package com.yorhp.sftp.http;

import com.yorhp.sftp.DonwloadListener;
import com.yorhp.sftp.threadpool.AppExecutors;

/**
 * @author Tyhj
 * @date 2019/8/5
 */

public class HttpMonitor implements HttpProgressMonitor {
    DonwloadListener listener;

    public HttpMonitor(DonwloadListener listener) {
        this.listener = listener;
    }


    int checkTime = 2000;
    long lastTime = 0;
    long lastDownloadSize = 0;
    long downloadSize = 0;

    long fileSize;


    @Override
    public void start(String s, String s1, long max) {
        System.out.println("开始下载：" + max / 1024 / 1024 + "M");
        lastTime = System.currentTimeMillis();
        this.fileSize = max;
    }

    @Override
    public boolean count(long count) {
        downloadSize = downloadSize + count;
        long intervalTime = System.currentTimeMillis() - lastTime;
        if (intervalTime >= checkTime) {
            int speed = (int) ((downloadSize - lastDownloadSize) * 1000 / intervalTime / 1024);
            System.out.println("下载速度为：" + speed + "kb/s" + "  下载进度为：" + downloadSize * 100 / (fileSize + 1) + "%");
            lastDownloadSize = downloadSize;
            lastTime = System.currentTimeMillis();
            AppExecutors.getInstance().mainThread().execute(() -> {
                if (listener != null) {
                    listener.progress((int) (downloadSize * 100 / (fileSize + 1)));
                }
            });
        }

        return true;
    }

    @Override
    public void end() {
        System.out.println("下载完成");
        AppExecutors.getInstance().mainThread().execute(() -> {
            listener.finish();
        });
    }
}
