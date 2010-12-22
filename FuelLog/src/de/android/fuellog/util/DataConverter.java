package de.android.fuellog.util;

import de.android.fuellog.model.FuelData;

public class DataConverter implements Values {

	public static FuelData getFuelDataForUi(FuelData data, final int pref) {
		switch (pref) {
		case DOLLAR_LITER_KM:
		case DOLLAR_LITER_MILES:
		case DOLLAR_UKGALLONS_KM:
		case DOLLAR_UKGALLONS_MILES:
		case DOLLAR_USGALLONS_KM:
		case DOLLAR_USGALLONS_MILES:
			data.setAmountCosts(UnitConverter.convertEuroInDollar(data.getAmountCosts()));
			data.setCosts(UnitConverter.convertEuroInDollar(data.getCosts()));
			break;
		case POUNDS_LITER_KM:
		case POUNDS_LITER_MILES:
		case POUNDS_UKGALLONS_KM:
		case POUNDS_UKGALLONS_MILES:
		case POUNDS_USGALLONS_KM:
		case POUNDS_USGALLONS_MILES:
			data.setAmountCosts(UnitConverter.convertEuroInPounds(data.getAmountCosts()));
			data.setCosts(UnitConverter.convertEuroInPounds(data.getCosts()));
			break;
		}

		switch (pref) {
		case DOLLAR_LITER_MILES:
		case DOLLAR_UKGALLONS_MILES:
		case DOLLAR_USGALLONS_MILES:
		case EURO_LITER_MILES:
		case EURO_UKGALLONS_MILES:
		case EURO_USGALLONS_MILES:
		case POUNDS_LITER_MILES:
		case POUNDS_UKGALLONS_MILES:
		case POUNDS_USGALLONS_MILES:
			data.setCurrentDistance(UnitConverter.convertKmInMiles(data.getCurrentDistance()));
			data.setLastDistance(UnitConverter.convertKmInMiles(data.getLastDistance()));
			break;
		}

		return data;
	}

	public static FuelData getFuelDataForStorage(FuelData data, int pref) {
		switch (pref) {
		case DOLLAR_LITER_KM:
		case DOLLAR_LITER_MILES:
		case DOLLAR_UKGALLONS_KM:
		case DOLLAR_UKGALLONS_MILES:
		case DOLLAR_USGALLONS_KM:
		case DOLLAR_USGALLONS_MILES:
			data.setAmountCosts(UnitConverter.convertDollarInEuro(data.getAmountCosts()));
			data.setCosts(UnitConverter.convertDollarInEuro(data.getCosts()));
			break;
		case POUNDS_LITER_KM:
		case POUNDS_LITER_MILES:
		case POUNDS_UKGALLONS_KM:
		case POUNDS_UKGALLONS_MILES:
		case POUNDS_USGALLONS_KM:
		case POUNDS_USGALLONS_MILES:
			data.setAmountCosts(UnitConverter.convertPoundsInEuro(data.getAmountCosts()));
			data.setCosts(UnitConverter.convertPoundsInEuro(data.getCosts()));
			break;
		}

		switch (pref) {
		case DOLLAR_LITER_MILES:
		case DOLLAR_UKGALLONS_MILES:
		case DOLLAR_USGALLONS_MILES:
		case EURO_LITER_MILES:
		case EURO_UKGALLONS_MILES:
		case EURO_USGALLONS_MILES:
		case POUNDS_LITER_MILES:
		case POUNDS_UKGALLONS_MILES:
		case POUNDS_USGALLONS_MILES:
			data.setCurrentDistance(UnitConverter.convertMilesInKm(data.getCurrentDistance()));
			data.setLastDistance(UnitConverter.convertMilesInKm(data.getLastDistance()));
			break;
		}

		return data;
	}

}
