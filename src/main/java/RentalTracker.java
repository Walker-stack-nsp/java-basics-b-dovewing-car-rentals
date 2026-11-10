
import exception.RentalException;
import model.*;
import service.Depot;

import java.math.BigDecimal;
import java.util.*;

public class RentalTracker {

    public static void main(String[] args) {
        
        var depot = new Depot("Dovewing Car Rentals");

        var vitz = depot.addVehicle("Toyota Vitz", "GT-1234-20",
                new EconomyCategory(new BigDecimal("150.00"), new BigDecimal("500.00")));
        var prado = depot.addVehicle("Toyota Prado", "GR-5678-22",
                new PremiumCategory(new BigDecimal("600.00"), new BigDecimal("2000.00")));

        var kofi = depot.addRenter("Kofi Mensah", MemberType.CASUAL);
        var ama = depot.addRenter("Ama Owusu", MemberType.MEMBER);

        // a normal day
        rent(depot, kofi, vitz, 3);
        rent(depot, ama, prado, 2);
        returnVehicle(depot, prado);

        // the request that used to double-book the Vitz: now refused
        rent(depot, ama, vitz, 2);

        printReport(depot);

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

    // Formats money like the original program
    private static String format(BigDecimal amount) {
        BigDecimal stripped = amount.stripTrailingZeros();
        return stripped.setScale(Math.max(1, stripped.scale())).toPlainString();
    }
}