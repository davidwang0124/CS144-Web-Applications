CREATE TABLE SpatialItem (
  itemId      INTEGER NOT NULL,
  position    POINT NOT NULL,
  SPATIAL INDEX(position)
) ENGINE=MyISAM;

INSERT INTO SpatialItem
(itemId, position)
SELECT ItemID, POINT(Latitude, Longitude)
FROM Item;
