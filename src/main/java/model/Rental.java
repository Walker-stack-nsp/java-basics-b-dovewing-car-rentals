package model;

import java.math.BigDecimal;

public class Rental {

    private final int id;
    private final Renter renter;
    private final Vehicle vehicle;
    private final int days;
    private final BigDecimal cost;
    private final BigDecimal deposit;
    private boolean returned;

    public Rental(int id, Renter renter, Vehicle vehicle, int days, BigDecimal cost, BigDecimal deposit) {
        this.id = id;
        this.renter = renter;
        this.vehicle = vehicle;
        this.days = days;
        this.cost = cost;
        this.deposit = deposit;
        this.returned = false;
    }


    public int getId() {
        return id;
    }

    public Renter getRenter() {
        return renter;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getDays() {
        return days;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public boolean isReturned() {
        return returned;
    }

    public void markReturned() {
        if (returned) {
            throw new IllegalStateException("Rental #" + id + " has already been returned");
        }
        returned = true;
    }
}
