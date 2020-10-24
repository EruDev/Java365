package com.github.java.geektime.concurrency.features.synchronizedcase;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 解决死锁问题
 *
 * @author pengfei.zhao
 * @date 2020/10/24 13:30
 */
public class SynchronizedResolveDeadLock {

    /**
     * 账本管理员
     */
    class Allocator {
        private List<Object> als = new ArrayList<>();

        /**
         * 只有同时拿到转出和转入的账本 才能执行转账操作
         *
         * @param from 转入账本
         * @param to   转出账本
         * @return 是否成功
         */
        public boolean apply(Object from, Object to) {
            if (als.contains(from) || als.contains(to)) {
                return false;
            } else {
                als.add(from);
                als.add(to);
            }
            return true;
        }

        public void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
        }
    }

    class Account {
        private Allocator allocator;
        private int balance;

        public Account(Allocator allocator, int balance) {
            this.allocator = allocator;
            this.balance = balance;
        }

        public void transfer(Account target, int amt) {
            while (!allocator.apply(this, target)) {
            }

            if (this.balance >= amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }

    /**
     * 通过ID大小顺序申请锁
     */
    class SortIdAccount {
        private int id;
        private int balance;

        public SortIdAccount(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }

        public void transfer(SortIdAccount target, int amt) {
            SortIdAccount left = this;
            SortIdAccount right = target;

            if (this.id > target.id) {
                left = target;
                right = this;
            }
            synchronized (left) {
                synchronized (right) {
                    if (this.balance >= amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        }
    }
}
