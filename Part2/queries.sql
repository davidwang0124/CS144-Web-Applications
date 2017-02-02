-- Find the number of users in the database.
SELECT Count(*) FROM
(
  SELECT UserID FROM Seller
  UNION
  SELECT UserID FROM Bidder
)
-- Find the number of items in "New York", (i.e., items whose location is exactly the string "New York"). Pay special attention to case sensitivity. You should match the items in "New York" but not in "new york".
 select count(*) from Item where Location = "New York" COLLATE Latin1_General_CS_AS;
-- Find the number of auctions belonging to exactly four categories.
 select count(*) from (select ItemID from ItemCategory group by ItemID having count(Category) = 4) T;
-- Find the ID(s) of current (unsold) auction(s) with the highest bid. Remember that the data was captured at the point in time December 20th, 2001, one second after midnight, so you can use this time point to decide which auction(s) are current. Pay special attention to the current auctions without any bid.
 select ItemID from Item where Currently = 
 	(select MAX(Currently) from Item where Ends > "Dec-20-01 00:00:01" and Started < "Dec-20-01 00:00:00" and NumberofBids > 0)
 and Ends > "Dec-20-01 00:00:01" and Started < "Dec-20-01 00:00:00" and NumberofBids > 0;
-- Find the number of sellers whose rating is higher than 1000.
 select count(UserID) from Seller where Rating > 1000;
-- Find the number of users who are both sellers and bidders.
 select count(UserID) from Seller, Bidder where Seller.UserID = Bidder.UserID;
-- Find the number of categories that include at least one item with a bid of more than $100.
 select count(*) from Bid, ItemCategory where Bid.ItemID = ItemCategory.ItemID and Bid.Amount > 100 group by ItemCategory.Category;