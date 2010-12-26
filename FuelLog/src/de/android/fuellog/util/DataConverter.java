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
            FuelData.currencyUnit = "$";
            break;
        case POUNDS_LITER_KM:
        case POUNDS_LITER_MILES:
        case POUNDS_UKGALLONS_KM:
        case POUNDS_UKGALLONS_MILES:
        case POUNDS_USGALLONS_KM:
        case POUNDS_USGALLONS_MILES:
            data.setAmountCosts(UnitConverter.convertEuroInPounds(data.getAmountCosts()));
            FuelData.currencyUnit = "£";
            break;
        default:
            FuelData.currencyUnit = "€";
            break;
        }

        switch (pref) {
        case EURO_UKGALLONS_KM:
        case EURO_UKGALLONS_MILES:
            data.setCosts(UnitConverter.UKGALLON_LITER * data.getCosts());
            break;
        case EURO_USGALLONS_KM:
        case EURO_USGALLONS_MILES:
            data.setCosts(UnitConverter.USGALLON_LITER * data.getCosts());
            break;
        case DOLLAR_LITER_KM:
        case DOLLAR_LITER_MILES:
            data.setCosts(UnitConverter.convertEuroInDollar(data.getCosts()));
            break;
        case DOLLAR_UKGALLONS_KM:
        case DOLLAR_UKGALLONS_MILES:
            data.setCosts(UnitConverter.convertEuroInDollar(UnitConverter.UKGALLON_LITER * data.getCosts()));
            break;
        case DOLLAR_USGALLONS_KM:
        case DOLLAR_USGALLONS_MILES:
            data.setCosts(UnitConverter.convertEuroInDollar(UnitConverter.USGALLON_LITER * data.getCosts()));
            break;
        case POUNDS_LITER_KM:
        case POUNDS_LITER_MILES:
            data.setCosts(UnitConverter.convertEuroInPounds(data.getCosts()));
        case POUNDS_UKGALLONS_KM:
        case POUNDS_UKGALLONS_MILES:
            data.setCosts(UnitConverter.convertEuroInPounds(UnitConverter.UKGALLON_LITER * data.getCosts()));
            break;
        case POUNDS_USGALLONS_KM:
        case POUNDS_USGALLONS_MILES:
            data.setCosts(UnitConverter.convertEuroInPounds(UnitConverter.USGALLON_LITER * data.getCosts()));
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
            FuelData.measureUnit = "m";
            break;
        default:
            FuelData.measureUnit = "km";
        }

        switch (pref) {
        case EURO_UKGALLONS_KM:
        case EURO_UKGALLONS_MILES:
        case DOLLAR_UKGALLONS_KM:
        case DOLLAR_UKGALLONS_MILES:
        case POUNDS_UKGALLONS_KM:
        case POUNDS_UKGALLONS_MILES:
            FuelData.volumeUnit = "Imp.gal";
            break;
        case EURO_USGALLONS_KM:
        case EURO_USGALLONS_MILES:
        case DOLLAR_USGALLONS_KM:
        case DOLLAR_USGALLONS_MILES:
        case POUNDS_USGALLONS_KM:
        case POUNDS_USGALLONS_MILES:
            FuelData.volumeUnit = "gal";
            break;
        default:
            FuelData.volumeUnit = "l";
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

    public static String getCurrencySign(final int pref) {
        switch (pref) {
        case Values.DOLLAR_LITER_KM:
        case Values.DOLLAR_LITER_MILES:
        case Values.DOLLAR_UKGALLONS_KM:
        case Values.DOLLAR_UKGALLONS_MILES:
        case Values.DOLLAR_USGALLONS_KM:
        case Values.DOLLAR_USGALLONS_MILES:
            return "$";
        case Values.POUNDS_LITER_KM:
        case Values.POUNDS_LITER_MILES:
        case Values.POUNDS_UKGALLONS_KM:
        case Values.POUNDS_UKGALLONS_MILES:
        case Values.POUNDS_USGALLONS_KM:
        case Values.POUNDS_USGALLONS_MILES:
            return "£";
        default:
            return "€";
        }
    }

    public static String getVolumeUnit(final int pref) {

        return null;
    }

    public static String getMeasureUnit(final int pref) {
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
        }
        return null;
    }

}
