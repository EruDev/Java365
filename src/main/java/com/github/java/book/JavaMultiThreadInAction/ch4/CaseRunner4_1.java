package com.github.java.book.JavaMultiThreadInAction.ch4;

import com.github.java.book.JavaMultiThreadInAction.util.Debug;

/**
 * @author pengfei.zhao
 * @date 2020/10/22 18:40
 */
public class CaseRunner4_1 {
    public static void main(String[] args) throws Exception {
        if (0 == args.length) {
            args = new String[] { "http://yourserver.com/bigfile", "2", "3" };
        }
        main0(args);
    }

    private static void main0(String[] args) throws Exception {
        final int argc = args.length;
        BigFileDownloader downloader = new BigFileDownloader(args[0]);

        // 下载线程数
        int workerThreadsCount = argc >= 2 ? Integer.valueOf(args[1]) : 2;
        long reportInterval = argc >= 3 ? Integer.valueOf(args[2]) : 2;

        Debug.info("downloading %s%nConfig:worker threads:%s,reportInterval:%s s.",
                args[0], workerThreadsCount, reportInterval);

        downloader.download(workerThreadsCount, (int) (reportInterval * 1000));
    }
}
