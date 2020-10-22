package com.github.java.book.JavaMultiThreadInAction.ch4;

import java.nio.ByteBuffer;

/**
 * @author pengfei.zhao
 * @date 2020/10/22 8:52
 */
public class DownloadBuffer {
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

    public void write(ByteBuffer buf){

    }
}
