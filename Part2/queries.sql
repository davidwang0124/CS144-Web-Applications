-- Find the number of users in the database.
SELECT COUNT(*) FROM
(
  SELECT UserID FROM Seller
  UNION
  SELECT UserID FROM Bidder
)

-- Find the number of items in "New York", (i.e., items whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the items in "New York" but not in "new york".
SELECT COUNT(*)
FROM Item
WHERE Location LIKE 'New York';


-- Find the number of auctions belonging to exactly four categories.

-- Find the ID(s) of current (unsold) auction(s) with the highest bid. Remember that the data was captured at the point in time December 20th, 2001, one second after midnight, so you can use this time point to decide which auction(s) are current. Pay special attention to the current auctions without any bid.

-- Find the number of sellers whose rating is higher than 1000.

-- Find the number of users who are both sellers and bidders.

-- Find the number of categories that include at least one item with a bid of more than $100.