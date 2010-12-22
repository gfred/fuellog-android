package de.android.fuellog.util;

public class UnitConverter {
	private static final double USGALLON_LITER = 3.785411784;
	private static final double LITER_USGALLON = 0.26417205235815;
	private static final double UKGALLON_LITER = 4.54609;
	private static final double LITER_UKGALLON = 0.21996924829909;
	private static final double KM_MILES = 0.6213712;
	private static final double MILES_KM = 1.609344;
	private static final double EURO_DOLLAR = 1.3099;
	private static final double EURO_POUNDS = 0.8512;
	private static final double DOLLAR_EURO = 0.7634;
	private static final double POUNDS_EURO = 1.1748;

	public static double convertLiterInGallonsUS(final double liter) {
		return liter * LITER_USGALLON;
	}

	public static double convertGallonsInLiterUS(final double gallons) {
		return gallons * USGALLON_LITER;
	}

	public static double convertLiterInGallonsUK(final double liter) {
		return liter * LITER_UKGALLON;
	}

	public static double convertGallonsInLiterUK(final double gallons) {
		return gallons * UKGALLON_LITER;
	}

	public static double convertKmInMiles(final double km) {
		return km * KM_MILES;
	}

	public static double convertMilesInKm(final double miles) {
		return miles * MILES_KM;
	}

	public static double convertEuroInDollar(final double euro) {
		return euro * EURO_DOLLAR;
	}

	public static double convertEuroInPounds(final double euro) {
		return euro * EURO_POUNDS;
	}

	public static double convertDollarInEuro(final double dollar) {
		return dollar * DOLLAR_EURO;
	}

	public static double convertPoundsInEuro(final double pounds) {
		return pounds * POUNDS_EURO;
	}
}
