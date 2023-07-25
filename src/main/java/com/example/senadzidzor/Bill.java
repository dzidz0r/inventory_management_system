package com.example.senadzidzor;

import javafx.beans.property.*;

import java.sql.Date;

public class Bill {
    private final IntegerProperty billID;
    private final ObjectProperty<Date> date;
    private final DoubleProperty totalAmount;

    public Bill(int billID, Date date, double totalAmount) {
        this.billID = new SimpleIntegerProperty(billID);
        this.date = new SimpleObjectProperty<>(date);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
    }

    public int getBillID() {
        return billID.get();
    }

    public void setBillID(int billID) {
        this.billID.set(billID);
    }

    public IntegerProperty billIDProperty() {
        return billID;
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    public DoubleProperty totalAmountProperty() {
        return totalAmount;
    }
}
