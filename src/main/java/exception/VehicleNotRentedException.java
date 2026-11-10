package exception;

public class VehicleNotRentedException extends RentalException {
    
    public VehicleNotRentedException() {
        super("Vehicle is not rented");
    }
}
