package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

public class AuctionSearch implements IAuctionSearch {

	/*
     * You will probably have to use JDBC to access MySQL data
     * Lucene IndexSearcher class to lookup Lucene index.
     * Read the corresponding tutorial to learn about how to use these.
     *
	 * You may create helper functions or classes to simplify writing these
	 * methods. Make sure that your helper functions are not public,
     * so that they are not exposed to outside of this class.
     *
     * Any new classes that you create should be part of
     * edu.ucla.cs.cs144 package and their source files should be
     * placed at src/edu/ucla/cs/cs144.
     *
     */

	public SearchResult[] basicSearch(String query, int numResultsToSkip,
			int numResultsToReturn) {
		// TODO: Your code here!
		ArrayList<SearchResult> sr = new ArrayList<SearchResult>();
		try {
			int total = numResultsToSkip + numResultsToReturn;
			SearchEngine se = new SearchEngine();
			TopDocs td = se.performSearch(query, total);
			ScoreDoc[] results = td.scoreDocs;
			for(int i = numResultsToSkip; i < total; i++) {
				Document doc = se.getDocument(results[i].doc);
				sr.add(new SearchResult(doc.get("itemID"), doc.get("name")));
			}
			return sr.toArray(new SearchResult[sr.size()]);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new SearchResult[0];
		}
	}

	public SearchResult[] spatialSearch(String query, SearchRegion region,
			int numResultsToSkip, int numResultsToReturn) {
		// TODO: Your code here!
		ArrayList<SearchResult> sr = new ArrayList<SearchResult>();
		try {
			int total = numResultsToSkip + numResultsToReturn;
			// get sorted items fron lucene
			SearchEngine se = new SearchEngine();
			Sort sort = new Sort(new SortField("itemID", SortField.Type.LONG));
			TopDocs td = se.performSearchWithSort(query, total, sort);
			ScoreDoc[] results = td.scoreDocs;
			// get sorted items from mysql spatial index
			try {
				Connection conn = DbManager.getConnection(true);
				// create the string for a MySQL geometric polygon for parameter region
				String polygon = getMySQLPolygon(region.getLx(), region.getLy(), region.getRx(), region.getRy());
				// prepared statement to test a specific item (by ItemID) for spatial containment in polygon region
				PreparedStatement checkContains = conn.prepareStatement(
					"SELECT itemId FROM SpatialItem WHERE " +
					"" +
					""
				);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			// intersect
			for(int i = numResultsToSkip; i < total; i++) {
				Document doc = se.getDocument(results[i].doc);
				sr.add(new SearchResult(doc.get("itemID"), doc.get("name")));
			}
			return sr.toArray(new SearchResult[sr.size()]);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new SearchResult[0];
		}
	}

	public String getXMLDataForItemId(String itemId) {
		// TODO: Your code here!
		return "";
	}

	public String echo(String message) {
		return message;
	}
	public String getMySQLPolygon(double lx, double ly, double rx, double ry) {
		return "GeomFromText('Polygon((" + lx + " " + ly + ", " + lx + " " + ry + ", " + rx + " " + ry + ", " + rx + " " + ly + ", " + lx + " " + ly +  "))')";
	}

}
