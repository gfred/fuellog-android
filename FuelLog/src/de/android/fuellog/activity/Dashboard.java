package de.android.fuellog.activity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import de.android.fuellog.R;
import de.android.fuellog.consumer.FuelLogDAO;
import de.android.fuellog.model.PreferencesData;
import de.android.fuellog.util.Calculations;
import de.android.fuellog.util.Values;

public class Dashboard extends Activity {
	private static final String TAG = "de.android.fuellog.activity.Dashboard";
	private static final boolean DEBUG = false;

	private TextView totalCostText;
	private TextView lastDateText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (DEBUG) {

		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		totalCostText = (TextView) findViewById(R.id.dashboard_total_money_text);
		lastDateText = (TextView) findViewById(R.id.dashboard_last_fillup_date_text);

		updateDashboard();
	}

	public final void onClickSearch(final View view) {
		if (DEBUG) {

		}
	}

	public final void onClickFillUp(final View view) {
		if (DEBUG) {

		}
		startActivityForResult(new Intent(this, FillUp.class), Values.QUIT_APPLICATION);
	}

	public final void onClickHistory(final View view) {
		if (DEBUG) {

		}
		startActivityForResult(new Intent(this, History.class), Values.QUIT_APPLICATION);
	}

	public final void onClickStatistics(final View view) {
		if (DEBUG) {

		}
		startActivityForResult(new Intent(this, Statistic.class), Values.QUIT_APPLICATION);
	}

	public final void onClickPreferences(final View view) {
		if (DEBUG) {

		}
		Log.d(TAG, "not implemented yet");
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateDashboard();
	}

	@Override
	protected final void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		if (DEBUG) {

		}
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {
		case Values.QUIT_APPLICATION:
			if (data != null && data.getExtras().getInt(Values.QUIT_APP_RESULT) == Values.QUIT_APPLICATION) {
				closeActivities();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		if (DEBUG) {
		}

		MenuItem quitItem = menu.add(1, 1, 1, R.string.menu_quit);

		quitItem.setIcon(android.R.drawable.ic_lock_power_off);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public final boolean onMenuItemSelected(final int featureId, final MenuItem item) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}

		switch (item.getItemId()) {
		case 1:
			closeActivities();
			break;
		default:
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	private void closeActivities() {
		if (DEBUG) {

		}
		Intent quitApplication = new Intent();
		quitApplication.putExtra(Values.QUIT_APP_RESULT, Values.QUIT_APPLICATION);
		setResult(Values.QUIT_APPLICATION, quitApplication);
		finish();
	}

	private void updateDashboard() {
		PreferencesData pref = FuelLogDAO.getInstance(this).getPreferences();
		int settings = 0;
		String pattern = "dd.MM.yyyy";

		if (pref != null) {
			settings = pref.getUnit();
			pattern = pref.getDatePattern();
		}

		DecimalFormat formater;

		switch (settings) {
		case Values.DOLLAR_LITER_KM:
		case Values.DOLLAR_LITER_MILES:
		case Values.DOLLAR_UKGALLONS_KM:
		case Values.DOLLAR_UKGALLONS_MILES:
		case Values.DOLLAR_USGALLONS_KM:
		case Values.DOLLAR_USGALLONS_MILES:
			formater = new DecimalFormat("#0.00 $");
			break;
		case Values.POUNDS_LITER_KM:
		case Values.POUNDS_LITER_MILES:
		case Values.POUNDS_UKGALLONS_KM:
		case Values.POUNDS_UKGALLONS_MILES:
		case Values.POUNDS_USGALLONS_KM:
		case Values.POUNDS_USGALLONS_MILES:
			formater = new DecimalFormat("#0.00 £");
			break;
		default:
			formater = new DecimalFormat("#0.00 €");
			break;
		}

		totalCostText.setText(getString(R.string.dashboard_total) + " "
				+ formater.format(Calculations.getTotalCosts(this)));

		Date date = Calculations.getLastFuelDate(this);
		if (date == null) {
			lastDateText.setText(getString(R.string.dashboard_last_fill_up) + " "
					+ getString(R.string.dashboard_no_fill_up));
		} else {
			lastDateText.setText(getString(R.string.dashboard_last_fill_up) + " "
					+ new SimpleDateFormat(pattern).format(date));
		}

	}
}
