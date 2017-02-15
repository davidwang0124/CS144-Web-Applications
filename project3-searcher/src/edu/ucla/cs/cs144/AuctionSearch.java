package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.text.SimpleDateFormat;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
			for(int i = numResultsToSkip; i < (total > results.length ? results.length : total); i++) {
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
			TopDocs td = se.performSearch(query, 2 * total);
			ScoreDoc[] queryResults = td.scoreDocs;
			ScoreDoc lastResult = queryResults[queryResults.length - 1];
			Arrays.sort(queryResults, new Comparator<ScoreDoc>() {
				public int compare(ScoreDoc doc1, ScoreDoc doc2) {
					return doc1.doc.get("itemID") - doc2.doc.get("itemID");
				}
			});

			// get sorted items from mysql spatial index
			Connection conn = DbManager.getConnection(true);
			String polygon = getMySQLPolygon(region.getLx(), region.getLy(), region.getRx(), region.getRy());
			PreparedStatement sortedItemsSpatial = conn.prepareStatement(
				"SELECT itemId FROM SpatialItem WHERE " +
				"MBRContains(" + polygon + ", position)" +
				"ORDER BY itemId;"
			);
			ResultSet regionResults = sortedItemsSpatial.executeQuery();

			// intersect
			int queryIdx = 0, i = 0;
			boolean regionExists = regionResults.next();
			Document doc = null;
			while (i < total) {
				while (queryIdx < 2*total && regionExists) {
					doc = se.getDocument(queryResults[queryIdx].doc);
					int queryId = Integer.parseInt(doc.get("itemID"));
					int regionId = regionResults.getInt("itemId");
					if (queryId < regionId) {
						queryIdx++;
					} else if (queryId > regionId) {
						regionExists = regionResults.next();
					} else {
						break;
					}
				}
				// check state
				if (!regionExists) {
					break;
				}
				if (queryIdx >= 2*total) {
					// query again from lucene
					td = se.performSearchAfter(lastResult, query, 2 * total);
					continue;
				}
				// add if there is still vacancy
				if (i >= numResultsToSkip) {
					sr.add(new SearchResult(doc.get("itemID"), doc.get("name")));
					i++;
				}
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
