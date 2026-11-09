public class PremiumCategory extends VehicleCategory {

    public PremiumCategory(BigDecimal dailyRate, BigDecimal deposit) {
        super(dailyRate, deposit);
    }

    @Override
    public String getName() {
        return "PREMIUM";
    }

}
