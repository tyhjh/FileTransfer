package com.yorhp.sftp.http;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Tyhj
 * @date 2019/8/4
 */

public class Download {

    String host;
    int port;


    public Download(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void saveFile(String filePath, String savePath, HttpProgressMonitor monitor) throws IOException {
        String urlStr = "http://" + host + ":" + port + filePath;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        OutputStream outputStream = new FileOutputStream(new File(savePath));
        if (inputStream != null && outputStream != null) {
            if (monitor != null) {
                monitor.start(filePath, savePath, conn.getContentLength());
            }
            byte[] bytes = new byte[1024 * 10];
            int read = inputStream.read(bytes);
            while (read != -1) {
                outputStream.write(bytes, 0, read);
                if (monitor != null) {
                    if (!monitor.count(read)) {
                        end(monitor, conn, inputStream, outputStream);
                        return;
                    }
                }
                read = inputStream.read(bytes);
            }
            end(monitor, conn, inputStream, outputStream);
        } else {
            Log.e("saveFile", "连接失败");
        }
    }


    /**
     * 完成文件下载，释放资源
     *
     * @param monitor
     * @param conn
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    private void end(HttpProgressMonitor monitor, HttpURLConnection conn, InputStream inputStream, OutputStream outputStream) throws IOException {
        outputStream.close();
        inputStream.close();
        conn.disconnect();
        if (monitor != null) {
            monitor.end();
        }
    }


    public void saveFile(String filePath, String savaPath) throws IOException {
        saveFile(filePath, savaPath, null);
    }

}
