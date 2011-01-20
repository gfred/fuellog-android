package com.arconsis.prototype.driversentry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class DriversLogEntryActivity extends Activity {
	private static final String TAG = "com.arconsis.fleetmanager.activities.DriversLogEntryActivity";
	private static final boolean DEBUG = false;

	private EditText dateText;
	private EditText timeText;
	private EditText locationText;
	private EditText mileageText;
	private EditText descriptionText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driverslogentry_alt);

		dateText = (EditText) findViewById(R.id.driverslogentryDate);
		timeText = (EditText) findViewById(R.id.driverslogentryTime);
		locationText = (EditText) findViewById(R.id.driverslogentryLocation);
		mileageText = (EditText) findViewById(R.id.driverslogentryMileage);
		descriptionText = (EditText) findViewById(R.id.driverslogentryComment);

		// EditText text = (EditText) findViewById(R.id.driverslogentryLocation);
		// Drawable x = getResources().getDrawable(android.R.drawable.ic_menu_mylocation);
		// x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
		//
		// text.setHint(R.string.driverslogentry_location);
		// text.setCompoundDrawables(null, null, x, null);
		//
		// ImageView image = new ImageView(this);
		// image.setImageDrawable(x);
		// text.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.d(TAG, "Location Press Test!!");
		// }
		// });

	}

	/**
	 * onClickSaveDriversLogEntry onClickTakePicture onClickTime onClickDate
	 */

	/**
	 * on click on the add button in the actionbar, this method will be executed
	 * 
	 * @param view
	 *            - Button view instance
	 */
	public final void onClickTopAdd(final View view) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}
		// startActivityForResult(new Intent(this, AddBookingActivity.class), ControlValues.QUIT_APPLICATION);
	}

	/**
	 * on click on the home button in the actionbar, this method will be executed
	 * 
	 * @param view
	 *            - Button view instance
	 */
	public final void onClickTopHome(final View view) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}
		// startActivityForResult(new Intent(this, DashboardActivity.class), ControlValues.QUIT_APPLICATION);
		quitApplication();
	}

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}

		MenuItem quitItem = menu.add(0, 0, 0, R.string.menu_quit);
		quitItem.setIcon(android.R.drawable.ic_lock_power_off);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public final boolean onMenuItemSelected(final int featureId, final MenuItem item) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}

		switch (item.getItemId()) {
		case 0:
			this.quitApplication();
			break;
		default:
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected final void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}

		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case ControlValues.QUIT_APPLICATION:
			this.quitApplication();
			break;
		default:
			break;
		}
	}

	/**
	 * Close this activity and set result value, to close also other open activities of this app.
	 */
	private void quitApplication() {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}

		Intent quitApplication = new Intent();
		quitApplication.putExtra(ControlValues.QUIT_APP_RESULT, ControlValues.QUIT_APPLICATION);
		setResult(ControlValues.QUIT_APPLICATION, quitApplication);
		super.finish();
	}
}
