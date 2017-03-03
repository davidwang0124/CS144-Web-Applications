package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;


public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        Item item = null;
    	try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		String xmlData = AuctionSearch.getXMLDataForItemId(request.getParameter("id"));
    		if(xmlData != null) {
    			Document doc = builder.parse(new ByteArrayInputStream(xmlData.getBytes()));
    			item = Parser.parseXML(doc.getDocumentElement());
                request.setAttribute("item", item);
                request.setAttribute("id", item.getItemId());
                request.setAttribute("name", item.getItemName());
                request.setAttribute("categories", item.getItemCategories());
                request.setAttribute("currently", item.getItemCurrently());
                request.setAttribute("buyPrice", item.getItemBuyPrice());
                request.setAttribute("firstBid", item.getItemFirstBid());
                request.setAttribute("numberofBids", item.getItemNumberOfBids());
                request.setAttribute("bids", item.getItemBids());
                request.setAttribute("location", item.getItemLocation());
                request.setAttribute("latitude", item.getItemLatitude());
                request.setAttribute("longitude", item.getItemLongitude());
                request.setAttribute("country", item.getItemCountry());
                request.setAttribute("started", item.getItemStarted());
                request.setAttribute("ends", item.getItemEnds());
                request.setAttribute("sellerId", item.getItemSellerId());
                request.setAttribute("description", item.getItemDescription());
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
        
        request.getRequestDispatcher("/item.jsp").forward(request, response);
    }
}
