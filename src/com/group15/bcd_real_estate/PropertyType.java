package com.group15.bcd_real_estate;

public class PropertyType {
    private int propertyID;
    private String propertyType;
    private double price;
    private String address;
    private int sqft;
    private String facilities;
    private String amenities;
    private String contact;
    private String status;
    private String listingDate;
    private int yearBuilt;
    private String seller;

    public PropertyType(int propertyID, String propertyType, double price, String address, int sqft, String facilities, String amenities, String contact, String status, String listingDate, int yearBuilt, String seller) {
        this.propertyID = propertyID;
        this.propertyType = propertyType;
        this.price = price;
        this.address = address;
        this.sqft = sqft;
        this.facilities = facilities;
        this.amenities = amenities;
        this.contact = contact;
        this.status = status;
        this.listingDate = listingDate;
        this.yearBuilt = yearBuilt;
        this.seller = seller;
    }

    public int getPropertyID() {
        return this.propertyID;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public double getPrice() {
        return this.price;
    }

    public String getAddress() {
        return this.address;
    }

    public int getSqft() {
        return this.sqft;
    }

    public String getFacilities() {
        return this.facilities;
    }

    public String getAmenities() {
        return this.amenities;
    }

    public String getContact() {
        return this.contact;
    }

    public String getStatus() {
        return this.status;
    }

    public String getListingDate() {
        return this.listingDate;
    }

    public int getYearBuilt() {
        return this.yearBuilt;
    }

    public String getSeller() {
        return this.seller;
    }

    public String toString() {
        return "Property [propertyID=" + this.propertyID + ", propertyType=" + this.propertyType + ", price=" + this.price + ", address=" + this.address + ", sqft=" + this.sqft + ", facilities=" + this.facilities + ", amenities=" + this.amenities + ", contact=" + this.contact + ", status=" + this.status + ", listingDate=" + this.listingDate + ", yearBuilt=" + this.yearBuilt + "]";
    }
}
