-- Find the number of users in the database.
SELECT count(*)
FROM (SELECT UserID FROM Seller
      UNION
      SELECT UserID FROM Bidder) U;

-- Find the number of items in "New York", (i.e., items whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the items in "New York" but not in "new york".
SELECT count(*)
FROM Item
WHERE Location = "New York" COLLATE utf8_bin;

-- Find the number of auctions belonging to exactly four categories.
SELECT count(*)
FROM (SELECT ItemID
    FROM ItemCategory
    GROUP BY ItemID
    HAVING count(Category) = 4) T;

-- Find the ID(s) of current (unsold) auction(s) with the highest bid. Remember that the data was captured at the point in time December 20th, 2001, one second after midnight, so you can use this time point to decide which auction(s) are current. Pay special attention to the current auctions without any bid.
SELECT ItemID
FROM Item
WHERE Currently = (SELECT MAX(Currently)
           FROM Item
           WHERE Ends > "2001-12-20 00:00:01"
           AND Started < "2001-12-20 00:00:00"
           AND NumberofBids > 0)
AND Ends > "2001-12-20 00:00:01"
AND Started < "2001-12-20 00:00:00"
AND NumberofBids > 0;

-- Find the number of sellers whose rating is higher than 1000.
SELECT count(*)
FROM Seller
WHERE Rating > 1000;

-- Find the number of users who are both sellers and bidders.
SELECT count(*)
FROM Seller
WHERE UserID IN (SELECT UserID FROM Bid);

-- Find the number of categories that include at least one item with a bid of more than $100.
SELECT count(*)
FROM (SELECT ItemCategory.Category FROM Bid, ItemCategory
	  WHERE Bid.ItemID = ItemCategory.ItemID
	  AND Bid.Amount > 100
  	  GROUP BY ItemCategory.Category) T;



