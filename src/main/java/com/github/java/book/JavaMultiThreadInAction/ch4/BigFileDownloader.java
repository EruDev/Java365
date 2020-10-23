package com.github.java.book.JavaMultiThreadInAction.ch4;

import com.github.java.Debug;
import com.github.java.Tools;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author pengfei.zhao
 * @date 2020/10/22 8:37
 */
public class BigFileDownloader {
    protected final URL requestUrl;
    private final long fileSize;

    /**
     * 负责已下载数据的存储
     */
    protected final Storage storage;
    private final AtomicBoolean taskCanceled = new AtomicBoolean(false);

    public BigFileDownloader(String strURL) throws Exception {
        requestUrl = new URL(strURL);

        // 获取下载资源的大小（单位：字节）
        fileSize = retieveFileSize(requestUrl);
        Debug.info("file total size: %s", fileSize);
        String fileName = strURL.substring(strURL.lastIndexOf("/") + 1);
        storage = new Storage(fileSize, fileName);
    }

    public void download(int taskCount, int reportInterval) throws InterruptedException {
        long chunkSizePerThread = fileSize / taskCount;
        // 下载数据段的起始字节
        long lowerBound = 0;
        // 下载数据段的结束字节
        long upperBound = 0;

        DownloadTask dt;
        for (int i = taskCount - 1; i >= 0; i--) {
            lowerBound = chunkSizePerThread * i;
            if (i == taskCount - 1) {
                upperBound = fileSize;
            } else {
                upperBound = lowerBound + chunkSizePerThread - 1;
            }

            // 创建下载任务
            dt = new DownloadTask(lowerBound, upperBound, requestUrl, storage, taskCanceled);
            dispatchWork(dt, i);
        }
        // 定时报告下载进度
        reportProcess(reportInterval);
        // 清理程序占用的资源
        doCleanup();
    }

    private void dispatchWork(DownloadTask dt, int i) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dt.run();
                } catch (Exception e) {
                    e.printStackTrace();
                    // 取消整个文件的下载
                    cancelDownload();
                }
            }
        });
    }

    protected void cancelDownload() {
        if (taskCanceled.compareAndSet(false, true)) {
            doCleanup();
        }
    }

    private void doCleanup() {
        Tools.silentClose(storage);
    }

    private void reportProcess(int reportInterval) throws InterruptedException {
        float lastCompletion;
        int completion = 0;
        while (!taskCanceled.get()) {
            lastCompletion = completion;
            completion = (int) (storage.getTotalWrites() * 100 / fileSize);
            if (completion == 100) {
                break;
            } else if (completion - lastCompletion >= 1) {
                Debug.info("Completion:%s%%", completion);
                if (completion >= 90) {
                    reportInterval = 1000;
                }
            }
            Thread.sleep(reportInterval);
        }
        Debug.info("Completion:%s%%", completion);
    }

    private long retieveFileSize(URL requestUrl) throws Exception {
        long size = -1;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setRequestProperty("Connection", "Keep-alive");
            conn.connect();
            final int code = conn.getResponseCode();
            if (HttpURLConnection.HTTP_OK != code) {
                throw new Exception("Server exception,status code:" + code);
            }

            String cl = conn.getHeaderField("Content-Length");
            size = Long.parseLong(cl);
        } finally {
            if (null != conn) {
                conn.disconnect();
                ;
            }
        }
        return size;
    }
}
