package de.android.fuellog.activity.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import de.android.fuellog.consumer.FuelLogDAO;
import de.android.fuellog.model.FuelData;

public class LogFileImporterTask extends AsyncTask<Void, Void, Boolean> {

    private Context ctx;

    public LogFileImporterTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            File logFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/fuellog");
            if (!logFile.exists()) {
                Log.d("LogFileImporter", "File doesn't exists");
                return false;
            } else {
                BufferedReader br = new BufferedReader(new FileReader(logFile));
                String read;
                read = br.readLine();

                while (read != null) {
                    Log.d("LogFileImporter", read);
                    StringTokenizer st = new StringTokenizer(read, ";");
                    DecimalFormat formater = new DecimalFormat("#0.00");
                    FuelData data = new FuelData();

                    for (int i = 0; st.hasMoreTokens(); i++) {
                        switch (i) {
                        case 0:
                            data.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(st.nextToken()));
                            break;
                        case 1:
                            data.setAmountCosts(formater.parse(st.nextToken()).doubleValue());
                            break;
                        case 2:
                            data.setCosts(formater.parse(st.nextToken()).doubleValue());
                            break;
                        case 4:
                            data.setCurrentDistance(formater.parse(st.nextToken()).doubleValue());
                            break;
                        case 5:
                            data.setComment(st.nextToken());
                            break;
                        default:
                            st.nextToken();
                            break;
                        }
                    }
                    FuelLogDAO.getInstance(ctx).createFuelData(data);
                    read = br.readLine();
                }
                logFile.renameTo(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/fuellog.old"));

                return true;
            }
        } catch (Exception e) {
            Log.e("LogFileImporterTask", "error", e);
            return false;
        }

    }
}
