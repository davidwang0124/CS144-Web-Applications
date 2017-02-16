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
				PreparedStatement bidsPS = conn.prepareStatement("SELECT * FROM Bid WHERE ItemID = ?");
				bidsPS.setString(1, itemID);
				ResultSet bidsRS = bidsPS.executeQuery();
				while(bidsRS.next()) {
					//xml += "<Category>" + escape(bidsRS.getString("Category")) + "</Category>";
				}

				System.out.println(xml);
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

}