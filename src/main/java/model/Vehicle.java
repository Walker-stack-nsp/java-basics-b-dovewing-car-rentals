public record Vehicle (int id, String model, String registration, VehicleCategory category) {

    public Vehicle {
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Vehicle model is required");
        }
        if (registration == null || registration.isBlank()) {
            throw new IllegalArgumentException("Vehicle registration is required");
        }
        Objects.requireNonNull(category, "category is required");
    }
}
