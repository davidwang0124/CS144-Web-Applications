package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {

    public SearchServlet() {}

    private Integer nextCount = 0;
    private String searchText;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        String requestSearchText = request.getParameter("q");
        String next = request.getParameter("next");
        SearchResult[] sr = null;
        if (next != null && next.equals("1")) {
            nextCount++;
        } else {
            searchText = requestSearchText;
            nextCount = 0;
        }
        if (searchText != null) {
            int k = 20;
            sr = AuctionSearch.basicSearch(searchText, nextCount * k, (nextCount + 1) * k);
        } else {
            sr = new SearchResult[0];
        }
        request.setAttribute("searchText", searchText);
        request.setAttribute("searchResults", sr);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
