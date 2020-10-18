package com.github.java.concurrency;

/**
 * 死锁
 *
 * @author pengfei.zhao
 * @date 2020/10/17 12:19
 */
public class DeadLock {

    static class Friend{
        private String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void bow(Friend bower){
            System.out.format("%s: %s has bowed to me!%n",
                    this.getName(), bower.getName());
            bower.bowBack(this);
        }

        private synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s has bowed back to me!%n",
                    this.name, bower.getName());
        }
    }

    public static void main(String[] args) {
        Friend alphonse = new Friend("Alphonse");
        Friend gaston = new Friend("gaston");
        new Thread(() -> alphonse.bow(gaston)).start();
        new Thread(() -> gaston.bow(alphonse)).start();
    }
}
