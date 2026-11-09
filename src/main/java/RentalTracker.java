// Dovewing Car Rentals - fleet and rental tracker
// Original program, left behind by a former colleague. It works, but the front
// desk keeps a paper backup because they do not trust it. Run it first to see why,
// and look closely at the active rentals in the end-of-day report.

import java.util.*;

public class RentalTracker {

    public static void main(String[] args) {
        
    }

    private static void rent(Depot depot, Renter renter, Vehicle vehicle, int days) {
        try {
            Rental rental = depot.rent(renter, vehicle, days);
            System.out.println("Rented " + describe(vehicle) + " to " + renter.name()
                    + " for " + days + " days, cost GHS " + format(rental.getCost())
                    + ", deposit GHS " + format(rental.getDeposit()));
        } catch (RentalException refusal) {
            System.out.println("Cannot rent " + describe(vehicle) + " to " + renter.name()
                    + ": " + refusal.getMessage());
        }
    }

    private static void returnVehicle(Depot depot, Vehicle vehicle) {
        try {
            depot.returnVehicle(vehicle);
            System.out.println("Returned " + describe(vehicle));
        } catch (RentalException refusal) {
            System.out.println("Cannot return " + describe(vehicle) + ": " + refusal.getMessage());
        }
    }

    static void printReport(Depot depot) {
        System.out.print("""

                === %s - end of day ===
                Fleet status:
                """.formatted(depot.getName()));

        for (Vehicle vehicle : depot.getVehicles()) {
            var status = depot.isAvailable(vehicle) ? "AVAILABLE" : "RENTED";
            System.out.println("  " + vehicle.model() + " (" + vehicle.registration() + ", "
                    + vehicle.category().getName() + "): " + status);
        }

        System.out.println("Active rentals:");
        for (Rental rental : depot.getActiveRentals()) {
            System.out.println("  Rental #" + rental.getId() + ": " + rental.getVehicle().registration()
                    + " -> " + rental.getRenter().name() + " (" + rental.getDays() + " days)");
        }
    }

    private static String describe(Vehicle vehicle) {
        return vehicle.model() + " (" + vehicle.registration() + ")";
    }

    
    private static String format(BigDecimal amount) {
        BigDecimal stripped = amount.stripTrailingZeros();
        return stripped.setScale(Math.max(1, stripped.scale())).toPlainString();
    }
}