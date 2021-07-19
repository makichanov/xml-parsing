package by.epam.task02.entity;

public class CurrencyAmount extends Amount {
    private String currency;

    public CurrencyAmount(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public CurrencyAmount(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        CurrencyAmount amount = (CurrencyAmount) o;
        return (Double.compare(super.amount, amount.amount) == 0)
                && (currency != null ? currency.equals(amount.currency) : amount.currency == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = currency != null ? currency.hashCode() : 0;
        temp = Double.doubleToLongBits(super.amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrencyAmount{");
        sb.append("amount=").append(amount);
        sb.append(", currency='").append(currency).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
