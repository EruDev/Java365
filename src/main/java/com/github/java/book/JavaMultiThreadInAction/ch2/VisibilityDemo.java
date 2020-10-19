package com.github.java.book.JavaMultiThreadInAction.ch2;

import com.github.java.book.JavaMultiThreadInAction.util.Tools;

/**
 * @author pengfei.zhao
 * @date 2020/10/19 15:36
 */
public class VisibilityDemo {
    public static void main(String[] args) {
        TimeConsumingTask task = new TimeConsumingTask();
        Thread thread = new Thread(task);
        thread.start();

        Tools.randomPause(10000);
        task.cancel();
    }
}

class TimeConsumingTask implements Runnable{
    private boolean toCancel = false;

    @Override
    public void run() {
        while (!toCancel){
            if (doExecute()){
                break;
            }
        }
        if (toCancel){
            System.out.println("Task is canceled.");
        } else{
            System.out.println("Task done.");
        }
    }

    private boolean doExecute() {
        boolean isDone = false;
        // 模拟业务操作需要的时间
        Tools.randomPause(50);
        return isDone;
    }

    void cancel(){
        toCancel = true;
        System.out.println(this + " is canceled.");
    }
}
