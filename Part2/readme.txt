## Part B: Design your relational schema
> 1. List your relations. Please specify all keys that hold on each relation. You need not specify attribute types at this stage.

Relations:
- Item (__ItemId__, Name, Currently, Buy\_Price, First\_Bid, Number\_of\_Bids, Location.Name, Country, Started, Ends, Seller.UserID, Description)
- ItemCategory (ItemID, Category)
- Bids (ItemId, Bidder.UserID, Bidder.Time)
- Bid (__Bidder.UserID__, __Time__, Amount)
- Bidder (__UserID__, Rating, Location.Name, Country)
- Location (__Name__, Latitude, Longtitude)
- Seller (__UserID__, Rating)

> 2. List all completely nontrivial functional dependencies that hold on each relation, excluding those that effectively specify keys.

- Item: `ItemId -> Name, ItemId -> Currently, ItemId -> Buy_Price, ItemId -> First_Bid, ItemId -> Number_of_Bids, ItemId -> Location.Name, ItemId -> Country, ItemId -> Started, ItemId -> Ends, ItemId -> Seller.UserID, ItemId -> Description; Location.Name -> Country`
- Bid: `Bidder.UserID, Time -> Amount`
- Bidder: `UserID -> Rating, UserID -> Location.Name, UserID -> Country; Location.Name -> Country`
- Location: `Name -> Latitude, Name -> Longtitude`
- Seller: `UserID -> Rating`

> 3. Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-BCNF relations.

For relation "Item" and "Bidder", `Location.Name -> Country`. We decide to leave it as in the real datasets, name of location is not neccessarily a "real" location and Country is usually more useful.

> 4. Are all of your relations in Fourth Normal Form (4NF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-4NF relations.

