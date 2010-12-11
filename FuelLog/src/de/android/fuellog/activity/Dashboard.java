package de.android.fuellog.activity;

import android.app.Activity;
import android.os.Bundle;
import de.android.fuellog.R;

public class Dashboard extends Activity {
	private static final String TAG = "de.android.fuellog.activity.Dashboard";
	private static final boolean DEBUG = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
	}

}
