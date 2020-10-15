package com.github.java.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author pengfei.zhao
 * @date 2020/10/15 8:15
 */
public class CopyBytes {
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fop = null;
        try {
            fis = new FileInputStream("xanadu.txt");
            fop = new FileOutputStream("test.txt");
            int c;
            while ((c = fis.read()) != -1) {
                fop.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fop != null) {
                try {
                    fop.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
