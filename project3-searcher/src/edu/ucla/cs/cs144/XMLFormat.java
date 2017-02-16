package edu.ucla.cs.cs144;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XMLFormat {
	private String itemID;
	private String xml;

	public XMLFormat (String itemID) {
		this.itemID = itemID;
		this.xml = "";
	}

	public String convert() {
		Connection conn = null;
		try {
    	    conn = DbManager.getConnection(true);
    	} catch (SQLException ex) {
    	    System.out.println(ex);
    	}

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Item WHERE ItemID = ?");
			ps.setString(1, itemID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				// item
				xml += "<Item itemID =" + itemID + ">\n";

				// name
				xml += "<Name>" + escape(rs.getString("Name")) + "</Name>\n";

				// Category
				PreparedStatement categoryPS = conn.prepareStatement("SELECT * FROM ItemCategory WHERE ItemID = ?");
				categoryPS.setString(1, itemID);
				ResultSet categoryRS = categoryPS.executeQuery();
				while(categoryRS.next()) {
					xml += "<Category>" + escape(categoryRS.getString("Category")) + "</Category>\n";
				}

				// Currently
				xml += "<Currently>$" + escape(rs.getString("Currently")) + "</Currently>\n";

				// Fisrt_Bid
				xml += "<First_Bid>$" + escape(rs.getString("FirstBid")) + "</First_Bid>\n";

				// Number_of_Bids
				xml += "<Number_of_Bids>" + rs.getString("NumberofBids") + "</Number_of_Bids>\n";

				// Bids
				// PreparedStatement bidsPS = conn.prepareStatement("SELECT * FROM Bid WHERE ItemID = ?");
				// bidsPS.setString(1, itemID);
				// ResultSet bidsRS = bidsPS.executeQuery();
				// while(bidsRS.next()) {
				// 	//xml += "<Category>" + escape(bidsRS.getString("Category")) + "</Category>";
				// }

				// Location
				if(rs.getString("Latitude").equals("0.000000")) {
					xml += "<Location>" + escape(rs.getString("Location")) + "</Location>\n";
				} else {
					xml += "<Location Latitude=\"" + rs.getString("Latitude") + "\" Longitude=\""
						 + rs.getString("Longitude") + "\">" + escape(rs.getString("Location")) + "</Location>\n";
				}
				
				// Country
				xml += "<Country>" + escape(rs.getString("Country")) + "</Country>\n";

				// Started
				xml += "<Started>" + escape(dateFormat(rs.getString("Started"))) + "</Started>\n";

				// Ends
				xml += "<Ends>" + escape(dateFormat(rs.getString("Ends"))) + "</Ends>\n";

				// Seller
				String sellerID = escape(rs.getString("UserID"));
				PreparedStatement sellerPS = conn.prepareStatement("SELECT * FROM Seller WHERE UserID = ?");
				sellerPS.setString(1, sellerID);
				ResultSet sellerRS = sellerPS.executeQuery();
				if(sellerRS.next()) {
					xml += "<Seller Rating=\"" + escape(sellerRS.getString("Rating")) + "\" UserID=\"" 
						+ sellerID + "\" />\n";
				}

				// Description
				xml += "<Description>" + escape(rs.getString("Description")) + "</Description>\n";
				xml += "</Item>";
			}
		} catch (Exception e) {
			System.out.println(e);
		}


		try {
    	    conn.close();
    	} catch (SQLException ex) {
    	    System.out.println(ex);
    	}
		return xml;
	}

	private String escape(String input) {
		if(input == null)	return null;
		String result = input.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("\'", "&apos;");
		return result;
	}

	private String dateFormat(String input) {
		SimpleDateFormat xmlFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ts = "";
        try {
            Date inputDate = sqlFormat.parse(input);
            ts = xmlFormat.format(inputDate);
        } catch(ParseException pe) {
            System.out.println("ERROR: could not parse \"" + input + "\"");
            System.exit(3);
        }
        return ts;
	}

}