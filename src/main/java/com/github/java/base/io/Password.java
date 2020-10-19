package com.github.java.io;

import java.io.Console;
import java.util.Arrays;

/**
 * @author pengfei.zhao
 * @date 2020/10/15 19:51
 */
public class Password {

    public static void main(String[] args) {
        Console c = System.console();

        if (c == null){
            System.err.println("No console.");
            System.exit(1);
        }

        String login = c.readLine("Enter your login: ");
        char[] oldPwd = c.readPassword("Enter your old password: ");

        if (verify(login, oldPwd)){
            boolean noMatch = false;
            do {
                char[] newPwd1 = c.readPassword("Enter your new password: ");
                char[] newPwd2 = c.readPassword("Enter your new password again: ");
                noMatch = !Arrays.equals(newPwd1, newPwd2);
                if (noMatch){
                    c.format("Password don't match. try again.%n");
                } else {
                    change(login, newPwd1);
                    c.format("Password for %s changed.%n", login);
                }
                Arrays.fill(newPwd1, ' ');
                Arrays.fill(newPwd2, ' ');
            }while (noMatch);
        }
    }

    private static void change(String login, char[] newPwd1) {

    }

    private static boolean verify(String login, char[] oldPwd) {
        return true;
    }
}
