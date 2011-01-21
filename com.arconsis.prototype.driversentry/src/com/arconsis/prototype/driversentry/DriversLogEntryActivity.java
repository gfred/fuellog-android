package com.arconsis.prototype.driversentry;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.arconsis.prototype.driversentry.model.DriversLogEntry;

/**
 * 
 * @author Frederik Goetz
 * @date 20.01.2011
 */
public class DriversLogEntryActivity extends Activity {
	private static final String TAG = "com.arconsis.fleetmanager.activities.DriversLogEntryActivity";
	private static final boolean DEBUG = false;

	private static final int DATE_PICKER_DIALOG = 1;
	private static final int TIME_PICKER_DIALOG = 2;
	private static final int SELECT_IMAGE_DIALOG = 3;

	private EditText dateText;
	private EditText timeText;
	private EditText locationText;
	private EditText mileageText;
	private EditText descriptionText;

	private DriversLogEntry entryData = new DriversLogEntry();

	private File tmpPictureFile;
	private int selection = -1;

	private LocationManager locationManager;
	private boolean locationEdited = false;

	private LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
			if (location.hasAccuracy() && location.getAccuracy() < 30) {
				entryData.setCurrentLoctaion(location);

				if (locationManager != null) {
					locationManager.removeUpdates(locationListener);

					if (!locationEdited) {
						new AsyncLocationTask(location).execute(null);
					}
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driverslogentry);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		dateText = (EditText) findViewById(R.id.driverslogentryDate);
		dateText.setText(new SimpleDateFormat(getString(R.string.datePattern)).format(entryData.getDate()));
		timeText = (EditText) findViewById(R.id.driverslogentryTime);
		timeText.setText(new SimpleDateFormat(getString(R.string.timePattern)).format(entryData.getDate()));
		locationText = (EditText) findViewById(R.id.driverslogentryLocation);
		locationText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			private String tmp;

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					tmp = ((TextView) v).getText().toString();
				} else {
					if (!((TextView) v).getText().toString().equals(tmp)) {
						entryData.setBookingLocation(null);
						tmp = null;
						locationEdited = true;
					}
				}
			}
		});

		mileageText = (EditText) findViewById(R.id.driverslogentryMileage);
		descriptionText = (EditText) findViewById(R.id.driverslogentryComment);

		getLastKnownLocation();
		getBestPossibleLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
		}
	}

	private void getBestPossibleLocation() {
		Criteria cr = new Criteria();
		cr.setAccuracy(Criteria.ACCURACY_FINE);
		cr.setPowerRequirement(Criteria.POWER_HIGH);
		cr.setBearingRequired(false);
		cr.setSpeedRequired(false);
		cr.setCostAllowed(true);
		// SICHER?
		cr.setAltitudeRequired(true);

		List<String> matchingProviders = locationManager.getProviders(cr, false);

		for (String provider : matchingProviders) {
			locationManager.requestLocationUpdates(provider, 1000, 5, locationListener);
		}
	}

	private void getLastKnownLocation() {
		Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		new AsyncLocationTask(location).execute(null);
	}

	private String getAddressAsString(Address address, double accuracy) {
		if (address != null) {
			ArrayList<String> entries = new ArrayList<String>(5);

			if (address.getThoroughfare() != null && accuracy < 100) {
				entries.add(address.getThoroughfare());
			}

			if (address.getPostalCode() != null) {
				entries.add(address.getPostalCode());
			}

			if (address.getSubAdminArea() != null) {
				entries.add(address.getLocality());
			}

			if (address.getAdminArea() != null) {
				entries.add(address.getAdminArea());
			}

			if (address.getCountryCode() != null) {
				entries.add(address.getCountryCode());
			}

			StringBuilder locDescription = new StringBuilder();
			for (int i = 0; i < entries.size(); i++) {
				locDescription.append(entries.get(i));
				if (i + 1 < entries.size()) {
					locDescription.append(", ");
				}
			}

			return locDescription.toString();
		}
		return "";
	}

	/**
	 * 
	 * @param view
	 */
	public final void onClickSaveDriversLogEntry(final View view) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}

		if (mileageText.getText().toString().trim().length() > 0) {
			entryData.setMileage(Double.parseDouble(mileageText.getText().toString()));
		} else {
			entryData.setMileage(Double.parseDouble("0"));
		}

		entryData.setDescription(descriptionText.getText().toString());
		entryData.setBookingLocationDesc(locationText.getText().toString());
	}

	/**
	 * 
	 * @param view
	 */
	public final void onClickTakePicture(final View view) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}

		if (!ControlValues.SDCARD_FOLDER.exists()) {
			ControlValues.SDCARD_FOLDER.mkdir();
		}

		tmpPictureFile = new File(ControlValues.SDCARD_FOLDER.getAbsolutePath() + "/" + System.currentTimeMillis()
				+ ".png");

		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		Uri uri = Uri.fromFile(tmpPictureFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, ControlValues.CAMERA_REQUEST_CODE);
	}

	/**
	 * 
	 * @param view
	 */
	public final void onClickPictures(final View view) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}
		showDialog(SELECT_IMAGE_DIALOG);
	}

	/**
	 * 
	 * @param view
	 */
	public final void onClickTime(final View view) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}
		showDialog(TIME_PICKER_DIALOG);
	}

	/**
	 * 
	 * @param view
	 */
	public final void onClickDate(final View view) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}
		showDialog(DATE_PICKER_DIALOG);
	}

	/**
	 * 
	 * @param view
	 */
	public final void onClickLocation(final View view) {
		if (DEBUG) {
			Log.d(TAG, "Debug");
		}
		new AsyncLocationTask(entryData.getCurrentLoctaion()).execute(null);
	}

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
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_DIALOG:
			int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(entryData.getDate()));
			int month = Integer.parseInt(new SimpleDateFormat("MM").format(entryData.getDate())) - 1;
			int day = Integer.parseInt(new SimpleDateFormat("dd").format(entryData.getDate()));
			OnDateSetListener dateListener = new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					try {
						entryData.getDate().setYear(year - 1900);
						entryData.getDate().setMonth(monthOfYear);
						entryData.getDate().setDate(dayOfMonth);
						dateText.setText(new SimpleDateFormat(getString(R.string.datePattern)).format(entryData
								.getDate()));
					} catch (Exception e) {
						Log.e(TAG, "Error Date Dialog", e);
					}
				}
			};
			return new DatePickerDialog(this, dateListener, year, month, day);
		case TIME_PICKER_DIALOG:
			int hours = Integer.parseInt(new SimpleDateFormat("HH").format(entryData.getDate()));
			int minutes = Integer.parseInt(new SimpleDateFormat("mm").format(entryData.getDate()));
			OnTimeSetListener timeListener = new OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					try {
						entryData.getDate().setHours(hourOfDay);
						entryData.getDate().setMinutes(minute);
						timeText.setText(new SimpleDateFormat(getString(R.string.timePattern)).format(entryData
								.getDate()));
					} catch (Exception e) {
						Log.e(TAG, "Error Date Dialog", e);
					}
				}
			};
			return new TimePickerDialog(this, timeListener, hours, minutes, true);
		case SELECT_IMAGE_DIALOG:
			if (entryData.getPictures().size() == 0) {
				Toast.makeText(this, R.string.driverslogentry_nopictures_toast, Toast.LENGTH_LONG).show();
			} else {
				return createPictureDialog();
			}
		default:
			break;
		}

		return super.onCreateDialog(id);
	}

	private AlertDialog createPictureDialog() {
		String[] items = new String[entryData.getPictures().size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = entryData.getPictures().get(i).getName();
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(R.string.driverslogentry_picture_title);
		builder.setSingleChoiceItems(items, selection, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				selection = item;
			}
		});

		builder.setPositiveButton(R.string.driverslogentry_dialog_picture_view, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(entryData.getPictures().get(selection)), "image/png");
				startActivity(intent);
				dialog.dismiss();
				selection = -1;
				removeDialog(SELECT_IMAGE_DIALOG);
			}
		});
		builder.setNeutralButton(R.string.driverslogentry_dialog_picture_delete, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (entryData.getPictures().get(selection).delete()) {
					entryData.getPictures().remove(selection);
					Toast.makeText(DriversLogEntryActivity.this, R.string.driverslogentry_delete_pictures_toast,
							Toast.LENGTH_LONG).show();
					dialog.dismiss();
					selection = -1;
					removeDialog(SELECT_IMAGE_DIALOG);
				} else {
					Toast.makeText(DriversLogEntryActivity.this, R.string.driverslogentry_delete_fail_pictures_toast,
							Toast.LENGTH_LONG).show();
					dialog.dismiss();
					selection = -1;
					removeDialog(SELECT_IMAGE_DIALOG);
				}
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				selection = -1;
				removeDialog(SELECT_IMAGE_DIALOG);
			}
		});
		return builder.create();
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

		if (requestCode == ControlValues.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
			tmpPictureFile.delete();
			tmpPictureFile = null;
		} else if (requestCode == ControlValues.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			this.entryData.getPictures().add(tmpPictureFile);
			tmpPictureFile = null;
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

	private class AsyncLocationTask extends AsyncTask<Void, Void, String> {
		private Location location;

		public AsyncLocationTask(Location location) {
			this.location = location;
		}

		@Override
		protected String doInBackground(Void... params) {
			Geocoder gc = new Geocoder(DriversLogEntryActivity.this);

			if (location != null) {
				entryData.setCurrentLoctaion(location);

				try {
					List<Address> addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
					if (addresses.size() > 0) {
						if (location.hasAccuracy()) {
							return getAddressAsString(addresses.get(0), location.getAccuracy());
						} else {
							return getAddressAsString(addresses.get(0), -1);
						}
					}
				} catch (IOException e) {
					Log.e(TAG, "Geocoder - IOException", e);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (result == null) {
				Toast.makeText(DriversLogEntryActivity.this, R.string.warning_no_internet, Toast.LENGTH_LONG).show();
			} else {
				locationText.setText(result);
				locationEdited = false;
				entryData.setBookingLocation(entryData.getCurrentLoctaion());
			}
		}
	}
}
