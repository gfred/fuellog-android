package de.android.fuellog.activity.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import de.android.fuellog.R;
import de.android.fuellog.model.PreferencesData;
import de.android.fuellog.util.Values;

public class PreferencesHelperTask extends AsyncTask<Void, Void, PreferencesData> implements Values {

    private Context ctx;

    public PreferencesHelperTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected PreferencesData doInBackground(Void... params) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        PreferencesData prefData = new PreferencesData();
        prefData.setFirstDistance(Double.parseDouble(preferences.getString(
                ctx.getString(R.string.preferences_current_distance_key), "0")));
        prefData.setDatePattern(preferences.getString(ctx.getString(R.string.preferences_date_pattern_key),
                "dd.MM.yyyy"));

        String currency = preferences.getString(ctx.getString(R.string.preferences_currency_key), "euro");
        String measure = preferences.getString(ctx.getString(R.string.preferences_measuringunit_key), "km");
        String volume = preferences.getString(ctx.getString(R.string.preferences_unit_of_volume_key), "liter");

        if (currency.equals("euro") && volume.equals("liter") && measure.equals("km")) {
            prefData.setUnit(EURO_LITER_KM);
        } else if (currency.equals("euro") && volume.equals("liter") && measure.equals("m")) {
            prefData.setUnit(EURO_LITER_MILES);
        } else if (currency.equals("euro") && volume.equals("usgal") && measure.equals("km")) {
            prefData.setUnit(EURO_USGALLONS_KM);
        } else if (currency.equals("euro") && volume.equals("usgal") && measure.equals("m")) {
            prefData.setUnit(EURO_USGALLONS_MILES);
        } else if (currency.equals("euro") && volume.equals("ukgal") && measure.equals("km")) {
            prefData.setUnit(EURO_UKGALLONS_KM);
        } else if (currency.equals("euro") && volume.equals("ukgal") && measure.equals("m")) {
            prefData.setUnit(EURO_UKGALLONS_MILES);
        } else if (currency.equals("dollar") && volume.equals("liter") && measure.equals("km")) {
            prefData.setUnit(DOLLAR_LITER_KM);
        } else if (currency.equals("dollar") && volume.equals("liter") && measure.equals("m")) {
            prefData.setUnit(DOLLAR_LITER_MILES);
        } else if (currency.equals("dollar") && volume.equals("usgal") && measure.equals("km")) {
            prefData.setUnit(DOLLAR_USGALLONS_KM);
        } else if (currency.equals("dollar") && volume.equals("usgal") && measure.equals("m")) {
            prefData.setUnit(DOLLAR_USGALLONS_MILES);
        } else if (currency.equals("dollar") && volume.equals("ukgal") && measure.equals("km")) {
            prefData.setUnit(DOLLAR_UKGALLONS_KM);
        } else if (currency.equals("dollar") && volume.equals("ukgal") && measure.equals("m")) {
            prefData.setUnit(DOLLAR_UKGALLONS_MILES);
        } else if (currency.equals("pounds") && volume.equals("liter") && measure.equals("km")) {
            prefData.setUnit(POUNDS_LITER_KM);
        } else if (currency.equals("pounds") && volume.equals("liter") && measure.equals("m")) {
            prefData.setUnit(POUNDS_LITER_MILES);
        } else if (currency.equals("pounds") && volume.equals("usgal") && measure.equals("km")) {
            prefData.setUnit(POUNDS_USGALLONS_KM);
        } else if (currency.equals("pounds") && volume.equals("usgal") && measure.equals("m")) {
            prefData.setUnit(POUNDS_USGALLONS_MILES);
        } else if (currency.equals("pounds") && volume.equals("ukgal") && measure.equals("km")) {
            prefData.setUnit(POUNDS_UKGALLONS_KM);
        } else if (currency.equals("pounds") && volume.equals("ukgal") && measure.equals("m")) {
            prefData.setUnit(POUNDS_UKGALLONS_MILES);
        }

        return prefData;
    }
}
