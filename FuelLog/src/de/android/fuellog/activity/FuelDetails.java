package de.android.fuellog.activity;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.android.fuellog.R;
import de.android.fuellog.model.FuelData;
import de.android.fuellog.util.Values;

public class FuelDetails extends Activity {
    private static final String TAG = "de.android.fuellog.activity.FuelDetails";
    private static final boolean DEBUG = false;

    private Button saveButton;
    private Button editButton;
    private Button cancelButton;

    private TextView dateText;
    private TextView distanceText;
    private TextView costsText;
    private TextView totalText;
    private TextView commentText;
    private TextView locationText;
    private TextView volumeText;

    private TextView stat1Text;
    private TextView stat2Text;
    private TextView stat3Text;

    private EditText dateEdit;
    private EditText distanceStartEdit;
    private EditText distanceEndEdit;
    private EditText costsEdit;
    private EditText totalEdit;
    private EditText commentEdit;
    private EditText locationEdit;

    private TableRow distanceStartRow;
    private TableRow distanceEndRow;
    private TableRow distanceDistanceRow;
    private TableRow volumeRow;

    private LinearLayout statisticTable;

    private FuelData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillup_details);
        data = (FuelData) getIntent().getSerializableExtra(Values.SELECTED_FUEL_DATA);

        saveButton = (Button) findViewById(R.id.fillupdetails_save_button);
        editButton = (Button) findViewById(R.id.fillupdetails_edit_button);
        cancelButton = (Button) findViewById(R.id.fillupdetails_cancel_button);

        dateText = (TextView) findViewById(R.id.fillupdetails_date_text);
        distanceText = (TextView) findViewById(R.id.fillupdetails_mileage_text);
        costsText = (TextView) findViewById(R.id.fillupdetails_price_text);
        totalText = (TextView) findViewById(R.id.fillupdetails_amount_text);
        commentText = (TextView) findViewById(R.id.fillupdetails_comment_text);
        locationText = (TextView) findViewById(R.id.fillupdetails_location_text);
        volumeText = (TextView) findViewById(R.id.fillupdetails_volume_text);

        stat1Text = (TextView) findViewById(R.id.fillupdetails_stat1_text);
        stat2Text = (TextView) findViewById(R.id.fillupdetails_stat2_text);
        stat3Text = (TextView) findViewById(R.id.fillupdetails_stat3_text);

        dateEdit = (EditText) findViewById(R.id.fillupdetails_date_edit);
        distanceStartEdit = (EditText) findViewById(R.id.fillupdetails_distancestart_edit);
        distanceEndEdit = (EditText) findViewById(R.id.fillupdetails_distanceend_edit);
        costsEdit = (EditText) findViewById(R.id.fillupdetails_price_edit);
        totalEdit = (EditText) findViewById(R.id.fillupdetails_amount_edit);
        commentEdit = (EditText) findViewById(R.id.fillupdetails_comment_edit);
        locationEdit = (EditText) findViewById(R.id.fillupdetails_location_edit);

        volumeRow = (TableRow) findViewById(R.id.fillupdetails_volume_row);
        distanceStartRow = (TableRow) findViewById(R.id.fillupdetails_distance_start);
        distanceEndRow = (TableRow) findViewById(R.id.fillupdetails_distance_end);
        distanceDistanceRow = (TableRow) findViewById(R.id.fillupdetails_distance);

        statisticTable = (LinearLayout) findViewById(R.id.fillupdetails_statistics);

        setTextOnlyValues();
    }

    public final void onClickHome(final View view) {
        startActivityForResult(new Intent(this, Dashboard.class), Values.QUIT_APPLICATION_CODE);
        closeActivity();
    }

    public final void onClickFillUp(final View view) {
        startActivityForResult(new Intent(this, FillUp.class), Values.QUIT_APPLICATION_CODE);
    }

    public final void onClickSearch(final View view) {

    }

    public final void onClickEdit(final View view) {
        setEditiableViewElements();
    }

    public final void onClickSave(final View view) {

        setTextOnlyViewElements();
    }

    public final void onClickCancel(final View view) {

        setTextOnlyViewElements();
    }

    public final void onClickDate(final View view) {

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
            closeActivity();
            break;
        default:
            break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void closeActivity() {
        Intent quitApplication = new Intent();
        quitApplication.putExtra(Values.QUIT_APP_RESULT, Values.QUIT_APPLICATION_CODE);
        setResult(Values.QUIT_APPLICATION_CODE, quitApplication);
        finish();
    }

    @Override
    protected final void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
        case Values.QUIT_APPLICATION_CODE:
            if (data != null && data.getExtras().getInt(Values.QUIT_APP_RESULT) == Values.QUIT_APPLICATION_CODE) {
                closeActivity();
            }
            break;
        default:
            break;
        }
    }

    private void setTextOnlyValues() {
        DecimalFormat formater = new DecimalFormat("#0.00");
        dateText.setText(data.getDateString());
        costsText.setText(formater.format(data.getCosts()) + " " + FuelData.currencyUnit);
        totalText.setText(formater.format(data.getAmountCosts()) + " " + FuelData.currencyUnit);
        commentText.setText(data.getComment());
        locationText.setText(data.getLocation());

        double distance = data.getCurrentDistance() - data.getLastDistance();
        distanceText.setText(formater.format(distance) + " " + FuelData.measureUnit);

        double volume = data.getAmountCosts() / data.getCosts();
        volumeText.setText(formater.format(volume) + " " + FuelData.volumeUnit);

        stat1Text.setText(formater.format(volume / distance * 100) + " " + FuelData.volumeUnit + " / 100"
                + FuelData.measureUnit);
        stat2Text
                .setText(formater.format(distance / volume) + " " + FuelData.measureUnit + " / " + FuelData.volumeUnit);
        stat3Text
                .setText(formater.format(volume / distance) + " " + FuelData.volumeUnit + " / " + FuelData.measureUnit);
    }

    private void setEditiableViewElements() {
        dateText.setVisibility(View.GONE);
        distanceText.setVisibility(View.GONE);
        costsText.setVisibility(View.GONE);
        totalText.setVisibility(View.GONE);
        commentText.setVisibility(View.GONE);
        locationText.setVisibility(View.GONE);
        distanceDistanceRow.setVisibility(View.GONE);
        volumeRow.setVisibility(View.GONE);
        statisticTable.setVisibility(View.GONE);

        distanceStartRow.setVisibility(View.VISIBLE);
        distanceEndRow.setVisibility(View.VISIBLE);
        dateEdit.setVisibility(View.VISIBLE);
        distanceStartEdit.setVisibility(View.VISIBLE);
        distanceEndEdit.setVisibility(View.VISIBLE);
        costsEdit.setVisibility(View.VISIBLE);
        totalEdit.setVisibility(View.VISIBLE);
        commentEdit.setVisibility(View.VISIBLE);
        locationEdit.setVisibility(View.VISIBLE);

        editButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    private void setTextOnlyViewElements() {
        dateText.setVisibility(View.VISIBLE);
        distanceText.setVisibility(View.VISIBLE);
        costsText.setVisibility(View.VISIBLE);
        totalText.setVisibility(View.VISIBLE);
        commentText.setVisibility(View.VISIBLE);
        locationText.setVisibility(View.VISIBLE);
        distanceDistanceRow.setVisibility(View.VISIBLE);
        volumeRow.setVisibility(View.VISIBLE);
        statisticTable.setVisibility(View.VISIBLE);

        distanceStartRow.setVisibility(View.GONE);
        distanceEndRow.setVisibility(View.GONE);
        dateEdit.setVisibility(View.GONE);
        distanceStartEdit.setVisibility(View.GONE);
        distanceEndEdit.setVisibility(View.GONE);
        costsEdit.setVisibility(View.GONE);
        totalEdit.setVisibility(View.GONE);
        commentEdit.setVisibility(View.GONE);
        locationEdit.setVisibility(View.GONE);

        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
    }
}
