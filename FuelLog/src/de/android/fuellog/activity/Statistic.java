package de.android.fuellog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import de.android.fuellog.R;
import de.android.fuellog.util.Values;

public class Statistic extends Activity {
	private static final String TAG = "de.android.fuellog.activity.Statistic";
	private static final boolean DEBUG = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
	}

	public final void onClickHome(final View view) {
		startActivityForResult(new Intent(this, Dashboard.class), Values.QUIT_APPLICATION_CODE);
		closeActivities();
	}

	public final void onClickSearch(final View view) {
		Log.d(TAG, "not implemented yet");
	}

	public final void onClickFillUp(final View view) {
		startActivityForResult(new Intent(this, FillUp.class), Values.QUIT_APPLICATION_CODE);
	}

	@Override
	protected final void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {
		case Values.QUIT_APPLICATION_CODE:
			if (data != null && data.getExtras().getInt(Values.QUIT_APP_RESULT) == Values.QUIT_APPLICATION_CODE) {
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
		Intent quitApplication = new Intent();
		quitApplication.putExtra(Values.QUIT_APP_RESULT, Values.QUIT_APPLICATION_CODE);
		setResult(Values.QUIT_APPLICATION_CODE, quitApplication);
		finish();
	}
}