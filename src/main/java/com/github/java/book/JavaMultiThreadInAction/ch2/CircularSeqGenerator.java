package com.github.java.book.JavaMultiThreadInAction.ch2;

/**
 * @author pengfei.zhao
 * @date 2020/10/19 20:35
 */
public interface CircularSeqGenerator {

    /**
     * 下一个序列号
     *
     * @return 序列号
     */
    short nextSequence();
}
