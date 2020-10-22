package com.github.java.book.JavaMultiThreadInAction.ch4;

import com.github.java.book.JavaMultiThreadInAction.util.Tools;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 下载子任务
 *
 * @author pengfei.zhao
 * @date 2020/10/22 8:43
 */
public class DownloadTask implements Runnable{
    private final long lowerBound;
    private final long upperBound;
    private final DownloadBuffer xbuf;
    private final URL requestURL;
    private final AtomicBoolean cancelFlag;

    public DownloadTask(long lowerBound, long upperBound, URL requestURL,
                        Storage storage, AtomicBoolean cancelFlag) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.requestURL = requestURL;
        this.xbuf = new DownloadBuffer(lowerBound, upperBound, storage);
        this.cancelFlag = cancelFlag;
    }

    @Override
    public void run() {
        if (cancelFlag.get()){
            return;
        }

        ReadableByteChannel channel = null;
        try {
            channel = Channels.newChannel(issueRequest(requestURL, lowerBound, upperBound));
            final ByteBuffer buf = ByteBuffer.allocate(1024);
            while (!cancelFlag.get() && channel.read(buf) > 0){
            // 将从网络读取的数据写入缓冲区
                xbuf.write(buf);
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Tools.silentClose(channel, xbuf);
        }
    }
}
