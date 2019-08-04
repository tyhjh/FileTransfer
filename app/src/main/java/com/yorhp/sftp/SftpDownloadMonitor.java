package com.yorhp.sftp;

import com.jcraft.jsch.SftpProgressMonitor;
import com.yorhp.sftp.threadpool.AppExecutors;

/**
 * @author Tyhj
 * @date 2019/7/30
 */

public class SftpDownloadMonitor implements SftpProgressMonitor {

    DonwloadListener listener;

    public SftpDownloadMonitor(DonwloadListener listener) {
        this.listener = listener;
    }


    int checkTime = 1000;
    long lastTime = 0;
    long lastDownloadSize = 0;
    long downloadSize = 0;

    long fileSize;


    @Override
    public void init(int i, String s, String s1, long max) {
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
            System.out.println("下载速度为：" + speed + "kb/s");
            lastDownloadSize = downloadSize;
            lastTime = System.currentTimeMillis();
            AppExecutors.getInstance().mainThread().execute(() -> {
                listener.progress((int) (downloadSize * 100 / fileSize));
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
