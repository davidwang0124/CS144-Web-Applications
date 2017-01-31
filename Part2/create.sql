-- - Item (__ItemID__, Name, Currently, Buy\_Price, First\_Bid, Number\_of\_Bids,
-- 		Location, Latitude, Longtitude, Country, Started, Ends, UserID, Description)
-- - ItemCategory (__ItemID__,__Category__)
-- - Bid (__UserID__,__Time__, ItemID, Amount)
-- - User (__UserID__, Seller_Rating, Buyer_Rating, Location, Country)

create table Item (
	ItemID 	INTEGER NOT NULL,
	Name	VARCHAR() NOT NULL,
	Currently	DECIMAL(8,2) NOT NULL,
	Buy_Price	DECIMAL(8,2),
	First_Bid	DECIMAL(8,2) NOT NULL,
	Number_of_Bids	INTEGER,
	Location	VARCHAR(50) NOT NULL,
	Latitude	DECIMAL(9,6) NOT NULL,
	Longtitude	DECIMAL(9,6) NOT NULL,
	Country		VARCHAR(50)	NOT NULL,
	Started		TIMESTAMP NOT NULL,
	Ends		TIMESTAMP NOT NULL,
	UserID 		INTEGER NOT NULL,
	Description	VARCHAR(4000),
	PRIMARY KEY (ItemID)
);

create table ItemCategory (
	ItemID 	INTEGER NOT NULL,
	Category VARCHAR(60) NOT NULL,
	PRIMARY KEY (ItemID, Category)
);

create table Bid (
	UserID 	INTEGER NOT NULL,
	Time 	TIMESTAMP NOT NULL,
	ItemID 	INTEGER NOT NULL,
	Amount 	DECIMAL(8,2) NOT NULL,
	PRIMARY KEY (UserID, Time),
	FOREIGN KEY (ItemID) REFERENCES Item(ItemID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

create table User (
	UserID INTEGER NOT NULL,
	Seller_Rating	INTEGER, 
	Buyer_Rating 	INTEGER,
	Location 	VARCHAR(50) NOT NULL,
	Country		VARCHAR(50) NOT NULL
	PRIMARY KEY (UserID)
);