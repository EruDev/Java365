package com.github.java;


import com.github.java.Debug;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author pengfei.zhao
 * @date 2020/10/18 16:52
 */
public final class Tools {
    private static final Random rnd = new Random();
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static void startAndWaitTerminated(Thread... threads)
            throws InterruptedException {
        if (null == threads) {
            throw new IllegalArgumentException("threads is null!");
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    public static void startThread(Thread... threads) {
        if (null == threads) {
            throw new IllegalArgumentException("threads is null!");
        }
        for (Thread t : threads) {
            t.start();
        }
    }

    public static void startAndWaitTerminated(Iterable<Thread> threads)
            throws InterruptedException {
        if (null == threads) {
            throw new IllegalArgumentException("threads is null!");
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    public static void randomPause(int maxPauseTime) {
        int sleepTime = rnd.nextInt(maxPauseTime);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void randomPause(int maxPauseTime, int minPauseTime) {
        int sleepTime = maxPauseTime == minPauseTime ? minPauseTime : rnd
                .nextInt(maxPauseTime - minPauseTime) + minPauseTime;
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //public static Unsafe getUnsafe() {
    //    try {
    //        Field f = Unsafe.class.getDeclaredField("theUnsafe");
    //        f.setAccessible(true);
    //        return (Unsafe) f.get(null);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //    return null;
    //}

    public static void silentClose(Closeable... closeable) {
        if (null == closeable) {
            return;
        }
        for (Closeable c : closeable) {
            if (null == c) {
                continue;
            }
            try {
                c.close();
            } catch (Exception ignored) {
            }
        }
    }

    public static void split(String str, String[] result, char delimeter) {
        int partsCount = result.length;
        int posOfDelimeter;
        int fromIndex = 0;
        String recordField;
        int i = 0;
        while (i < partsCount) {
            posOfDelimeter = str.indexOf(delimeter, fromIndex);
            if (-1 == posOfDelimeter) {
                recordField = str.substring(fromIndex);
                result[i] = recordField;
                break;
            }
            recordField = str.substring(fromIndex, posOfDelimeter);
            result[i] = recordField;
            i++;
            fromIndex = posOfDelimeter + 1;
        }
    }

    public static void log(String message) {
        LOGGER.log(Level.INFO, message);
    }

    public static String md5sum(final InputStream in) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buf = new byte[1024];
        try (DigestInputStream dis = new DigestInputStream(in, md)) {
            while (-1 != dis.read(buf))
                ;
        }
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String checkSum = bigInt.toString(16);

        while (checkSum.length() < 32) {
            checkSum = "0" + checkSum;
        }
        return checkSum;
    }

    public static String md5sum(final File file) throws NoSuchAlgorithmException, IOException {
        return md5sum(new BufferedInputStream(new FileInputStream(file)));
    }

    public static String md5sum(String str) throws NoSuchAlgorithmException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
        return md5sum(in);
    }

    public static void delayedAction(String prompt, Runnable action, int delay/* seconds */) {
        Debug.info("%s in %d seconds.", prompt, delay);
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException ignored) {
        }
        action.run();
    }

    public static Object newInstanceOf(String className) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        return Class.forName(className).newInstance();
    }

}
