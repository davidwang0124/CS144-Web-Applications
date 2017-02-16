package edu.ucla.cs.cs144;
import org.apache.lucene.search.ScoreDoc;


public class ScoreDocComparator {
	private SearchEngine se;

	public void ScoreDocComparator (SearchEngine se) {
		this.se = se;
	}

    public int compare(ScoreDoc doc1, ScoreDoc doc2) {
    	try {
			return Integer.parseInt(se.getDocument(doc1.doc).get("itemID")) - Integer.parseInt(se.getDocument(doc2.doc).get("itemID"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}