## Part B: Design your relational schema
> 1. List your relations. Please specify all keys that hold on each relation. You need not specify attribute types at this stage.

Relations:
- Item (__ItemID__, Name, Currently, Buy\_Price, First\_Bid, Number\_of\_Bids, Location.Name, Country, Started, Ends, Seller.UserID, Description)
- ItemCategory (ItemID, Category)
- Bids (ItemID, Bidder.UserID, Bidder.Time)
- Bid (__Bidder.UserID__, __Time__, Amount)
- Bidder (__UserID__, Rating, Location.Name, Country)
- Location (__Name__, Latitude, Longtitude)
- Seller (__UserID__, Rating)

> 2. List all completely nontrivial functional dependencies that hold on each relation, excluding those that effectively specify keys.

- Item: `ItemID -> Name, ItemID -> Currently, ItemID -> Buy_Price, ItemID -> First_Bid, ItemID -> Number_of_Bids, ItemID -> Location.Name, ItemID -> Country, ItemID -> Started, ItemID -> Ends, ItemID -> Seller.UserID, ItemID -> Description; Location.Name -> Country`
- Bid: `Bidder.UserID, Time -> Amount`
- Bidder: `UserID -> Rating, UserID -> Location.Name, UserID -> Country; Location.Name -> Country`
- Location: `Name -> Latitude, Name -> Longtitude`
- Seller: `UserID -> Rating`

> 3. Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-BCNF relations.

For relation "Item" and "Bidder", `Location.Name -> Country`. We decide to leave it as in the real datasets, name of location is not neccessarily a "real" location and Country is usually more useful.

> 4. Are all of your relations in Fourth Normal Form (4NF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-4NF relations.

No, After careful consideration, we think there are multivalued dependency for table bids and bid. For instance, there are two transaction for table bids. 1. ItemID: 1, UserID: 10, Time:11:50.  2. ItemID:1, UserID:10, Time: 12:20. As a result, there will be multivaluted dependency between ItemID and UserID. So we decide to redesign the relations as follow:
- Item (__ItemID__, Name, Currently, Buy\_Price, First\_Bid, Number\_of\_Bids, 
		Location, Latitude, Longtitude, Country, Started, Ends, UserID, Description)
- ItemCategory (__ItemID__,__Category__)
- Bids (__UserID__,__Time__, ItemID, Amount)
- User (__UserID__, Rating, Location)
