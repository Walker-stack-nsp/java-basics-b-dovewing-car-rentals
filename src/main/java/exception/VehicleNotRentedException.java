package exception;

import model.Vehicle;

public class VehicleNotRentedException extends RuntimeException{
    
    public VehicleNotRentedException(Vehicle vehicle) {
        System.out.printf("Vehicle model : %s with id: %s is not rented\n", vehicle.id(), vehicle.model());
    }
}
