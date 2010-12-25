package de.android.fuellog.activity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import de.android.fuellog.R;
import de.android.fuellog.activity.tasks.LogFileImporterTask;
import de.android.fuellog.activity.tasks.PreferencesHelperTask;
import de.android.fuellog.activity.view.Dialogs;
import de.android.fuellog.consumer.FuelLogDAO;
import de.android.fuellog.model.FuelData;
import de.android.fuellog.model.PreferencesData;
import de.android.fuellog.util.Calculations;
import de.android.fuellog.util.Values;

public class Dashboard extends Activity {
    private static final String TAG = "de.android.fuellog.activity.Dashboard";
    private static final boolean DEBUG = true;

    private static final int FIRST_START_DIALOG = 1;
    private static final int PREFERENCES_WARNING_DIALOG = 2;
    private static boolean started = false;
    private TextView totalCostText;
    private TextView lastDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (DEBUG) {

        }

        if (firstRun()) {
            try {
                if (!preferencesSet()) {
                    showDialog(PREFERENCES_WARNING_DIALOG);
                }
                new LogFileImporterTask(this).execute().get();
                showDialog(FIRST_START_DIALOG);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            started = true;
        } else if (!preferencesSet()) {
            if (!started) {
                started = true;
                showDialog(PREFERENCES_WARNING_DIALOG);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        totalCostText = (TextView) findViewById(R.id.dashboard_total_money_text);
        lastDateText = (TextView) findViewById(R.id.dashboard_last_fillup_date_text);

        updateDashboard();
    }

    public final void onClickSearch(final View view) {
        if (DEBUG) {

        }
    }

    public final void onClickFillUp(final View view) {
        if (DEBUG) {

        }
        startActivityForResult(new Intent(this, FillUp.class), Values.QUIT_APPLICATION_CODE);
    }

    public final void onClickHistory(final View view) {
        if (DEBUG) {

        }
        startActivityForResult(new Intent(this, History.class), Values.QUIT_APPLICATION_CODE);
    }

    public final void onClickStatistics(final View view) {
        if (DEBUG) {

        }
        startActivityForResult(new Intent(this, Statistic.class), Values.QUIT_APPLICATION_CODE);
    }

    public final void onClickPreferences(final View view) {
        if (DEBUG) {

        }
        startActivityForResult(new Intent(this, Preferences.class), Values.PREFENCES_BACK_CODE);
    }

    @Override
    protected void onResume() {
        if (DEBUG) {

        }
        super.onResume();
        updateDashboard();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case FIRST_START_DIALOG:
            return Dialogs.getFirstStartDialog(this);
        case PREFERENCES_WARNING_DIALOG:
            return Dialogs.getPreferencesWarningDialog(this);
        default:
            break;
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected final void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (DEBUG) {

        }
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

        switch (requestCode) {
        case Values.PREFENCES_BACK_CODE:
            refreshPreferences();
            updateDashboard();
            break;
        default:
            break;
        }
    }

    private void refreshPreferences() {
        try {
            FuelLogDAO.getInstance(Dashboard.this).setMainPreferences(
                    new PreferencesHelperTask(Dashboard.this).execute().get());
        } catch (InterruptedException e) {
            Log.e(TAG, "Error!", e);
        } catch (ExecutionException e) {
            Log.e(TAG, "Error!", e);
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
        if (DEBUG) {

        }
        Intent quitApplication = new Intent();
        quitApplication.putExtra(Values.QUIT_APP_RESULT, Values.QUIT_APPLICATION_CODE);
        setResult(Values.QUIT_APPLICATION_CODE, quitApplication);
        finish();
    }

    private void updateDashboard() {
        PreferencesData pref = FuelLogDAO.getInstance(this).getMainPreferences();
        int settings = 0;
        String pattern = "dd.MM.yyyy";

        if (pref != null) {
            settings = pref.getUnit();
            pattern = pref.getDatePattern();
        }

        DecimalFormat formater = new DecimalFormat("#0.00 " + FuelData.currencyUnit);

        totalCostText.setText(getString(R.string.dashboard_total) + " "
                + formater.format(Calculations.getTotalCosts(this)));

        Date date = Calculations.getLastFuelDate(this);
        if (date == null) {
            lastDateText.setText(getString(R.string.dashboard_last_fill_up) + " "
                    + getString(R.string.dashboard_no_fill_up));
        } else {
            lastDateText.setText(getString(R.string.dashboard_last_fill_up) + " "
                    + new SimpleDateFormat(pattern).format(date));
        }
    }

    private boolean firstRun() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Values.PREFERENCES_NAME, 0);

        if (!pref.contains("firstRun") && pref.getBoolean("firstRun", true)) {
            Log.d(TAG, "FIRST RUN! false");
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firstRun", false);
            editor.commit();
            return true;
        }

        return false;
    }

    private boolean preferencesSet() {
        refreshPreferences();
        return FuelLogDAO.getInstance(this).getMainPreferences().getFirstDistance() > 0;
    }
}
