package com.github.java.book.jvm.ch2;

import lombok.extern.slf4j.Slf4j;

/**
 * 虚拟机栈和本地方法栈溢出
 * VM args: -Xss128k
 *
 * @author pengfei.zhao
 * @date 2020/10/29 8:42
 */
@Slf4j
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        final JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            log.error("stack length: " + oom.stackLength);
            throw e;
        }
    }
}
