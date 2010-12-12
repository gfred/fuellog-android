package de.android.fuellog.consumer;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import de.android.fuellog.database.tables.Car;
import de.android.fuellog.database.tables.Fuel;
import de.android.fuellog.database.tables.Preferences;
import de.android.fuellog.model.CarData;
import de.android.fuellog.model.FuelData;
import de.android.fuellog.model.PreferencesData;
import de.android.fuellog.provider.FuelLogProvider;

public class FuelLogDAO {
	private static final String TAG = "de.android.fuellog.consumer.FuelLogDAO";
	private static final Boolean DEBUG = false;

	public static final Uri FUEL_DATA_URI = Uri.parse("content://" + FuelLogProvider.AUTHORITY + "/"
			+ FuelLogProvider.FuelContent.CONTENT_PATH);

	public static final Uri PREFERENCES_URI = Uri.parse("content://" + FuelLogProvider.AUTHORITY + "/"
			+ FuelLogProvider.PreferencesContent.CONTENT_PATH);
	public static final Uri CAR_DATA_URI = Uri.parse("content://" + FuelLogProvider.AUTHORITY + "/"
			+ FuelLogProvider.CarContent.CONTENT_PATH);

	private static FuelLogDAO instance = null;
	private static Context ctx = null;
	private static ContentResolver resolver = null;

	private HashMap<Long, FuelData> fuelCache = new HashMap<Long, FuelData>();
	private HashMap<Long, PreferencesData> preferencesCache = new HashMap<Long, PreferencesData>();
	private HashMap<Long, CarData> carCache = new HashMap<Long, CarData>();

	private FuelLogDAO() {
	}

	public static FuelLogDAO getInstancte(final Context ctx) {
		if (instance == null) {
			FuelLogDAO.ctx = ctx;
			FuelLogDAO.resolver = ctx.getContentResolver();
			instance = new FuelLogDAO();
		}
		return instance;
	}

	public FuelData getFuelDataById(final Long id) {
		if (id == null) {
			return null;
		}
		if (!fuelCache.containsKey(id) || fuelCache.get(id) != null) {
			Uri fuelUri = ContentUris.withAppendedId(FUEL_DATA_URI, id);
			Cursor cursor = resolver.query(fuelUri, null, null, null, null);
			cursor.moveToFirst();

			FuelData fuelData = new FuelData();
			fuelData.setId(id);
			fuelData.setAmountCosts(cursor.getDouble(cursor.getColumnIndex(Fuel.AMOUNT_COSTS)));
			fuelData.setComment(cursor.getString(cursor.getColumnIndex(Fuel.COMMENT)));
			fuelData.setCosts(cursor.getDouble(cursor.getColumnIndex(Fuel.COSTS)));
			fuelData.setCurrentDistance(cursor.getDouble(cursor.getColumnIndex(Fuel.CURRENT_DISTANCE)));
			fuelData.setDate(new Date(cursor.getLong(cursor.getColumnIndex(Fuel.LAST_DISTANCE))));
			fuelData.setLastDistance(cursor.getDouble(cursor.getColumnIndex(Fuel.LAST_DISTANCE)));
			fuelData.setLocation(cursor.getString(cursor.getColumnIndex(Fuel.LOCATION)));
			fuelData.setPicturePath(cursor.getString(cursor.getColumnIndex(Fuel.PICTURE_PATH)));
			fuelData.setCarId(cursor.getInt(cursor.getColumnIndex(Fuel.CAR_ID)));

			if (!cursor.isClosed()) {
				cursor.close();
			}

			fuelCache.put(id, fuelData);

			return fuelData;
		}

		return fuelCache.get(id);
	}

	public Collection<FuelData> getAllFuelData() {
		Cursor cursor = resolver.query(FUEL_DATA_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			getFuelDataById(cursor.getLong(cursor.getColumnIndex(Fuel.ID)));
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return fuelCache.values();
	}

	public PreferencesData getPreferencesById(final Long id) {
		if (id == null) {
			return null;
		}
		if (!preferencesCache.containsKey(id) || preferencesCache.get(id) != null) {
			Uri preferencesUri = ContentUris.withAppendedId(PREFERENCES_URI, id);
			Cursor cursor = resolver.query(preferencesUri, null, null, null, null);
			cursor.moveToFirst();

			PreferencesData preferences = new PreferencesData();
			preferences.setId(id);
			preferences.setCurrency(cursor.getInt(cursor.getColumnIndex(Preferences.CURRENCY)));
			preferences.setFirstDistance(cursor.getDouble(cursor.getColumnIndex(Preferences.FIRST_DISTANCE)));
			preferences.setUnit(cursor.getInt(cursor.getColumnIndex(Preferences.UNIT)));

			if (!cursor.isClosed()) {
				cursor.close();
			}

			preferencesCache.put(id, preferences);

			return preferences;
		}

		return preferencesCache.get(id);
	}

	public Collection<PreferencesData> getAllPreferences() {
		Cursor cursor = resolver.query(PREFERENCES_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			getFuelDataById(cursor.getLong(cursor.getColumnIndex(Preferences.ID)));
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return preferencesCache.values();
	}

	public CarData getCarById(final Long id) {
		if (id == null) {
			return null;
		}
		if (!carCache.containsKey(id) || carCache.get(id) != null) {
			Uri carDataUri = ContentUris.withAppendedId(CAR_DATA_URI, id);
			Cursor cursor = resolver.query(carDataUri, null, null, null, null);
			cursor.moveToFirst();

			CarData car = new CarData();
			car.setId(id);
			car.setLicenseNo(cursor.getString(cursor.getColumnIndex(Car.LICENSE_NO)));
			car.setName(cursor.getString(cursor.getColumnIndex(Car.CAR_NAME)));

			if (!cursor.isClosed()) {
				cursor.close();
			}

			carCache.put(id, car);

			return car;
		}

		return carCache.get(id);
	}

	public Collection<CarData> getAllCars() {
		Cursor cursor = resolver.query(CAR_DATA_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			getFuelDataById(cursor.getLong(cursor.getColumnIndex(Car.ID)));
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return carCache.values();
	}

	public void setCarData(final CarData cardata) {
		Uri carUri = ContentUris.withAppendedId(CAR_DATA_URI, cardata.getId());

		int update = ctx.getContentResolver().update(carUri, getCarDataAsContentValues(cardata), null, null);

		if (update > 0) {
			carCache.put(cardata.getId(), cardata);
		} else {
			Log.d(TAG, "Car update failed! Updated Values: " + update);
		}
	}

	public void setFuelData(final FuelData fuelData) {
		Uri fuelUri = ContentUris.withAppendedId(FUEL_DATA_URI, fuelData.getId());

		int update = ctx.getContentResolver().update(fuelUri, getFuelDataAsContentValues(fuelData), null, null);

		if (update > 0) {
			fuelCache.put(fuelData.getId(), fuelData);
		} else {
			Log.d(TAG, "Car update failed! Updated Values: " + update);
		}
	}

	public void setPreferencesData(final PreferencesData preferencesData) {
		Uri prefUri = ContentUris.withAppendedId(PREFERENCES_URI, preferencesData.getId());

		int update = ctx.getContentResolver().update(prefUri, getPreferencesAsContentValues(preferencesData), null,
				null);

		if (update > 0) {
			preferencesCache.put(preferencesData.getId(), preferencesData);
		} else {
			Log.d(TAG, "Car update failed! Updated Values: " + update);
		}
	}

	public void createCarData(final CarData cardata) {
		Uri carUri = ContentUris.withAppendedId(CAR_DATA_URI, cardata.getId());
		ctx.getContentResolver().insert(carUri, getCarDataAsContentValues(cardata));
		carCache.put(cardata.getId(), cardata);
	}

	public void createFuelData(final FuelData fuelData) {
		Uri fuelUri = ContentUris.withAppendedId(FUEL_DATA_URI, fuelData.getId());
		ctx.getContentResolver().insert(fuelUri, getFuelDataAsContentValues(fuelData));
		fuelCache.put(fuelData.getId(), fuelData);
	}

	public void createPreferencesData(final PreferencesData preferencesData) {
		Uri prefUri = ContentUris.withAppendedId(PREFERENCES_URI, preferencesData.getId());
		ctx.getContentResolver().insert(prefUri, getPreferencesAsContentValues(preferencesData));
		preferencesCache.put(preferencesData.getId(), preferencesData);
	}

	private ContentValues getCarDataAsContentValues(CarData cardata) {
		ContentValues values = new ContentValues();

		values.put(Car.ID, cardata.getId());
		values.put(Car.CAR_NAME, cardata.getName());
		values.put(Car.LICENSE_NO, cardata.getLicenseNo());

		return values;
	}

	private ContentValues getFuelDataAsContentValues(FuelData fuelData) {
		ContentValues values = new ContentValues();

		values.put(Fuel.ID, fuelData.getId());
		values.put(Fuel.AMOUNT_COSTS, fuelData.getAmountCosts());
		values.put(Fuel.CAR_ID, fuelData.getCarId());
		values.put(Fuel.COMMENT, fuelData.getComment());
		values.put(Fuel.COSTS, fuelData.getCosts());
		values.put(Fuel.CURRENT_DISTANCE, fuelData.getCurrentDistance());
		values.put(Fuel.FUEL_DATE, fuelData.getDate().getTime());
		values.put(Fuel.LAST_DISTANCE, fuelData.getLastDistance());
		values.put(Fuel.LOCATION, fuelData.getLocation());
		values.put(Fuel.PICTURE_PATH, fuelData.getPicturePath());

		return values;
	}

	private ContentValues getPreferencesAsContentValues(PreferencesData preferencesData) {
		ContentValues values = new ContentValues();

		values.put(Preferences.ID, preferencesData.getId());
		values.put(Preferences.CURRENCY, preferencesData.getCurrency());
		values.put(Preferences.FIRST_DISTANCE, preferencesData.getFirstDistance());
		values.put(Preferences.UNIT, preferencesData.getUnit());

		return values;
	}

	public void fetchAll() {
		getAllFuelData();
		getAllPreferences();
		getAllCars();
	}

	public void clearCache() {
		fuelCache.clear();
		preferencesCache.clear();
		carCache.clear();
	}
}
