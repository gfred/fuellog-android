package com.arconsis.prototype.driversentry;

import java.io.File;

import android.os.Environment;

/**
 * Static values to control the live cycle of the application.
 * 
 * @author Frederik Goetz
 * @date 18.10.2010
 */
public interface ControlValues {

    /**
     * Server URL
     */
    // String SERVER_URL = "https://team.arconsis.com/fleetmanager-service";
    // //"http://10.0.2.2:8080/fleetmanager-service";

    /**
     * Quit Application INT Value for Return Result.
     */
    int QUIT_APPLICATION = 1;

    /**
     * Quit Application String Value for Return Result.
     */
    String QUIT_APP_RESULT = "QUIT";

    /**
     * Camera Request Code.
     */
    int CAMERA_REQUEST_CODE = 2;

    /**
     * Value of option entry business voyage.
     */
    int BUSINESS_VOYAGE = 0;

    /**
     * Value of option driver's logbook entry
     */
    int DRIVERS_LOG_ENTRY = 1;

    /**
     * Value of option entry fuel.
     */
    int FUEL = 2;

    /**
     * Value of option entry case of damage.
     */
    int CASE_OF_DAMAGE = 3;

    /**
     * Value of option entry other costs.
     */
    int OTHER_COSTS = 4;

    /**
     * Value of option entry other bookings.
     */
    int OTHER_BOOKINGS = 5;

    /**
     * SD Card Folder
     */
    File SDCARD_FOLDER = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Fleetmanager");
}
