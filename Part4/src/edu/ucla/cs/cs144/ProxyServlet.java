package edu.ucla.cs.cs144;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet implements Servlet {

    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        String baseUrl = "http://google.com/complete/search?output=toolbar&q=";
        String rawQuery = request.getParameter("q");
        String query = URLEncoder.encode(rawQuery, "UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        if (query != null && !query.equals("")) {
            HttpURLConnection conn = (HttpURLConnection) new URL(baseUrl + query).openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line  = rd.readLine()) != null) {
                out.println(line);
            }
            rd.close();
        }
    }
}
