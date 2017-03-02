package edu.ucla.cs.cs144;

import java.io.IOException;
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
    	try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		String xmlData = AuctionSearch.getXMLDataForItemId(request.getParameter("id"));
    		if(xmlData != null) {
                InputSource source = new InputSource(new StringReader(xmlData));
    			Document doc = builder.parse(source);
    			Item item = Parser.parseXML(doc.getDocumentElement());
                request.setAttribute("item", item);
    		}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
        request.getRequestDispatcher("/item.jsp").forward(request, response);
    }
}
