package com.github.java.book.JavaMultiThreadInAction.ch1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author pengfei.zhao
 * @date 2020/10/18 20:56
 */
@WebServlet("/echo")
public class EchoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Thread currentThread = Thread.currentThread();
        String currentThreadName = currentThread.getName();
        resp.setContentType("text/plain");
        try(PrintWriter writer = resp.getWriter()) {
            writer.printf("The currentThread name: %s", currentThreadName);
            writer.flush();
        }
    }
}
