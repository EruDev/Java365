package com.github.java.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author pengfei.zhao
 * @date 2020/10/15 8:29
 */
public class CopyCharacters {
    public static void main(String[] args) throws IOException {
        FileReader reader = null;
        FileWriter writer = null;
        try {
            reader = new FileReader("test.txt");
            writer = new FileWriter("test.txt");
            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (reader != null) {
                writer.close();
            }
        }
    }
}
