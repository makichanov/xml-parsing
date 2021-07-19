package by.epam.task02.entity;

import java.time.LocalDate;

public class Deposit {
    private static final String DEFAULT_COUNTRY = "BY";
    private String bankName;
    private String country;
    private DepositType type;
    private String depositorFirstName;
    private String depositorLastName;
    private String id;
    private Amount amount;
    private double profitability;
    private LocalDate date;

    public Deposit() {
        this.country = DEFAULT_COUNTRY;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DepositType getType() {
        return type;
    }

    public void setType(DepositType type) {
        this.type = type;
    }

    public String getDepositorFirstName() {
        return depositorFirstName;
    }

    public void setDepositorFirstName(String depositorFirstName) {
        this.depositorFirstName = depositorFirstName;
    }

    public String getDepositorLastName() {
        return depositorLastName;
    }

    public void setDepositorLastName(String depositorLastName) {
        this.depositorLastName = depositorLastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmountValue() {
        return amount.amount;
    }

    public void setAmountValue(double value) {
        amount.amount = value;
    }

    public enum DepositType {
        DEMAND,
        CHECKING,
        ACCUMULATION,
        SAVINGS,
        METAL
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        Deposit deposit = (Deposit) o;
        return Double.compare(profitability, deposit.profitability) == 0
                && (bankName != null ? bankName.equals(deposit.bankName) : deposit.bankName == null)
                && (country != null ? country.equals(deposit.country) : deposit.country == null)
                && type == deposit.type
                && (depositorFirstName != null
                    ? depositorFirstName.equals(deposit.depositorFirstName)
                    : deposit.depositorFirstName == null)
                && (depositorLastName != null
                    ? depositorLastName.equals(deposit.depositorLastName)
                    : deposit.depositorLastName == null)
                && (id != null ? id.equals(deposit.id) : deposit.id == null)
                && (amount != null ? amount.equals(deposit.amount) : deposit.amount == null)
                && (date != null ? date.equals(deposit.date) : deposit.date == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = bankName != null ? bankName.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (depositorFirstName != null ? depositorFirstName.hashCode() : 0);
        result = 31 * result + (depositorLastName != null ? depositorLastName.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        temp = Double.doubleToLongBits(profitability);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Deposit{");
        sb.append("bankName='").append(bankName).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", type=").append(type);
        sb.append(", depositorFirstName='").append(depositorFirstName).append('\'');
        sb.append(", depositorLastName='").append(depositorLastName).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", profitability=").append(profitability);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
