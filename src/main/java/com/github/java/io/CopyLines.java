package com.github.java.io;

import java.io.*;

/**
 * @author pengfei.zhao
 * @date 2020/10/15 8:34
 */
public class CopyLines {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader("test.txt"));
            writer = new PrintWriter(new FileWriter("test.txt"));
            String l;
            while ((l = reader.readLine()) != null) {
                writer.println(l);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
}
