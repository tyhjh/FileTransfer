package com.yorhp.sftp;

/**
 * @author Tyhj
 * @date 2019/7/30
 */

public interface DonwloadListener {

    /**
     * 下载进度
     *
     * @param progress
     */
    void progress(int progress);

    /**
     * 下载失败
     *
     * @param msg
     */
    void fail(String msg);

    /**
     * 下载完成
     */
    void finish();
}
