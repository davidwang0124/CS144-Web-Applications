/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class MyParser {

    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;

    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };

    static FileWriter itemWriter;
    static FileWriter itemCategoryWriter;
    static FileWriter bidsWriter;
    static FileWriter userWriter;

    static class MyErrorHandler implements ErrorHandler {

        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }

        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }

        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
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

    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals("") || money.equals("NULL"))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                e.printStackTrace();
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }

    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }

        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);

        /* Fill in code here (you will probably need to write auxiliary
            methods). */

        writeTableFiles(doc.getDocumentElement());

        /**************************************************************/

    }

    public static void writeTableFiles(Element ele) {
        // creating files
        itemWriter = createCSVFile("item.csv");
        itemCategoryWriter = createCSVFile("itemCategory.csv");
        bidsWriter = createCSVFile("bids.csv");
        userWriter = createCSVFile("user.csv");
        System.out.println("Successfully created csv files");
        // get all Item tags
        Element[] itemList = getElementsByTagNameNR(ele, "Item");
        System.out.println("There are " + itemList.length + "<Item> tags");
        // process each Item
        for (Element item : itemList) {
            writeItem(item);
        }
        // close open files
        closeCSVFile(itemWriter);
        closeCSVFile(itemCategoryWriter);
        closeCSVFile(bidsWriter);
        closeCSVFile(userWriter);
    }
    static void writeItem(Element ele) {
        // write user
        StringBuilder sellerBuilder = new StringBuilder();
        Element sellerElement = getElementByTagNameNR(ele, "Seller");
        String sellerID = sellerElement.getAttribute("UserID");
        sellerBuilder.append(sellerID).append(" -|- ");
        String rating = sellerElement.getAttribute("Rating");
        sellerBuilder.append(rating).append(" -|- ");
        sellerBuilder.append("NULL -|- NULL");
        writeTuple(userWriter, sellerBuilder.toString());

        // write Item table
        StringBuilder itemTupleBuilder = new StringBuilder();

        String itemID = ele.getAttribute("ItemID");
        itemTupleBuilder.append(itemID).append(" -|- ");

        String name = getElementTextByTagNameNR(ele, "Name");
        itemTupleBuilder.append(name).append(" -|- ");

        String currently = strip(getElementTextByTagNameNR(ele, "Currently"));
        itemTupleBuilder.append(currently).append(" -|- ");

        String buyPrice = strip(getOptionalElementTextByTagNameNR(ele, "Buy_Price"));
        itemTupleBuilder.append(buyPrice).append(" -|- ");

        String firstBid = strip(getElementTextByTagNameNR(ele, "First_Bid"));
        itemTupleBuilder.append(firstBid).append(" -|- ");

        String numberOfBids = getElementTextByTagNameNR(ele, "Number_of_Bids");
        itemTupleBuilder.append(numberOfBids).append(" -|- ");

        Element locationElement = getElementByTagNameNR(ele, "Location");

        String locationName = getElementText(locationElement);
        itemTupleBuilder.append(locationName).append(" -|- ");

        String latitude = locationElement.getAttribute("Latitude");
        itemTupleBuilder.append(latitude).append(" -|- ");

        String longtitude = locationElement.getAttribute("Longtitude");
        itemTupleBuilder.append(longtitude).append(" -|- ");

        String country = getElementTextByTagNameNR(ele, "Country");
        itemTupleBuilder.append(country).append(" -|- ");

        String started = convertToSQLTime(getElementTextByTagNameNR(ele, "Started"));
        itemTupleBuilder.append(started).append(" -|- ");
        String ends = convertToSQLTime(getElementTextByTagNameNR(ele, "Ends"));
        itemTupleBuilder.append(ends).append(" -|- ");

        itemTupleBuilder.append(sellerID).append(" -|- ");

        String description = getElementTextByTagNameNR(ele, "Description");
        description = description.substring(0, Math.min(description.length(), 4000));
        itemTupleBuilder.append(description);

        writeTuple(itemWriter, itemTupleBuilder.toString());

        // write itemCategory table
        Element[] categories = getElementsByTagNameNR(ele, "Category");
        for (Element categoryElem : categories) {
            String category = getElementText(categoryElem);
            String itemCategoryTuple = itemID + " -|- " + category;
            writeTuple(itemCategoryWriter, itemCategoryTuple);
        }

        // write bids
        Element bidsElement = getElementByTagNameNR(ele, "Bids");
        Element[] bidElements = getElementsByTagNameNR(bidsElement, "Bid");
        for (Element bid : bidElements) {
            StringBuilder bidBuilder = new StringBuilder();

            Element bidderElement = getElementByTagNameNR(bid, "Bidder");
            writeBidderTuple(bidderElement);

            String bidUserID = bidderElement.getAttribute("UserID");
            bidBuilder.append(bidUserID).append(" -|- ");

            String time = convertToSQLTime(getElementTextByTagNameNR(bid, "Time"));
            bidBuilder.append(time).append(" -|- ");

            String amount = strip(getElementTextByTagNameNR(bid, "Amount"));
            bidBuilder.append(amount);

            writeTuple(bidsWriter, bidBuilder.toString());
        }
    }
    static void writeBidderTuple(Element bidderElem) {
        StringBuilder bidderBuilder = new StringBuilder();
        String userID = bidderElem.getAttribute("UserID");
        bidderBuilder.append(userID).append(" -|- ");
        String rating = bidderElem.getAttribute("Rating");
        bidderBuilder.append(rating).append(" -|- ");
        String location = getElementTextByTagNameNR(bidderElem, "Location");
        bidderBuilder.append(location).append(" -|- ");
        String country = getElementTextByTagNameNR(bidderElem, "Country");
        bidderBuilder.append(country);
        writeTuple(userWriter, bidderBuilder.toString());
    }
    static FileWriter createCSVFile(String fileName) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        return writer;
    }
    static void closeCSVFile(FileWriter w) {
        try {
            w.flush();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void writeTuple(FileWriter w, String line) {
        try {
            w.append(line + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "NULL" is returned. NR means Non-Recursive.
     */
    static String getOptionalElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null && elem.getChildNodes().getLength() == 1) {
            Text elementText = (Text) elem.getFirstChild();
            return elementText.getNodeValue();
        } else {
            return "NULL";
        }
    }
    /** Return SQL TIMESTAMP data
     * 'Dec-15-01 14:13:37' => '2001-12-15 14:13:37'
     */
    static String convertToSQLTime(String xmlTime) {
        SimpleDateFormat xmlFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ts = "";
        try {
            Date inputDate = xmlFormat.parse(xmlTime);
            ts = sqlFormat.format(inputDate);
        } catch(ParseException pe) {
            System.out.println("ERROR: could not parse \"" + xmlTime + "\"");
            System.exit(3);
        }
        return ts;
    }

    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }

        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        }
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }

        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
