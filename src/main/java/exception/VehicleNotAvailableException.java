package exception;

import model.Rental;

public class VehicleNotAvailableException extends RentalException {

    public VehicleNotAvailableException(Rental rental) {
        super("Vehicle not available for rental #" + rental.getId());
    }
    
}
