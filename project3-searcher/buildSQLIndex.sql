CREATE TABLE SpatialItem (
  itemId      INTEGER NOT NULL,
  position    POINT
) ENGINE = MyISAM;

INSERT INTO SpatialItem
(itemId, position)
SELECT ItemID, POINT(Latitude, Longtitude)
FROM Item
