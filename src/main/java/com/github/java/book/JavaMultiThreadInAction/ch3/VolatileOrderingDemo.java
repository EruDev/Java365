package com.github.java.book.JavaMultiThreadInAction.ch3;

import com.github.java.book.JavaMultiThreadInAction.stf.Actor;
import com.github.java.book.JavaMultiThreadInAction.stf.ConcurrencyTest;
import com.github.java.book.JavaMultiThreadInAction.stf.Expect;
import com.github.java.book.JavaMultiThreadInAction.stf.Observer;
import com.github.java.book.JavaMultiThreadInAction.stf.TestRunner;

/**
 * @author pengfei.zhao
 * @date 2020/10/20 18:32
 */
@ConcurrencyTest(iterations = 2000)
public class VolatileOrderingDemo {
    private int dataA = 0;
    private long dataB = 0L;
    private String dataC = null;
    private volatile boolean ready = false;

    @Actor
    public void writer() {
        dataA = 1;
        dataB = 10000L;
        dataC = "Content...";
        ready = true;
    }

    @Observer({
        @Expect(desc = "Normal", expected = 1),
        @Expect(desc = "Impossible", expected = 2),
        @Expect(desc = "ready not true", expected = 3)
    })
    public int reader() {
        int result = 0;
        boolean allIsOk;
        if (ready) {
            allIsOk = (dataA == 1) && (dataB == 10000L) && ("Content...".equals(dataC));
            result = allIsOk ? 1 : 2;
        } else {
            result = 3;
        }
        return result;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        TestRunner.runTest(VolatileOrderingDemo.class);
    }
}
