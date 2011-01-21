package com.arconsis.prototype.driversentry.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.location.Location;

public class DriversLogEntry {

	private Date date;
	private List<File> pictures = new ArrayList<File>();
	private Location bookingLocation;
	private Location currentLoctaion;
	private String bookingLocationDesc;
	private Double mileage;
	private String description;

	public DriversLogEntry() {
		this.date = new Date();
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<File> getPictures() {
		return this.pictures;
	}

	public void setPictures(List<File> pictures) {
		this.pictures = pictures;
	}

	public Location getBookingLocation() {
		return this.bookingLocation;
	}

	public Location getCurrentLoctaion() {
		return this.currentLoctaion;
	}

	public void setBookingLocation(Location bookingLocation) {
		this.bookingLocation = bookingLocation;
	}

	public void setCurrentLoctaion(Location currentLoctaion) {
		this.currentLoctaion = currentLoctaion;
	}

	public Double getMileage() {
		return this.mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBookingLocationDesc() {
		return this.bookingLocationDesc;
	}

	public void setBookingLocationDesc(String bookingLocationDesc) {
		this.bookingLocationDesc = bookingLocationDesc;
	}
}
