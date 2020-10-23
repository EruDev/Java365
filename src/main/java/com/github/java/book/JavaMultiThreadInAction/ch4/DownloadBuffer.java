package com.github.java.book.JavaMultiThreadInAction.ch4;

import com.github.java.Debug;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author pengfei.zhao
 * @date 2020/10/22 8:52
 */
public class DownloadBuffer implements Closeable {
    /**
     * 当前Buffer中缓冲的数据相对于整个存储文件的位置偏移
     */
    private long globalOffset;
    private long upperBound;
    private int offset = 0;
    public final ByteBuffer byteBuf;
    private final Storage storage;

    public DownloadBuffer(long globalOffset, long upperBound,
                          final Storage storage) {
        this.globalOffset = globalOffset;
        this.upperBound = upperBound;
        this.byteBuf = ByteBuffer.allocate(1024 * 1024);
        this.storage = storage;
    }

    public void write(ByteBuffer buf) throws IOException {
        int length = buf.position();
        int capacity = byteBuf.capacity();
        // 当缓存区已满, 或者剩余容量不够刷新新数据
        if (offset + length > capacity || length == capacity) {
            // 将缓存区的数据写入文件
            flush();
        }
        byteBuf.position(offset);
        buf.flip();
        byteBuf.put(buf);
        offset += length;
    }

    @Override
    public void close() throws IOException {
        Debug.info("globalOffset:%s,upperBound:%s", globalOffset, upperBound);
        if (globalOffset < upperBound) {
            flush();
        }
    }

    private void flush() throws IOException {
        int length;
        byteBuf.flip();
        length = storage.store(globalOffset, byteBuf);
        byteBuf.clear();
        globalOffset += length;
        offset = 0;
    }
}
