package com.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@WebServlet(value = "/")
public class MainServlet extends HttpServlet {
    public static final List<String> users = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        String user = req.getHeader("user-agent") + " " + req.getRemoteAddr();
        out.print("<b>" + user + "</b>");
        out.print("<br>");
        users.forEach(x -> {
            out.print(x);
            out.print("<br>");
        });
        users.add(user);
    }
}
