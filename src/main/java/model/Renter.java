public record Renter(int id, String name, MemberType memberType) {

    public Renter {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Renter name is required");
        }
        Objects.requireNonNull(memberType, "memberType is required");
    }

    /** Members have their deposit waived. */
    public boolean isMember() {
        return memberType == MemberType.MEMBER;
    }
}
