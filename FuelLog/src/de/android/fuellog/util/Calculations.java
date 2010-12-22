package de.android.fuellog.util;

import java.util.Date;

import android.content.Context;
import de.android.fuellog.consumer.FuelLogDAO;
import de.android.fuellog.model.FuelData;

public class Calculations {

	public static double getTotalCosts(Context ctx) {
		FuelLogDAO dao = FuelLogDAO.getInstance(ctx);
		double total = 0;
		for (FuelData data : dao.getAllFuelData()) {
			total += data.getAmountCosts();
		}
		return total;
	}

	public static Date getLastFuelDate(Context ctx) {
		FuelLogDAO dao = FuelLogDAO.getInstance(ctx);
		Date date = null;
		for (FuelData data : dao.getAllFuelData()) {
			if (date == null) {
				date = data.getDate();
			} else if (date.after(data.getDate())) {
				date = data.getDate();
			}

		}
		return date;
	}

}
