package com.github.java.book.JavaMultiThreadInAction.ch2;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pengfei.zhao
 * @date 2020/10/19 20:59
 */
public class RequestIDGenerator implements CircularSeqGenerator {
    private final static RequestIDGenerator INSTANCE = new RequestIDGenerator();
    private final static short SEQ_UPPER_LIMIT = 999;
    private short sequence = -1;

    // 不允许外部构造
    private RequestIDGenerator() {
    }

    @Override
    public short nextSequence() {
        if (sequence >= SEQ_UPPER_LIMIT) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }

    public String nextId() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        final String date = sdf.format(new Date());
        final DecimalFormat df = new DecimalFormat("000");

        final short sequenceNo = nextSequence();

        return "0049" + date + df.format(sequenceNo);
    }

    public static RequestIDGenerator getInstance() {
        return INSTANCE;
    }
}
