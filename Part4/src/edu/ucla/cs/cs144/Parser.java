package edu.ucla.cs.cs144;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Text;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Parser {
	private static int maxDescriptionLength = 4000;

	private static String dateFormat(String input) {
		SimpleDateFormat xmlFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ts = "";
        try {
            Date inputDate = xmlFormat.parse(input);
            ts = sqlFormat.format(inputDate);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return ts;
	}

	private static ArrayList<Bid> getBids(Element item) {
		ArrayList<Bid> bidsList = new ArrayList<Bid>();
        Element[] bids = getElementsByTagNameNR(getElementByTagNameNR(item, "Bids"), "Bid");
        for(Element bid: bids) {
        	Element bidder = getElementByTagNameNR(bid, "Bidder");
            String id = bidder.getAttribute("UserID");
            String rating = bidder.getAttribute("Rating");
            String location = getElementTextByTagNameNR(bidder, "Location");
            String country = getElementTextByTagNameNR(bidder, "Country");
            String time = dateFormat(getElementTextByTagNameNR(bid, "Time"));
            String amount = getElementTextByTagNameNR(bid, "Amount");

            Bid bidItem = new Bid(id, rating, location, country, time, amount);
            bidsList.add(bidItem);
        }
        Collections.sort(bidsList, new Comparator<Bid>() {
        	@Override
		    public int compare(final Bid bid1, final Bid bid2) {
		    	String date1 = "";
		    	String date2 = "";
		      	try {
		        	date1 = dateFormat(bid1.getBidTime());
		        	date2 = dateFormat(bid2.getBidTime());
		      	} catch (Exception e) {
		        	e.printStackTrace();
		      	}
		      	return date1.compareTo(date2);
		   	}
        });
		return bidsList;
	}

	private static String getNodeText(Node node) {
		if(node.getChildNodes().getLength() == 1) {
			return node.getFirstChild().getNodeValue();
		} else {
			return "";
		}
	}

	/* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }

	/* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }

    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }

	/* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }

	public static Item parseXML(Element item) throws Exception {
		
		ArrayList<String> categoriesList = new ArrayList<String>();
		ArrayList<Bid> bidsList = new ArrayList<Bid>();

		String id = item.getAttributes().getNamedItem("ItemID").getNodeValue();
		String name = getElementTextByTagNameNR(item, "Name");

		// Categories
		for(Element category: getElementsByTagNameNR(item, "Category")) {
			categoriesList.add(category.getTextContent());
		}

		String currently = getElementTextByTagNameNR(item, "Currently");
		String buyPrice = getElementTextByTagNameNR(item, "Buy_Price");
		String firstBid = getElementTextByTagNameNR(item, "First_Bid");
		String numberofBids = getElementTextByTagNameNR(item, "Number_of_Bids");

		// Bids
		bidsList = getBids(item);

		String location = getElementTextByTagNameNR(item, "Location");
        String longitude = getElementByTagNameNR(item, "Location").getAttribute("Longitude");
        String latitude = getElementByTagNameNR(item, "Location").getAttribute("Latitude");
        String country = getElementTextByTagNameNR(item, "Country");

		String started = dateFormat(getElementTextByTagNameNR(item, "Started"));
		String ends = dateFormat(getElementTextByTagNameNR(item, "Ends"));

		// Seller
		String sellerId = getElementByTagNameNR(item, "Seller").getAttribute("UserID");
        String rating = getElementByTagNameNR(item, "Seller").getAttribute("Rating");

		String description = getElementTextByTagNameNR(item, "Description");
		if(description.length() > maxDescriptionLength)
			description = description.substring(0, maxDescriptionLength);
		
		Item itemInstance = new Item(id, name, currently, buyPrice, firstBid, numberofBids, location,
				 latitude, longitude, country, started, ends, sellerId, description);
		itemInstance.setCategories(categoriesList.toArray(new String[categoriesList.size()]));
		
        itemInstance.setBids(bidsList.toArray(new Bid[bidsList.size()]));
        return itemInstance;
	}
}