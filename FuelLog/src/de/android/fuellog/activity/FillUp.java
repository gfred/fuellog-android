package de.android.fuellog.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableRow;
import de.android.fuellog.R;
import de.android.fuellog.consumer.DAOUtils;
import de.android.fuellog.consumer.FuelLogDAO;
import de.android.fuellog.model.FuelData;
import de.android.fuellog.util.Values;

public class FillUp extends Activity {
	private static final String TAG = "de.android.fuellog.activity.FillUp";
	private static final boolean DEBUG = false;

	private static final int DATE_TIME_PICKER_DIALOG = 0;
	private static final int ERROR_DIALOG = 1;

	private EditText dateText;
	private EditText mileAgeText;
	private EditText priceText;
	private EditText totalCostsText;
	private EditText commentText;
	private EditText locationText;

	private TableRow dateRow;
	private TableRow mileAgeRow;
	private TableRow priceRow;
	private TableRow totalCostsRow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fillup);

		dateRow = (TableRow) findViewById(R.id.fillup_date_row);
		mileAgeRow = (TableRow) findViewById(R.id.fillup_mileage_row);
		priceRow = (TableRow) findViewById(R.id.fillup_price_row);
		totalCostsRow = (TableRow) findViewById(R.id.fillup_amount_row);

		dateText = (EditText) findViewById(R.id.fillup_date_text);
		mileAgeText = (EditText) findViewById(R.id.fillup_mileage_text);
		priceText = (EditText) findViewById(R.id.fillup_price_text);
		totalCostsText = (EditText) findViewById(R.id.fillup_amount_text);
		commentText = (EditText) findViewById(R.id.fillup_comment_text);
		locationText = (EditText) findViewById(R.id.fillup_location_text);

		dateText.setText(new SimpleDateFormat(getString(R.string.datePattern)).format(new Date(System
				.currentTimeMillis())));
	}

	public final void onClickHome(final View view) {
		startActivityForResult(new Intent(this, Dashboard.class), Values.QUIT_APPLICATION_CODE);
		closeActivities();
	}

	public final void onClickSearch(final View view) {

	}

	public final void onClickDate(final View view) {
		showDialog(DATE_TIME_PICKER_DIALOG);
	}

	public final void onClickSave(final View view) {
		if (saveData()) {
			FillUp.this.finish();
		} else {
			showDialog(ERROR_DIALOG);
		}
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

	private boolean saveData() {
		FuelData fuel = new FuelData();
		fuel.setLocation(locationText.getText().toString());
		fuel.setComment(commentText.getText().toString());

		try {
			dateRow.setBackgroundColor(0);
			String date = dateText.getText().toString();
			fuel.setDate(new SimpleDateFormat(getString(R.string.datePattern)).parse(date));
		} catch (Exception e) {
			dateRow.setBackgroundColor(R.color.text_error_background);
			Log.e(TAG, "Date Cast Exception, wrong datatype!", e);
			return false;
		}

		try {
			mileAgeRow.setBackgroundColor(0);
			String mileage = mileAgeText.getText().toString();
			fuel.setCurrentDistance(Double.parseDouble(mileage));
		} catch (Exception e) {
			mileAgeRow.setBackgroundColor(R.color.text_error_background);
			Log.e(TAG, "Mile Age Cast Exception, wrong datatype!", e);
			return false;
		}

		try {
			priceRow.setBackgroundColor(0);
			String price = priceText.getText().toString();
			fuel.setCosts(Double.parseDouble(price));
		} catch (Exception e) {
			priceRow.setBackgroundColor(R.color.text_error_background);
			Log.e(TAG, "Price Cast Exception, wrong datatype!", e);
			return false;
		}

		try {
			totalCostsRow.setBackgroundColor(0);
			String totalCosts = totalCostsText.getText().toString();
			fuel.setAmountCosts(Double.parseDouble(totalCosts));
		} catch (Exception e) {
			totalCostsRow.setBackgroundColor(R.color.text_error_background);
			Log.e(TAG, "Total Costs Cast Exception, wrong datatype!", e);
			return false;
		}

		fuel.setLastDistance(DAOUtils.getLastDistance(this));

		FuelLogDAO.getInstance(this).createFuelData(fuel);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_TIME_PICKER_DIALOG:
			int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis())));
			int monthOfYear = Integer.parseInt(new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis()))) - 1;
			int dayOfMonth = Integer.parseInt(new SimpleDateFormat("dd").format(new Date(System.currentTimeMillis())));
			OnDateSetListener dateListener = new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					try {
						dateText.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
					} catch (Exception e) {
						Log.e(TAG, "Error Time Dialog", e);
					}
				}
			};
			return new DatePickerDialog(this, dateListener, year, monthOfYear, dayOfMonth);
		case ERROR_DIALOG:
			return createErrorDialog();
		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	private AlertDialog createErrorDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.fillup_errordialog_title);
		builder.setMessage(R.string.fillup_errordialog_msg);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				removeDialog(ERROR_DIALOG);
			}
		});
		return builder.create();
	}

	private void closeActivities() {
		Intent quitApplication = new Intent();
		quitApplication.putExtra(Values.QUIT_APP_RESULT, Values.QUIT_APPLICATION_CODE);
		setResult(Values.QUIT_APPLICATION_CODE, quitApplication);
		finish();
	}
}
