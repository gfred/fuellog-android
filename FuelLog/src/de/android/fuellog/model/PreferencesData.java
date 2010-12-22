package de.android.fuellog.model;

public class PreferencesData {
	private long id;
	private Double firstDistance;
	private Integer currency;
	private Integer unit;
	private String datePattern;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getFirstDistance() {
		return this.firstDistance;
	}

	public void setFirstDistance(Double firstDistance) {
		this.firstDistance = firstDistance;
	}

	public Integer getCurrency() {
		return this.currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public Integer getUnit() {
		return this.unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getDatePattern() {
		return this.datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
}
