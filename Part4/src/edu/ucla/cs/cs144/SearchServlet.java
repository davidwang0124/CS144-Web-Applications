package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    private Integer k = 20;
    private Integer nextCount = 0;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        String searchText = request.getParameter("q");
        String next = request.getParameter("next");
        if (next != null && next.equals("1")) {
            nextCount++;
        } else {
            nextCount = 0;
        }
        SearchResult[] sr = AuctionSearch.basicSearch(searchText, nextCount * k, (nextCount + 1) * k);
        request.setAttribute("searchText", searchText);
        request.setAttribute("searchResults", sr);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
