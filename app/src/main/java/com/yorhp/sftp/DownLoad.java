package com.yorhp.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author Tyhj
 * @date 2019/7/30
 */

public class DownLoad {

    private String userName = "Tyhj";
    private String pwd = "1q";
    private String filePath = "/Users/Tyhj/Movies/jvbaoji.mp4";
    private String savePath = "/sdcard/Atest.mp4";
    private String host = "192.168.0.105";
    private int port = 22;

    public void start(DonwloadListener listener) {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp;

        // 创建JSch对象
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(userName, host, port);
            session.setPassword(pwd);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(2000);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            SftpDownloadMonitor monitor = new SftpDownloadMonitor(listener);
            channelSftp.get(filePath,new FileOutputStream(new File(savePath)), monitor);

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
