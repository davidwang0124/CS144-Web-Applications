package edu.ucla.cs.cs144;

public class Item {

	private String id;
	private String name;
	private String currently;
	private String buyPrice;
	private String firstBid;
	private String numberofBids;
	private String location;
	private String latitude;
	private String longitude;
	private String country;
	private String started;
	private String ends;
	private String sellerId;
	private String description;
	private String[] categories;
	private Bid[] bids;

	public Item() {}

	public Item(String id, String name, String currently, String buyPrice, String firstBid, String numberofBids,
			String location, String latitude, String longitude, String country, String started, String ends, String sellerId, String description) {
		this.id = id;
		this.name = name;
		this.currently = currently;
		this.buyPrice = buyPrice;
		this.firstBid = firstBid;
		this.numberofBids = numberofBids;
		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;
		this.country = country;
		this.started = started;
		this.ends = ends;
		this.sellerId = sellerId;
		this.description = description;

	}

	public void setCategories (String[] categories) {
        this.categories = categories;
    }

    public void setBids(BidResult[] bids) {
        this.bids = bids;
    }

    public String getItemId() {
        return id;
    }

    public String getItemName() {
        return name;
    }

    public String getItemCurrently() {
        return currently;
    }

    public String getItemBuyPrice() {
        return buyPrice;
    }

    public String getItemFirstBid() {
        return firstBid;
    }

    public String getItemNumberOfBids() {
        return numberofBids;
    }

    public String getItemLocation() {
        return location;
    }

    public String getItemLatitude() {
        return latitude;
    }

    public String getItemLongitude() {
        return longitude;
    }

	public String getItemCountry() {
        return country;
    }

    public String getItemStarted() {
        return started;
    }

    public String getItemEnds() {
        return ends;
    }

    public String getItemSellerId() {
        return sellerId;
    }

    public String getItemDescription() {
        return description;
    }

    public String[] getItemCategories() {
        return categories;
    }

    public Bid[] getItemBids() {
        return bids;
    }
}