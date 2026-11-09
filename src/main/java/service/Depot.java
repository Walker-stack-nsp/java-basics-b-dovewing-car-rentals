

public class Depot {

    private final String name;
    private final Map<String, Vehicle> vehicles = new LinkedHashMap<>(); // key: registration
    private final List<Renter> renters = new ArrayList<>();
    private final List<Rental> rentals = new ArrayList<>();

    private int nextVehicleId = 1;
    private int nextRenterId = 1;
    private int nextRentalId = 1;

    public Depot(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Depot name is required");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Vehicle addVehicle(String model, String registration, VehicleCategory category) {
        if (vehicles.containsKey(registration)) {
            throw new IllegalArgumentException("A vehicle with registration " + registration + " already exists");
        }
        var vehicle = new Vehicle(nextVehicleId, model, registration, category);
        vehicles.put(registration, vehicle);
        nextVehicleId++;
        return vehicle;
    }

    public Renter addRenter(String renterName, MemberType memberType) {
        var renter = new Renter(nextRenterId, renterName, memberType);
        renters.add(renter);
        nextRenterId++;
        return renter;
    }

    public Rental rent(Renter renter, Vehicle vehicle, int days) {
        checkRenterBelongsHere(renter);
        checkVehicleBelongsHere(vehicle);
        if (days < 1) {
            throw new IllegalArgumentException("Days must be at least 1, got " + days);
        }
        var activeRental = findActiveRental(vehicle);
        if (activeRental != null) {
            throw new VehicleNotAvailableException(activeRental);
        }

        // Every check has passed: only now do we change anything.
        var category = vehicle.category();
        var rental = new Rental(nextRentalId, renter, vehicle, days,
                category.calculateCost(days),
                category.calculateDeposit(renter));
        rentals.add(rental);
        nextRentalId++;
        return rental;
    }

    public Rental returnVehicle(Vehicle vehicle) {
        checkVehicleBelongsHere(vehicle);
        var activeRental = findActiveRental(vehicle);
        if (activeRental == null) {
            throw new VehicleNotRentedException();
        }
        activeRental.markReturned();
        return activeRental;
    }

    public boolean isAvailable(Vehicle vehicle) {
        checkVehicleBelongsHere(vehicle);
        return findActiveRental(vehicle) == null;
    }

    public List<Vehicle> getVehicles() {
        return List.copyOf(vehicles.values());
    }

    public List<Renter> getRenters() {
        return List.copyOf(renters);
    }

    public List<Rental> getRentals() {
        return List.copyOf(rentals);
    }

    //Helpers
    private Rental findActiveRental(Vehicle vehicle) {
        for (Rental rental : rentals) {
            if (rental.getVehicle().equals(vehicle) && !rental.isReturned()) {
                return rental;
            }
        }
        return null;
    }

    private void checkVehicleBelongsHere(Vehicle vehicle) {
        if (vehicle == null || !vehicle.equals(vehicles.get(vehicle.registration()))) {
            throw new IllegalArgumentException("That vehicle is not part of the " + name + " fleet");
        }
    }

    private void checkRenterBelongsHere(Renter renter) {
        if (renter == null || !renters.contains(renter)) {
            throw new IllegalArgumentException("That renter is not registered with " + name);
        }
    }
}