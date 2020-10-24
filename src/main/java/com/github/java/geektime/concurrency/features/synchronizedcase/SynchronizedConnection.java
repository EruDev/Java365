package com.github.java.geektime.concurrency.features.synchronizedcase;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * 描述：
 * 保护有关联关系的多个对象, 银行转账问题
 *
 * @author pengfei.zhao
 * @date 2020/10/24 12:42
 */
@Slf4j
public class SynchronizedConnection {

    /**
     * 线程不安全
     */
    class UnsafeAccount {
        private int balance;

        /**
         * 转账
         *
         * @param target 目标账户
         * @param amt    转账数量
         */
        public void transfer(UnsafeAccount target, int amt) {
            if (this.balance >= amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }

    @Getter
    static class LooksSafeAccount {
        private String name;
        private volatile boolean flag = true;
        private int balance;

        public LooksSafeAccount(int balance) {
            this.balance = balance;
        }

        public LooksSafeAccount(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public void cancel() {
            this.flag = false;
        }

        /**
         * 锁对象为 this, 而target对象资源不受保护
         *
         * @param target 目标账户
         * @param amt    转账数量
         */
        public synchronized void transfer(LooksSafeAccount target, int amt) {
            while (flag) {
            }
            log.info("Before Transfer");
            log.info(Thread.currentThread().getName() + ": " + name + " balance: " + balance
                    + " target: " + target.getName() + " balance: " + target.getBalance());
            if (this.balance >= amt) {
                this.balance -= amt;
                target.balance += amt;
            }
            log.info("After Transfer");
            log.info(Thread.currentThread().getName() + ": " + name + " balance: " + balance
                    + " target: " + target.getName() + " balance: " + target.getBalance());
        }
    }

    /**
     * 通过锁类对象的方式
     * 能解决并发问题，但是会导致操作串行执行，性能极差不推荐使用
     */
    class SafeAccount {
        private int balance;

        public void transfer(SafeAccount account, int amt) {
            synchronized (SafeAccount.class) {
                if (this.balance >= amt) {
                    this.balance -= amt;
                    account.balance += amt;
                }
            }
        }
    }

    /**
     * 通过传入锁的方式 但是必须保持lock对象和Account 一对一绑定
     * 并且真实场景中创建真实对象的地方很可能被是分布式的，所以比较复杂也不推荐
     */
    class Account {
        private int balance;
        private Object lock;

        public void transfer(SafeAccount account, int amt) {
            synchronized (lock) {
                if (this.balance >= amt) {
                    this.balance -= amt;
                    account.balance += amt;
                }
            }

        }
    }

    public static void main(String[] args) {
        LooksSafeAccount A = new LooksSafeAccount("A", 200);
        LooksSafeAccount B = new LooksSafeAccount("B", 200);
        LooksSafeAccount C = new LooksSafeAccount("C", 200);

        Thread t1 = new Thread(() -> {
            A.transfer(B, 100);
        });

        Thread t2 = new Thread(() -> {
            B.transfer(C, 100);
        });

        t1.start();
        t2.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            final String msg = scanner.next();
            if ("1".equals(msg)) {
                A.cancel();
                B.cancel();
                C.cancel();
                break;
            }
        }
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        log.info("Finish!");
        log.info("A:" + A.getBalance());
        log.info("B:" + B.getBalance());
        log.info("C:" + C.getBalance());
    }
}
