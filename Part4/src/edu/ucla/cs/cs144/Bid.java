package edu.ucla.cs.cs144;

public class Bid {
	private String bidderId;
	private String rating;
	private String location;
	private String country;
	private String time;
	private String amount;

	public Bid () {}

	public Bid (String id, String rating, String location, String country, String time, String amount) {
		this.bidderId = id;
		this.rating = rating;
		this.location = location;
		this.country = country;
		this.time = time;
		this.amount = amount;
	}

	public int compareTo(Bid other) {
        // Descending order
        return other.getBidTime().compareTo(this.getBidTime());
    }

    public String getBidTime() {
        return time;
    }

    public String getBidAmount() {
        return amount;
    }

    public String getBidderId() {
        return bidderId;
    }

    public String getBidderRating() {
        return rating;
    }

    public String getBidderLocation() {
        return location;
    }

    public String getBidderCountry() {
        return country;
    }
}