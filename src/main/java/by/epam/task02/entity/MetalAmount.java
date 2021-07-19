package by.epam.task02.entity;

public class MetalAmount extends Amount {
    private String metal;

    public MetalAmount(String metal, double amount) {
        this.metal = metal;
        this.amount = amount;
    }

    public MetalAmount(String metal) {
        this.metal = metal;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        MetalAmount amount = (MetalAmount) o;
        return (Double.compare(super.amount, amount.amount) == 0)
                && (metal != null ? metal.equals(amount.metal) : amount.metal == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = metal != null ? metal.hashCode() : 0;
        temp = Double.doubleToLongBits(super.amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MetalAmount{");
        sb.append("amount=").append(amount);
        sb.append(", metal='").append(metal).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
