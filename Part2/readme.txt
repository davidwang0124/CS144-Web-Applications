## Part B: Design your relational schema
> 1. List your relations. Please specify all keys that hold on each relation. You need not specify attribute types at this stage.

Relations:
- Item (__ItemID__, Name, Currently, Buy\_Price, First\_Bid, Number\_of\_Bids, 
		Location, Latitude, Longtitude, Country, Started, Ends, UserID, Description)
- ItemCategory (__ItemID__,__Category__)
- Bids (__UserID__,__Time__, ItemID, Amount)
- User (__UserID__, Rating, Location, Country)

> 2. List all completely nontrivial functional dependencies that hold on each relation, excluding those that effectively specify keys.

- Item: `ItemID -> Name, ItemID -> Currently, ItemID -> Buy_Price, ItemID -> First_Bid, ItemID -> Number_of_Bids, ItemID -> Location, ItemID -> Latitude, ItemID -> Longtitude, ItemID -> Country, ItemID -> Started, ItemID -> Ends, ItemID -> UserID, ItemID -> Description; Location -> Country`
- Bids: `UserID, Time -> ItemID, UserID, Time -> Amount`
- User: `UserID -> Rating, UserID -> Location, UserID -> Country, Location -> Country`

> 3. Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-BCNF relations.

For relation "Item" and "User", `Location -> Country`. We decide to leave it as in the real datasets, name of location is not neccessarily a "real" location and Country is usually more useful.

> 4. Are all of your relations in Fourth Normal Form (4NF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-4NF relations.

Yes. 