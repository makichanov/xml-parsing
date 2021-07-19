package by.epam.task02.handler;

public enum BankXMLTag {
    BANK("bank"),
    FIRST_NAME("first-name"),
    LAST_NAME("last-name"),
    TYPE("type"),
    CURRENCY_AMOUNT("currency-amount"),
    METAL_AMOUNT("metal-amount"),
    PROFITABILITY("profitability"),
    TIME("time"),
    ID("id"),
    COUNTRY("country"),
    DEPOSITOR("depositor"),
    CURRENCY("currency"),
    METAL("metal"),
    DEPOSIT("deposit"),
    BANKS("banks");
    private String title;

    BankXMLTag(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
