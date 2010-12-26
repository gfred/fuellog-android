package de.android.fuellog.activity.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import de.android.fuellog.R;
import de.android.fuellog.activity.Dashboard;

public class Dialogs {

    public static AlertDialog getFirstStartDialog(final Context ctx) {
        AlertDialog.Builder changeUserDialog = new AlertDialog.Builder(ctx);
        changeUserDialog.setMessage(R.string.first_start_msg).setTitle(R.string.first_start_title)
                .setIcon(android.R.drawable.ic_menu_info_details)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.cancel();
                    }
                });
        return changeUserDialog.create();
    }

    public static AlertDialog getPreferencesWarningDialog(final Dashboard dashboard) {
        AlertDialog.Builder changeUserDialog = new AlertDialog.Builder(dashboard);
        changeUserDialog.setMessage(R.string.no_preferences_msg).setTitle(R.string.no_preferences_title)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dashboard.onClickPreferences(dashboard.getCurrentFocus());
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.cancel();
                    }
                });
        return changeUserDialog.create();
    }
}
