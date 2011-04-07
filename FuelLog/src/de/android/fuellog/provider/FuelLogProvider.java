package de.android.fuellog.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;
import de.android.fuellog.database.Database;
import de.android.fuellog.database.tables.Car;
import de.android.fuellog.database.tables.Fuel;
import de.android.fuellog.database.tables.Preferences;

public class FuelLogProvider extends ContentProvider {
    private static final String TAG = "de.android.fuellog.provider.FuelLogProvider";
    private static final boolean DEBUG = false;

    public static final String AUTHORITY = "de.android.fuellog.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri FUEL_CONTENT_URI = Uri.withAppendedPath(FuelLogProvider.AUTHORITY_URI,
            FuelContent.CONTENT_PATH);
    public static final Uri PREFERENCES_CONTENT_URI = Uri.withAppendedPath(FuelLogProvider.AUTHORITY_URI,
            PreferencesContent.CONTENT_PATH);
    public static final Uri CAR_CONTENT_URI = Uri.withAppendedPath(FuelLogProvider.AUTHORITY_URI,
            PreferencesContent.CONTENT_PATH);

    private static final int FUEL_DIR = 1;
    private static final int FUEL_ID = 2;
    private static final int PREFERENCES_DIR = 3;
    private static final int PREFERENCES_ID = 4;
    private static final int CAR_DIR = 5;
    private static final int CAR_ID = 6;

    private static final UriMatcher URI_MATCHER;
    private Database database = null;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, FuelContent.CONTENT_PATH, FUEL_DIR);
        URI_MATCHER.addURI(AUTHORITY, FuelContent.CONTENT_PATH + "/#", FUEL_ID);

        URI_MATCHER.addURI(AUTHORITY, PreferencesContent.CONTENT_PATH, PREFERENCES_DIR);
        URI_MATCHER.addURI(AUTHORITY, PreferencesContent.CONTENT_PATH + "/#", PREFERENCES_ID);

        URI_MATCHER.addURI(AUTHORITY, CarContent.CONTENT_PATH, CAR_DIR);
        URI_MATCHER.addURI(AUTHORITY, CarContent.CONTENT_PATH + "/#", CAR_ID);
    }

    public static final class FuelContent implements BaseColumns {
        public static final String CONTENT_PATH = "fuel";

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.android.fuel";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.android.fuel";

        public static final String[] ALL_COLUMNS = Fuel.ALL_COLUMNS;
    }

    public static final class PreferencesContent implements BaseColumns {
        public static final String CONTENT_PATH = "preferences";

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.android.preferences";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.android.preferences";

        public static final String[] ALL_COLUMNS = Preferences.ALL_COLUMNS;
    }

    public static final class CarContent implements BaseColumns {
        public static final String CONTENT_PATH = "car";

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.android.car";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.android.car";

        public static final String[] ALL_COLUMNS = Car.ALL_COLUMNS;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase dbConnection = database.getWritableDatabase();
        int deleteCount = 0;
        try {
            switch (URI_MATCHER.match(uri)) {
            case FUEL_DIR:
                deleteCount = dbConnection.delete(Fuel.TABLE_NAME, selection, selectionArgs);
                break;
            case FUEL_ID:
                deleteCount = dbConnection.delete(Fuel.TABLE_NAME, Fuel.WHERE_ID_EQUALS, new String[] { uri
                        .getPathSegments().get(1) });
                break;
            case PREFERENCES_DIR:
                deleteCount = dbConnection.delete(Preferences.TABLE_NAME, selection, selectionArgs);
                break;
            case PREFERENCES_ID:
                deleteCount = dbConnection.delete(Preferences.TABLE_NAME, Preferences.WHERE_ID_EQUALS,
                        new String[] { uri.getPathSegments().get(1) });
                break;

            case CAR_DIR:
                deleteCount = dbConnection.delete(Car.TABLE_NAME, selection, selectionArgs);
                break;
            case CAR_ID:
                deleteCount = dbConnection.delete(Car.TABLE_NAME, Car.WHERE_ID_EQUALS, new String[] { uri
                        .getPathSegments().get(1) });
                break;
            default:
                Log.d(TAG, "Unknown Uri");
                break;
            }
        } finally {
            dbConnection.close();
        }

        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
        if (DEBUG) {
            Log.d(TAG, "Debug");
        }
        switch (URI_MATCHER.match(uri)) {
        case FUEL_DIR:
            return FuelContent.CONTENT_TYPE;
        case FUEL_ID:
            return FuelContent.CONTENT_ITEM_TYPE;
        case PREFERENCES_DIR:
            return PreferencesContent.CONTENT_TYPE;
        case PREFERENCES_ID:
            return PreferencesContent.CONTENT_ITEM_TYPE;
        case CAR_DIR:
            return CarContent.CONTENT_TYPE;
        case CAR_ID:
            return CarContent.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase dbConnection = database.getWritableDatabase();
        dbConnection.beginTransaction();
        try {
            switch (URI_MATCHER.match(uri)) {
            case FUEL_DIR:
            case FUEL_ID:
                final long fuelId = dbConnection.insertOrThrow(Fuel.TABLE_NAME, null, values);
                final Uri newFuel = ContentUris.withAppendedId(FUEL_CONTENT_URI, fuelId);
                getContext().getContentResolver().notifyChange(newFuel, null);
                dbConnection.setTransactionSuccessful();
                return newFuel;
            case PREFERENCES_DIR:
            case PREFERENCES_ID:
                final long preferencesId = dbConnection.insertOrThrow(Preferences.TABLE_NAME, null, values);
                final Uri newPreferences = ContentUris.withAppendedId(PREFERENCES_CONTENT_URI, preferencesId);
                getContext().getContentResolver().notifyChange(newPreferences, null);
                dbConnection.setTransactionSuccessful();
                return newPreferences;
            case CAR_DIR:
            case CAR_ID:
                final long carId = dbConnection.insertOrThrow(Car.TABLE_NAME, null, values);
                final Uri newCar = ContentUris.withAppendedId(CAR_CONTENT_URI, carId);
                getContext().getContentResolver().notifyChange(newCar, null);
                dbConnection.setTransactionSuccessful();
                return newCar;
            default:
                Log.d(TAG, "Unknown Uri");
                break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Insert Exception: " + values.toString(), e);
        } finally {
            dbConnection.endTransaction();
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        database = new Database(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        final SQLiteDatabase dbConnection = database.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
        case FUEL_ID:
            queryBuilder.appendWhere(Fuel.ID + "=" + uri.getPathSegments().get(1));
        case FUEL_DIR:
            queryBuilder.setTables(Fuel.TABLE_NAME);
            break;
        case PREFERENCES_ID:
            queryBuilder.appendWhere(Preferences.ID + "=" + uri.getPathSegments().get(1));
        case PREFERENCES_DIR:
            queryBuilder.setTables(Preferences.TABLE_NAME);
            break;
        case CAR_ID:
            queryBuilder.appendWhere(Car.ID + "=" + uri.getPathSegments().get(1));
        case CAR_DIR:
            queryBuilder.setTables(Car.TABLE_NAME);
            break;
        default:
            Log.d(TAG, "Unknown Uri");
            break;
        }

        Cursor cursor = queryBuilder.query(dbConnection, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase dbConnection = database.getWritableDatabase();
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
        case FUEL_DIR:
            count = dbConnection.update(Fuel.TABLE_NAME, values, selection, selectionArgs);
            break;
        case FUEL_ID:
            Long fuelId = ContentUris.parseId(uri);
            count = dbConnection.update(Fuel.TABLE_NAME, values, Fuel.ID + "=" + fuelId
                    + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);
            break;
        case PREFERENCES_DIR:
            count = dbConnection.update(Preferences.TABLE_NAME, values, selection, selectionArgs);
            break;
        case PREFERENCES_ID:
            Long preferencesId = ContentUris.parseId(uri);
            count = dbConnection.update(Preferences.TABLE_NAME, values, Preferences.ID + "=" + preferencesId
                    + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);
            break;
        case CAR_DIR:
            count = dbConnection.update(Car.TABLE_NAME, values, selection, selectionArgs);
            break;
        case CAR_ID:
            Long carId = ContentUris.parseId(uri);
            count = dbConnection.update(Car.TABLE_NAME, values, Car.ID + "=" + carId
                    + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);
            break;
        default:
            Log.d(TAG, "Unknown Uri");
            break;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }
}