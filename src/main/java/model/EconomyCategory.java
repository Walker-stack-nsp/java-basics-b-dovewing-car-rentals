public class EconomyCategory extends VehicleCategory {

    public EconomyCategory(BigDecimal dailyRate, BigDecimal deposit) {
        super(dailyRate, deposit);
    }

    @Override
    public String getName() {
        return "ECONOMY";
    }

}
