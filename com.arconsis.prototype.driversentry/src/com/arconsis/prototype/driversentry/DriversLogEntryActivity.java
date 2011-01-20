package com.arconsis.prototype.driversentry;

import java.io.File;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.arconsis.prototype.driversentry.model.DriversLogEntry;

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

    private DriversLogEntry data;
    private File tmpFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverslogentry);

        data = new DriversLogEntry();

        dateText = (EditText) findViewById(R.id.driverslogentryDate);
        dateText.setText(new SimpleDateFormat(getString(R.string.datePattern)).format(data.getDate()));
        timeText = (EditText) findViewById(R.id.driverslogentryTime);
        timeText.setText(new SimpleDateFormat(getString(R.string.timePattern)).format(data.getDate()));
        locationText = (EditText) findViewById(R.id.driverslogentryLocation);
        mileageText = (EditText) findViewById(R.id.driverslogentryMileage);
        descriptionText = (EditText) findViewById(R.id.driverslogentryComment);

        // EditText text = (EditText) findViewById(R.id.driverslogentryLocation);
        // Drawable x = getResources().getDrawable(android.R.drawable.ic_menu_mylocation);
        // x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        //
        // text.setHint(R.string.driverslogentry_location);
        // text.setCompoundDrawables(null, null, x, null);
        //
        // ImageView image = new ImageView(this);
        // image.setImageDrawable(x);
        // text.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // Log.d(TAG, "Location Press Test!!");
        // }
        // });

    }

    public final void onClickSaveDriversLogEntry(final View view) {
        if (DEBUG) {
            Log.d(TAG, "Debug");
        }
        // SELECT_IMAGE_DIALOG
    }

    public final void onClickTakePicture(final View view) {
        if (DEBUG) {
            Log.d(TAG, "Debug");
        }

        if (!ControlValues.SDCARD_FOLDER.exists()) {
            ControlValues.SDCARD_FOLDER.mkdir();
        }

        tmpFile = new File(ControlValues.SDCARD_FOLDER.getAbsolutePath() + "/" + System.currentTimeMillis() + ".png");

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(tmpFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, ControlValues.CAMERA_REQUEST_CODE);
    }

    public final void onClickPictures(final View view) {
        if (DEBUG) {
            Log.d(TAG, "Debug");
        }
        showDialog(SELECT_IMAGE_DIALOG);
    }

    public final void onClickTime(final View view) {
        if (DEBUG) {
            Log.d(TAG, "Debug");
        }
        showDialog(TIME_PICKER_DIALOG);
    }

    public final void onClickDate(final View view) {
        if (DEBUG) {
            Log.d(TAG, "Debug");
        }
        showDialog(DATE_PICKER_DIALOG);
    }

    public final void onClickLocation(final View view) {
        if (DEBUG) {
            Log.d(TAG, "Debug");
        }
    }

    /**
     * onClickSaveDriversLogEntry onClickTakePicture onClickTime onClickDate onClickLocation
     */

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

    int selection = -1;

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
        case SELECT_IMAGE_DIALOG:
            ((AlertDialog) dialog).getListView().setSelection(-1);
            break;
        default:
            break;
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_PICKER_DIALOG:
            int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(data.getDate()));
            int monthOfYear = Integer.parseInt(new SimpleDateFormat("MM").format(data.getDate())) - 1;
            int dayOfMonth = Integer.parseInt(new SimpleDateFormat("dd").format(data.getDate()));
            OnDateSetListener dateListener = new OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    try {
                        data.getDate().setYear(year - 1900);
                        data.getDate().setMonth(monthOfYear);
                        data.getDate().setDate(dayOfMonth);
                        dateText.setText(new SimpleDateFormat(getString(R.string.datePattern)).format(data.getDate()));
                    } catch (Exception e) {
                        Log.e(TAG, "Error Date Dialog", e);
                    }
                }
            };
            return new DatePickerDialog(this, dateListener, year, monthOfYear, dayOfMonth);
        case TIME_PICKER_DIALOG:
            int hours = Integer.parseInt(new SimpleDateFormat("HH").format(data.getDate()));
            int minutes = Integer.parseInt(new SimpleDateFormat("mm").format(data.getDate()));
            OnTimeSetListener timeListener = new OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    try {
                        data.getDate().setHours(hourOfDay);
                        data.getDate().setMinutes(minute);
                        timeText.setText(new SimpleDateFormat(getString(R.string.timePattern)).format(data.getDate()));
                    } catch (Exception e) {
                        Log.e(TAG, "Error Date Dialog", e);
                    }
                }
            };
            return new TimePickerDialog(this, timeListener, hours, minutes, true);
        case SELECT_IMAGE_DIALOG:
            if (data.getPictures().size() == 0) {
                Toast.makeText(this, R.string.driverslogentry_nopictures_toast, Toast.LENGTH_LONG).show();
            } else {
                String[] items = new String[data.getPictures().size()];
                for (int i = 0; i < items.length; i++) {
                    items[0] = data.getPictures().get(i).getName();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle(R.string.driverslogentry_picture_title);
                builder.setSingleChoiceItems(items, selection, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        selection = item;
                    }
                });

                builder.setPositiveButton(R.string.driverslogentry_dialog_picture_view,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "VIEW!" + selection);
                                Intent intent = new Intent();
                                intent.setAction(android.content.Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.fromFile(data.getPictures().get(selection)), "image/png");
                                startActivity(intent);
                                dialog.cancel();
                                selection = -1;
                            }
                        });
                builder.setNeutralButton(R.string.driverslogentry_dialog_picture_delete,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "DELET!" + selection + "-"
                                        + data.getPictures().get(selection).getAbsolutePath());
                                if (data.getPictures().get(selection).delete()) {
                                    data.getPictures().remove(selection);
                                    Toast.makeText(DriversLogEntryActivity.this,
                                            R.string.driverslogentry_delete_pictures_toast, Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                    selection = -1;
                                } else {
                                    Toast.makeText(DriversLogEntryActivity.this,
                                            R.string.driverslogentry_delete_fail_pictures_toast, Toast.LENGTH_LONG)
                                            .show();
                                    dialog.cancel();
                                    selection = -1;
                                }
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "CANCEL!");
                        dialog.cancel();
                        selection = -1;
                    }
                });
                return builder.create();
            }
        default:
            break;
        }

        return super.onCreateDialog(id);
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
            tmpFile.delete();
            tmpFile = null;
        } else if (requestCode == ControlValues.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            this.data.getPictures().add(tmpFile);
            tmpFile = null;
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
}
