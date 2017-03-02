package edu.ucla.cs.cs144;

import java.util.*;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Parser {
	private static int maxDescriptionLength = 4000;

	private String dateFormat(String input) {
		SimpleDateFormat xmlFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ts = "";
        try {
            Date inputDate = xmlFormat.parse(input);
            ts = sqlFormat.format(inputDate);
        } catch(ParseException pe) {
            System.out.println("ERROR: could not parse \"" + input + "\"");
            System.exit(3);
        }
        return ts;
	}

	private ArrayList<Bid> getBids(Node node) {
		ArrayList<Bid> bidsList = new ArrayList<Bid>();
		NodeList bids = node.getChildNodes();
		for(int i = 0; i < bids.getLength(); i++) {
			Node n = bids.item(i);
			String bidderId = n.getAttribute("UserID");
			String rating = n.getAttribute("Rating");
			switch (n.getNodeName()) {
				case "Bidder":
					String location = getNodeText(n.getFirstChild());
					String country = getNodeText(n.getLastChild());
					break;
				case "Time":
					String time = dateFormat(getNodeText(n));
					break;
				case "Amount":
					String amount = getNodeText(n);
					break;
				default:
					break;
			}
			bidsList.add(new Bid(bidderId, rating, location, country, time, amount));
		}
		return bidsList;
	}

	private String getNodeText(Node node) {
		if(node.getChildNodes().getLength() == 1) {
			return node.getFirstChild().getNodeValue();
		} else {
			return "";
		}
	}


	public Item parseXML(Element item) throws Exception {
		String id = item.getAttributes().getNamedItem("ItemID").getNodeValue();
		ArrayList<String> categoriesList = new ArrayList<String>();
		ArrayList<Bid> bidsList = new ArrayList<Bid>();

		NodeList nList = item.getChildNodes();
		for(int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			switch (node.getNodeName()) {
				case "Name":
					String name = getNodeText(node);
					break;
				case "Category":
					categoriesList.add(getNodeText(node));
					break;
				case "Currently":
					String currently = getNodeText(node);
					break;
				case "Buy_Price":
					String buyPrice = getNodeText(node);
					break;
				case "First_Bid":
					String firstBid = getNodeText(node);
					break;
				case "Number_of_Bids":
					String numberofBids = getNodeText(node);
					break;
				case "Bids":
					bidsList = getBids(node);
					break;
				case "Location":
					String location = getNodeText(node);
					String latitude = node.getAttribute("Latitude");
					String longitude = node.getAttribute("Longitude");
					break;
				case "Country":
					String country = getNodeText(node);
					break;
				case "Started":
					String started = dateFormat(getNodeText(node));
					break;
				case "Ends":
					String ends = dateFormat(getNodeText(node));
					break;
				case "Seller":
					String sellerId = node.getAttribute("UserID");
					String rating = node.getAttribute("Rating");
				case "Description":
					String description = getNodeText(node);
					if(description.length() > maxDescriptionLength)
						description = description.substring(0, maxDescriptionLength);
					break;
				default:
					break;
			}
		}
		
		Item item = new Item(id, name, currently, buyPrice, firstBid, numberofBids, location,
				 latitude, longitude, country, started, ends, sellerId, description);
		item.setCategories(categoriesList.toArray(new String[categoriesList.size()]));
		Collections.sort(bidsList);
        item.setBids(bidsList.toArray(new Bid[bidsList.size()]));
        return item;
	}
}