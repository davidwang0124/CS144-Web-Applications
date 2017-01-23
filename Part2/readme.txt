## Part B: Design your relational schema
> 1. List your relations. Please specify all keys that hold on each relation. You need not specify attribute types at this stage.
Relations:
- Item (__ItemId__, Name, Category, Currently, Buy\_Price, First\_Bid, Number\_of\_Bids, Location.name, Country, Started, Ends, Seller.UserID, Description)
- Bids (ItemId, Bidder.UserID, Bidder.Time)
- Bid (__Bidder.UserID__, __Time__, Amount)
- Bidder (__UserID__, Rating, Location.name, Country)
- Location (__name__, Latitude, Longtitude)
- Seller (__UserID__, Rating)

> 2. List all completely nontrivial functional dependencies that hold on each relation, excluding those that effectively specify keys.

> 3. Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-BCNF relations.

> 4. Are all of your relations in Fourth Normal Form (4NF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-4NF relations.

