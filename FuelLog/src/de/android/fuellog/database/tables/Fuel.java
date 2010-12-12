package de.android.fuellog.database.tables;

public interface Fuel {
	String TABLE_NAME = "fuel";

	String ID = "_id";
	String FUEL_DATE = "fueldate";
	String LAST_DISTANCE = "lastdistance";
	String CURRENT_DISTANCE = "currentdistance";
	String COSTS = "costs";
	String AMOUNT_COSTS = "amountcosts";
	String COMMENT = "comment";
	String LOCATION = "location";
	String PICTURE_PATH = "picturepath";
	String CAR_ID = "cardid";

	String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FUEL_DATE
			+ " INTEGER, " + LAST_DISTANCE + " REAL, " + CURRENT_DISTANCE + " REAL, " + COSTS + " REAL, "
			+ AMOUNT_COSTS + " REAL, " + COMMENT + " TEXT, " + LOCATION + " TEXT, " + PICTURE_PATH + " TEXT, " + CAR_ID
			+ " INTEGER )";

	String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " ( " + FUEL_DATE + "," + LAST_DISTANCE + "," + CURRENT_DISTANCE
			+ "," + COSTS + "," + AMOUNT_COSTS + "," + COMMENT + "," + LOCATION + "," + PICTURE_PATH + "," + CAR_ID
			+ " ) VALUES (?,?,?,?,?,?,?,?,?)";

	String WHERE_ID_EQUALS = ID + "=?";

	String[] ALL_COLUMNS = new String[] { ID, FUEL_DATE, LAST_DISTANCE, CURRENT_DISTANCE, COSTS, AMOUNT_COSTS, COMMENT,
			LOCATION, PICTURE_PATH, CAR_ID };
}
