package com.github.java.io;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path
 *
 * @author pengfei.zhao
 * @date 2020/10/16 7:40
 */
public class PathDemo {
    public static void main(String[] args) {
        //Path p1 = Paths.get("/tmp/foo");
        //Path p2 = Paths.get(args[0]);
        //Path p3 = Paths.get(URI.create("file://User/joe/FileTest.java"));
        //Path p4 = Paths.get(System.getProperty("user.home"), "logs", "foo.log");
        //System.out.println(p4);

        Path path = Paths.get("C:\\home\\joe\\foo");
        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());
    }
}
