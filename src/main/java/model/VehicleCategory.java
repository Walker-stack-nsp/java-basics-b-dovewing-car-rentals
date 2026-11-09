public abstract class VehicleCategory {

    private final BigDecimal dailyRate;
    private final BigDecimal deposit;

    protected VehicleCategory(BigDecimal dailyRate, BigDecimal deposit) {
        this.dailyRate = requireNotNegative(dailyRate, "dailyRate");
        this.deposit = requireNotNegative(deposit, "deposit");
    }

    /** The name shown in reports, for example "ECONOMY". Each subclass supplies its own. */
    public abstract String getName();

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    /** Daily rate x days. A subclass can override this if its pricing differs. */
    public BigDecimal calculateCost(int days) {
        return dailyRate.multiply(BigDecimal.valueOf(days));
    }

    /** Casual renters pay the deposit; members have it waived. */
    public BigDecimal calculateDeposit(Renter renter) {
        if (renter.isMember()) {
            return BigDecimal.ZERO;
        }
        return deposit;
    }

    private static BigDecimal requireNotNegative(BigDecimal amount, String field) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException(field + " must be zero or more");
        }
        return amount;
    }
}
