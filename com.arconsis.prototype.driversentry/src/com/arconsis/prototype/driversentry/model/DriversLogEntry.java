package com.arconsis.prototype.driversentry.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriversLogEntry {

    private Date date;
    private List<File> pictures = new ArrayList<File>();

    public DriversLogEntry() {
        this.date = new Date();
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<File> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<File> pictures) {
        this.pictures = pictures;
    }

}
