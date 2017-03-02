package edu.ucla.cs.cs144;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	private static ArrayList<Bid> getBids(Node node) {
		ArrayList<Bid> bidsList = new ArrayList<Bid>();
		NodeList bids = node.getChildNodes();
		for(int i = 0; i < bids.getLength(); i++) {
			Node n = bids.item(i);
			String bidderId = n.getAttributes().getNamedItem("UserID").getNodeValue();
			String rating = n.getAttributes().getNamedItem("Rating").getNodeValue();
			String location = "", country = "", time = "", amount = "";
			switch (n.getNodeName()) {
				case "Bidder":
					location = getNodeText(n.getFirstChild());
					country = getNodeText(n.getLastChild());
					break;
				case "Time":
					time = dateFormat(getNodeText(n));
					break;
				case "Amount":
					amount = getNodeText(n);
					break;
				default:
					break;
			}
			bidsList.add(new Bid(bidderId, rating, location, country, time, amount));
		}
		return bidsList;
	}

	private static String getNodeText(Node node) {
		if(node.getChildNodes().getLength() == 1) {
			return node.getFirstChild().getNodeValue();
		} else {
			return "";
		}
	}


	public static Item parseXML(Element item) throws Exception {
		String id = item.getAttributes().getNamedItem("ItemID").getNodeValue();
		ArrayList<String> categoriesList = new ArrayList<String>();
		ArrayList<Bid> bidsList = new ArrayList<Bid>();
		String name = "", currently = "", buyPrice = "", firstBid = "",
			numberofBids = "", location = "", latitude = "", longitude = "", country = "", 
			started = "", ends = "", sellerId = "", rating = "", description = "";
		NodeList nList = item.getChildNodes();
		for(int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			switch (node.getNodeName()) {
				case "Name":
					name = getNodeText(node);
					break;
				case "Category":
					categoriesList.add(getNodeText(node));
					break;
				case "Currently":
					currently = getNodeText(node);
					break;
				case "Buy_Price":
					buyPrice = getNodeText(node);
					break;
				case "First_Bid":
					firstBid = getNodeText(node);
					break;
				case "Number_of_Bids":
					numberofBids = getNodeText(node);
					break;
				case "Bids":
					bidsList = getBids(node);
					break;
				case "Location":
					location = getNodeText(node);
					latitude = node.getAttributes().getNamedItem("Latitude").getNodeValue();
					longitude = node.getAttributes().getNamedItem("Longitude").getNodeValue();
					break;
				case "Country":
					country = getNodeText(node);
					break;
				case "Started":
					started = dateFormat(getNodeText(node));
					break;
				case "Ends":
					ends = dateFormat(getNodeText(node));
					break;
				case "Seller":
					sellerId = node.getAttributes().getNamedItem("UserID").getNodeValue();
					rating = node.getAttributes().getNamedItem("Rating").getNodeValue();
				case "Description":
					description = getNodeText(node);
					if(description.length() > maxDescriptionLength)
						description = description.substring(0, maxDescriptionLength);
					break;
				default:
					break;
			}
		}
		
		Item itemInstance = new Item(id, name, currently, buyPrice, firstBid, numberofBids, location,
				 latitude, longitude, country, started, ends, sellerId, description);
		itemInstance.setCategories(categoriesList.toArray(new String[categoriesList.size()]));
		//Collections.sort(bidsList);
        itemInstance.setBids(bidsList.toArray(new Bid[bidsList.size()]));
        return itemInstance;
	}
}