package de.android.fuellog.database.tables;

public interface Car {
	String TABLE_NAME = "fuel";

	String ID = "_id";
	String CAR_NAME = "carname";
	String LICENSE_NO = "licenseNo";

	String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAR_NAME
			+ " TEXT, " + LICENSE_NO + " TEXT )";

	String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " ( " + CAR_NAME + "," + LICENSE_NO + " ) VALUES (?,?)";

	String WHERE_ID_EQUALS = ID + "=?";

	String[] ALL_COLUMNS = new String[] { ID, CAR_NAME, LICENSE_NO };
}
