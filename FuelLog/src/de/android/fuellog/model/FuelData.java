package de.android.fuellog.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class FuelData {

	private Long id;
	private Date date;
	private Double lastDistance;
	private Double currentDistance;
	private Double costs;
	private Double amountCosts;
	private String comment;
	private String location;
	private String picturePath;
	private Long carId;

	public Long getCarId() {
		return this.carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getLastDistance() {
		return this.lastDistance;
	}

	public void setLastDistance(Double lastDistance) {
		this.lastDistance = lastDistance;
	}

	public Double getCurrentDistance() {
		return this.currentDistance;
	}

	public void setCurrentDistance(Double currentDistance) {
		this.currentDistance = currentDistance;
	}

	public Double getCosts() {
		return this.costs;
	}

	public void setCosts(Double costs) {
		this.costs = costs;
	}

	public Double getAmountCosts() {
		return this.amountCosts;
	}

	public void setAmountCosts(Double amountCosts) {
		this.amountCosts = amountCosts;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPicturePath() {
		return this.picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	@Override
	public String toString() {
		Log.d("Fuel Data", (date.getTime() == 1292112000000L) + "???");
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
}
