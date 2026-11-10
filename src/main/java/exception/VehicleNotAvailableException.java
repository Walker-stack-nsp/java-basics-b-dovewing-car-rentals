package exception;

import model.Rental;

public class VehicleNotAvailableException extends RentalException {

    public VehicleNotAvailableException(int rentalId) {
        super("Vehicle already rented #N" + rentalId);
    }
    
}
