package exception;

import model.Rental;
import model.Vehicle;

public class VehicleNotAvailableException extends RentalException {

    public VehicleNotAvailableException(Vehicle vehicle) {
        System.out.printf("Vehicle model : %s with id: %s is not available\n",vehicle.model(), vehicle.id());
    }
    
}
