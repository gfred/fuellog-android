package de.android.fuellog.database.tables;

public interface Preferences {
	String TABLE_NAME = "preferences";

	String ID = "_id";
	String FIRST_DISTANCE = "firstdistance";
	String CURRENCY = "currency";
	String UNIT = "unit";
	String DATEPATTERN = "datepattern";

	String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FIRST_DISTANCE + " INTEGER, " + CURRENCY + " INTEGER, " + UNIT + " INTEGER, " + DATEPATTERN + " TEXT )";

	String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " ( " + FIRST_DISTANCE + "," + CURRENCY + "," + UNIT + ","
			+ DATEPATTERN + " ) VALUES (?,?,?,?)";

	String WHERE_ID_EQUALS = ID + "=?";

	String[] ALL_COLUMNS = new String[] { ID, FIRST_DISTANCE, CURRENCY, UNIT, DATEPATTERN };
}
