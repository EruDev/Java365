package com.github.java.book.jvm.ch3;

/**
 * 代码目的：
 * 1.对象可以在被GC时自我拯救
 * 2.这种自救机会只有一次，因为一个对象的finalize()方法最多只会被系统自动调用一次
 *
 * @author pengfei.zhao
 * @date 2020/10/31 9:01
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method execute");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();

        // 对象第一次成功自救
        SAVE_HOOK = null;
        System.gc();
        // 因为Finalizer方法优先级很低,暂停0.5秒，以等待它执行
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }

        // 这次却自救失败了
        SAVE_HOOK = null;
        System.gc();
        // 因为Finalizer方法优先级很低,暂停0.5秒，以等待它执行
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }
    }
}
