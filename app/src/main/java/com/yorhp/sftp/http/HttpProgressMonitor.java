package com.yorhp.sftp.http;

/**
 * @author Tyhj
 * @date 2019/8/4
 */

public interface HttpProgressMonitor {
    /**
     * 开始文件传输
     *
     * @param filePath
     * @param savePath
     * @param fileSize
     */
    void start(String filePath, String savePath, long fileSize);

    /**
     * 每次传输的文件大小
     *
     * @param increment
     * @return
     */
    boolean count(long increment);

    /**
     * 传输完成
     */
    void end();
}
