package com.github.java.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


/**
 * 类似
 * Linux shell 文件查找
 * java Find . -name "*.java"
 *
 * @author pengfei.zhao
 * @date 2020/10/17 8:43
 */
public class Find {
    public static class Finder extends SimpleFileVisitor<Path> {
        private final PathMatcher matcher;
        private int numMatchers = 0;

        Finder(String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher("glob" + pattern);
        }

        void find(Path file) {
            Path fileName = file.getFileName();
            if (fileName != null && matcher.matches(fileName)) {
                numMatchers++;
                System.out.println(fileName);
            }
        }

        void done() {
            System.out.println("Matched: " + numMatchers);
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            find(dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            find(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            System.err.println(exc);
            return FileVisitResult.CONTINUE;
        }
    }

    static void usage() {
        System.err.println("java Find <path> -name \"<glob_pattern>\"");
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 3 || !args[1].equals("-name"))
            usage();

        Path startingDir = Paths.get(args[0]);
        String pattern = args[2];

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.done();
    }
}
