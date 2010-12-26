package de.android.fuellog.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import de.android.fuellog.R;
import de.android.fuellog.util.Values;

public class Preferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Values.PREFENCES_BACK_CODE);
    }

}
