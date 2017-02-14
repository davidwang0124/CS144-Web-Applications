package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
    private static final String indexDirectory = "/var/lib/lucene/index1";
    private IndexWriter indexWriter = null;
    
    /** Creates a new instance of Indexer */
    public Indexer() {
    }
 
    private IndexWriter getIndexWriter() throws IOException {
        if (indexWriter == null) {
            Directory indexDir = FSDirectory.open(new File("indexDirectory"));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
            indexWriter = new IndexWriter(indexDir, config);
        }
        return indexWriter;
    }


    public void rebuildIndexes() {

        Connection conn = null;

        // create a connection to the database to retrieve Items from MySQL
    	try {
    	    conn = DbManager.getConnection(true);
    	} catch (SQLException ex) {
    	    System.out.println(ex);
    	}


    	/*
    	 * Add your code here to retrieve Items using the connection
    	 * and add corresponding entries to your Lucene inverted indexes.
         *
         * You will have to use JDBC API to retrieve MySQL data from Java.
         * Read our tutorial on JDBC if you do not know how to use JDBC.
         *
         * You will also have to use Lucene IndexWriter and Document
         * classes to create an index and populate it with Items data.
         * Read our tutorial on Lucene as well if you don't know how.
         *
         * As part of this development, you may want to add 
         * new methods and create additional Java classes. 
         * If you create new classes, make sure that
         * the classes become part of "edu.ucla.cs.cs144" package
         * and place your class source files at src/edu/ucla/cs/cs144/.
    	 * 
    	 */
        try {
            conn = DriverManager.getConnection(true);
            getIndexWriter();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ItemID, Name, Description FROM Item");
            PreparedStatement ps = conn.PreparedStatement("SELECT Category FROM ItemCategory WHERE ItemID = ?");
            while (rs.next()) {
                String itemID = rs.getString("ItemID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                String category = null;
                ps.setString(1, itemID);
                ResultSet result = ps.executeQuery();
                while (result.next()) {
                    category += result.getString("Category") + " ";
                }
                Document doc = new Document();

                doc.add(new StringField("ItemID", itemID, Field.Store.YES));
                doc.add(new StringField("Name", name, Field.Store.YES));
                String searchContent = name + " " + description + " " + category;
                doc.add(new TextField("searchContent", searchContent, Field.Store.NO));
            }
            indexWriter.addDocument(doc);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }


        // close the database connection
    	try {
            if (indexWriter != null) {
                indexWriter.close();
            }
    	    conn.close();
    	} catch (SQLException ex) {
    	    System.out.println(ex);
    	}
    }    

    public static void main(String args[]) {
        Indexer idx = new Indexer();
        idx.rebuildIndexes();
    }   
}
