package com.github.java.book.JavaMultiThreadInAction.ch1;

import com.github.java.Debug;
import com.github.java.Tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 文件下载器
 *
 * @author pengfei.zhao
 * @date 2020/10/18 21:23
 */
public class FileDownloaderApp {
    public static void main(String[] args) {
        Thread downloaderThread = null;
        for (String url : args) {
            downloaderThread = new Thread(new FileDownloader(url));
            downloaderThread.start();
        }
    }

    static class FileDownloader implements Runnable {
        private final String fileUrl;

        public FileDownloader(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        @Override
        public void run() {
            Debug.info("Downloading from " + fileUrl);
            String fileBaseName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            try {
                final URL url = new URL(fileUrl);
                final String localFileName = System.getProperty("java.io.tmpdir")
                        + "erudev-"
                        + fileBaseName;
                Debug.info("Saving to: " + localFileName);
                downloadFile(url, new FileOutputStream(localFileName), 1024);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Debug.info("Done downloading from " + fileUrl);
        }

        private void downloadFile(URL fileURL, OutputStream outputStream, int bufSize) {
            try {
                final HttpURLConnection httpConnection = (HttpURLConnection) fileURL.openConnection();
                httpConnection.setRequestMethod("GET");
                ReadableByteChannel inChannel = null;
                WritableByteChannel outChannel = null;
                try {
                    final int responseCode = httpConnection.getResponseCode();
                    if (2 != responseCode / 100) {
                        throw new IOException("Error: HTTP " + responseCode);
                    }

                    if (0 == httpConnection.getContentLength()) {
                        Debug.info("Nothing to be downloaded " + fileURL);
                        return;
                    }
                    inChannel = Channels.newChannel(
                            new BufferedInputStream(httpConnection.getInputStream()));
                    outChannel = Channels.newChannel(
                            new BufferedOutputStream(outputStream));
                    final ByteBuffer buf = ByteBuffer.allocate(bufSize);
                    while (-1 != inChannel.read(buf)) {
                        buf.flip();
                        outChannel.write(buf);
                        buf.clear();
                    }
                } finally {
                    // 关闭指定的Channel以及HttpURLConnection
                    Tools.silentClose(inChannel, outChannel);
                    httpConnection.disconnect();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
