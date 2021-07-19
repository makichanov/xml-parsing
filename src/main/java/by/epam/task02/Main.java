package by.epam.task02;

import by.epam.task02.builder.AbstractBankBuilder;
import by.epam.task02.builder.BankBuilderFactory;
import by.epam.task02.entity.Deposit;

public class Main {
    public static void main(String[] args) throws Exception {
        AbstractBankBuilder dom = BankBuilderFactory.createBankBuilder("DOM");
        dom.buildSetDeposits(Main.class.getResource("/data/banks.xml").getFile());
        for (Deposit deposit : dom.getDeposits()) {
            System.out.println(deposit);
        }
    }
}
