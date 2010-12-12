package de.android.fuellog.consumer;

import android.content.Context;
import de.android.fuellog.model.FuelData;
import de.android.fuellog.model.PreferencesData;

public class DAOUtils {

	public static Double getLastDistance(Context ctx) {
		FuelLogDAO dao = FuelLogDAO.getInstancte(ctx);
		FuelData tmp = null;

		for (FuelData fuel : dao.getAllFuelData()) {
			if (tmp == null) {
				tmp = fuel;
			} else if (tmp.getCurrentDistance() < fuel.getCurrentDistance()) {
				tmp = fuel;
			}
		}

		if (tmp != null && tmp.getCurrentDistance() != null && tmp.getCurrentDistance() > 0) {
			return tmp.getCurrentDistance();
		} else {
			PreferencesData prefTmp = null;
			for (PreferencesData pref : dao.getAllPreferences()) {
				if (prefTmp == null) {
					prefTmp = pref;
				} else if (pref.getFirstDistance() > prefTmp.getFirstDistance()) {
					prefTmp = pref;
				}
			}

			if (prefTmp != null && prefTmp.getFirstDistance() != null && prefTmp.getFirstDistance() > 0) {
				return prefTmp.getFirstDistance();
			} else {
				return null;
			}
		}
	}
}
