package de.android.fuellog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.android.fuellog.database.tables.Car;
import de.android.fuellog.database.tables.Fuel;
import de.android.fuellog.database.tables.Preferences;

public class Database extends SQLiteOpenHelper {
	private static final String TAG = "de.android.fuellog.database.Database";
	private static final Boolean DEBUG = false;

	private static final String DATABASE_NAME = "fuellog.db";
	private static final int DATABASE_VERSION = 1;

	public Database(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Fuel.SQL_CREATE);
		db.execSQL(Preferences.SQL_CREATE);
		db.execSQL(Car.SQL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(Fuel.SQL_DROP);
		db.execSQL(Preferences.SQL_DROP);
		db.execSQL(Car.SQL_DROP);
		onCreate(db);
	}

}
