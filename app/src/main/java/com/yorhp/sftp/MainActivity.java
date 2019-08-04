package com.yorhp.sftp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yorhp.sftp.http.Download;
import com.yorhp.sftp.http.HttpMonitor;
import com.yorhp.sftp.threadpool.AppExecutors;

import java.io.IOException;

/**
 * @author Tyhj
 */
public class MainActivity extends AppCompatActivity {


    private String userName = "Tyhj";
    private String pwd = "1q";
    private String filePath = "/Users/Tyhj/Movies/jvbaoji.mp4";
    private String savePath = "/sdcard/Atest.mp4";
    private String host = "192.168.0.105";
    private int port = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        System.out.println("核心数为：" + Runtime.getRuntime().availableProcessors());

        findViewById(R.id.tvHello).setOnClickListener((v) -> {

            /*AppExecutors.getInstance().networkIO().execute(() -> {
                new DownLoad().start(new DonwloadListener() {
                    @Override
                    public void progress(int progress) {
                        ToastUtil.showShort("下载进度为：" + progress+"%");
                    }

                    @Override
                    public void fail(String msg) {

                    }

                    @Override
                    public void finish() {
                        ToastUtil.showShort("下载进度完成");
                    }
                });
            });*/

            Download download = new Download("192.168.0.119", 80);

            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        download.saveFile("/game_of_power03.mp4", "sdcard/AgamePower.mp4", new HttpMonitor(null));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        });
    }
}
