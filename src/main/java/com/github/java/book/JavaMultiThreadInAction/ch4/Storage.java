package com.github.java.book.JavaMultiThreadInAction.ch4;

import com.github.java.book.JavaMultiThreadInAction.util.Debug;
import com.github.java.book.JavaMultiThreadInAction.util.Tools;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author pengfei.zhao
 * @date 2020/10/22 8:28
 */
public class Storage implements Closeable, AutoCloseable {
    private final RandomAccessFile storeFile;
    private final FileChannel storeChannel;
    private final AtomicLong totalWrites = new AtomicLong(0);

    public Storage(long fileSize, String fileShortName) throws IOException {
        String fullFileName = System.getProperty("java.io.tmpdir") + "/" + fileShortName;
        String localFileName;
        localFileName = createStoreFile(fileSize, fullFileName);
        this.storeFile = new RandomAccessFile(localFileName, "rw");
        this.storeChannel = storeFile.getChannel();
    }

    /**
     * 将 data 中指定的数据写入文件
     *
     * @param offset 写入数据在整个文件中的起始偏移位置
     * @param byteBuffer byteBuf必须在该方法调用前执行byteBuf.flip()
     * @return 写入文件的数据长度
     * @throws IOException
     */
    public int store(long offset, ByteBuffer byteBuffer) throws IOException {
        int length;
        storeChannel.write(byteBuffer, offset);
        length = byteBuffer.limit();
        totalWrites.addAndGet(length);
        return length;
    }

    public long getTotalWrites() {
        return totalWrites.get();
    }

    private String createStoreFile(long fileSize, String fullFileName) throws IOException {
        final File file = new File(fullFileName);
        Debug.info("create local file: %s", fullFileName);
        RandomAccessFile raf;
        raf = new RandomAccessFile(file, "rw");
        try {
            raf.setLength(fileSize);
        } finally {
            Tools.silentClose(raf);
        }
        return fullFileName;
    }

    @Override
    public synchronized void close() throws IOException {
        if (storeChannel.isOpen()) {
            Tools.silentClose(storeChannel, storeFile);
        }
    }
}
